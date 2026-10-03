package com.cookiecraftmods.mdm;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModMenus;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import it.unimi.dsi.fastutil.ints.IntObjectImmutablePair;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mdm")
public class MdmMod {
    public static final Logger LOGGER = LogManager.getLogger(MdmMod.class);
    public static final String MODID = "mdm";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("mdm", "mdm"), () -> "1", "1"::equals, "1"::equals);
    private static int messageID = 0;
    private static final Queue<IntObjectPair<Runnable>> workToBeScheduled = new ConcurrentLinkedQueue<>();
    private static final PriorityQueue<TickTask> workQueue = new PriorityQueue<>(Comparator.comparingInt(TickTask::getTick));

    public MdmMod(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = context.getModEventBus();
        MdmModBlocks.REGISTRY.register(bus);
        MdmModBlockEntities.REGISTRY.register(bus);
        MdmModItems.REGISTRY.register(bus);
        MdmModTabs.REGISTRY.register(bus);
        MdmModMenus.REGISTRY.register(bus);
    }

    public static <T> void addNetworkMessage(
        Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<Context>> messageConsumer
    ) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    public static void queueServerWork(int delay, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            workToBeScheduled.add(new IntObjectImmutablePair(delay, action));
        }
    }

    @SubscribeEvent
    public void tick(ServerTickEvent event) {
        if (event.phase == Phase.END) {
            int currentTick = event.getServer().getTickCount();

            IntObjectPair<Runnable> work;
            while ((work = workToBeScheduled.poll()) != null) {
                workQueue.add(new TickTask(currentTick + work.leftInt(), (Runnable)work.right()));
            }

            while (!workQueue.isEmpty() && currentTick >= workQueue.peek().getTick()) {
                workQueue.poll().run();
            }
        }
    }
}
