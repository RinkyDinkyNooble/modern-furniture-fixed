package com.rinkynooble.modernfurniturefixed;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** Storage open and close sounds. They play Minecraft's barrel sounds (see {@code assets/mdm/sounds.json}). */
public final class MffSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MdmMod.MODID);
    private static final String PREFIX = "block.furniture.";
    public static final RegistryObject<SoundEvent> OPEN = register("open");
    public static final RegistryObject<SoundEvent> CLOSE = register("close");

    private MffSounds() {
    }

    private static RegistryObject<SoundEvent> register(String name) {
        return REGISTRY.register(PREFIX + name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MdmMod.MODID, PREFIX + name)));
    }

    /** Mutes the storage sounds for players who turned them off in the client config. */
    @Mod.EventBusSubscriber(modid = MdmMod.MODID, value = Dist.CLIENT)
    public static final class ClientEvents {
        private ClientEvents() {
        }

        @SubscribeEvent
        public static void onPlaySound(PlaySoundEvent event) {
            SoundInstance sound = event.getSound();
            if (sound != null && !MffConfig.furnitureSounds()
                    && MdmMod.MODID.equals(sound.getLocation().getNamespace())
                    && sound.getLocation().getPath().startsWith(PREFIX)) {
                event.setSound(null);
            }
        }
    }
}
