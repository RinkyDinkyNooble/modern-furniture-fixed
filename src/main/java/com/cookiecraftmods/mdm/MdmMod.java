package com.cookiecraftmods.mdm;

import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.cookiecraftmods.mdm.init.MdmModItems;
import com.cookiecraftmods.mdm.init.MdmModTabs;
import com.rinkynooble.modernfurniturefixed.MffConfig;
import com.rinkynooble.modernfurniturefixed.MffSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mdm")
public class MdmMod {
    public static final Logger LOGGER = LogManager.getLogger(MdmMod.class);
    public static final String MODID = "mdm";

    public MdmMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        MdmModBlocks.REGISTRY.register(bus);
        MdmModBlockEntities.REGISTRY.register(bus);
        MdmModItems.REGISTRY.register(bus);
        MdmModTabs.REGISTRY.register(bus);
        MffSounds.REGISTRY.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MffConfig.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MffConfig.CLIENT_SPEC);
    }
}
