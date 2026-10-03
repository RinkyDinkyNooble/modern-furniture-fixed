package com.cookiecraftmods.mdm.network;

import com.cookiecraftmods.mdm.MdmMod;
import com.cookiecraftmods.mdm.init.MdmModMenus;
import com.cookiecraftmods.mdm.init.MdmModScreens;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(
    bus = Bus.MOD
)
public class MenuStateUpdateMessage {
    private final int elementType;
    private final String name;
    private final Object elementState;

    public MenuStateUpdateMessage(int elementType, String name, Object elementState) {
        this.elementType = elementType;
        this.name = name;
        this.elementState = elementState;
    }

    public MenuStateUpdateMessage(FriendlyByteBuf buffer) {
        this.elementType = buffer.readInt();
        this.name = buffer.readUtf();
        Object elementState = null;
        if (this.elementType == 0) {
            elementState = buffer.readUtf();
        } else if (this.elementType == 1) {
            elementState = buffer.readBoolean();
        } else if (this.elementType == 2) {
            elementState = buffer.readDouble();
        }

        this.elementState = elementState;
    }

    public static void buffer(MenuStateUpdateMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.elementType);
        buffer.writeUtf(message.name);
        if (message.elementType == 0) {
            buffer.writeUtf((String)message.elementState);
        } else if (message.elementType == 1) {
            buffer.writeBoolean((Boolean)message.elementState);
        } else if (message.elementType == 2 && message.elementState instanceof Number n) {
            buffer.writeDouble(n.doubleValue());
        }
    }

    public static void handler(MenuStateUpdateMessage message, Supplier<Context> contextSupplier) {
        if (message.name.length() <= 256) {
            if (message.elementState instanceof String string && string.length() > 8192) {
                return;
            }

            Context context = contextSupplier.get();
            context.enqueueWork(
                () -> {
                    if (context.getSender().containerMenu instanceof MdmModMenus.MenuAccessor menu) {
                        menu.getMenuState().put(message.elementType + ":" + message.name, message.elementState);
                        if (!context.getDirection().getReceptionSide().isServer()
                            && Minecraft.getInstance().screen instanceof MdmModScreens.ScreenAccessor accessor) {
                            accessor.updateMenuState(message.elementType, message.name, message.elementState);
                        }
                    }
                }
            );
            context.setPacketHandled(true);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MdmMod.addNetworkMessage(MenuStateUpdateMessage.class, MenuStateUpdateMessage::buffer, MenuStateUpdateMessage::new, MenuStateUpdateMessage::handler);
    }
}
