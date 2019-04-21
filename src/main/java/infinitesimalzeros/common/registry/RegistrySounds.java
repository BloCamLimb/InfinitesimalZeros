package infinitesimalzeros.common.registry;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistrySounds {
	
	public static SoundEvent BUTTONCLICK;
	
	public static void registerSounds(IForgeRegistry<SoundEvent> registry) {
		BUTTONCLICK = registerSound(registry, "button");
	}
	
	public static SoundEvent registerSound(IForgeRegistry<SoundEvent> registry, String soundName) {
        ResourceLocation soundID = new ResourceLocation(InfinitesimalZeros.MODID, soundName);
        SoundEvent event = new SoundEvent(soundID).setRegistryName(soundID);
        registry.register(event);
        return event;
    }
	
}
