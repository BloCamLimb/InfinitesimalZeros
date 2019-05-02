package infinitesimalzeros.common.fluid;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.registry.RegistryFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidCore extends Fluid {

	public FluidCore(String fluidName, int color) {
		
		super(fluidName, new ResourceLocation(InfinitesimalZeros.MODID, "blocks/liquid/liquid_still"), new ResourceLocation(InfinitesimalZeros.MODID, "blocks/liquid/liquid_flow"));
		setColor(color);
		RegistryFluid.FLUIDS.add(this);
	}
	
	
			
	
}
