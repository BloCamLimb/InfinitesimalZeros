package infinitesimalzeros.common.registry;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class RegistryFluid {
	
	public static final Fluid salt_water = new Fluid("saltwater", new ResourceLocation(InfinitesimalZeros.MODID, "blocks/liquid/liquidheavywater"), new ResourceLocation(InfinitesimalZeros.MODID, "blocks/liquid/liquidheavywater"));
	
	public static void register() {
		
		FluidRegistry.registerFluid(salt_water);
		FluidRegistry.addBucketForFluid(salt_water);
	}
	
}
