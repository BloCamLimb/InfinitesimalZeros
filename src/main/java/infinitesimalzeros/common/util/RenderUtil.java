package infinitesimalzeros.common.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUtil {
	
	public static int getFluidColor(FluidStack fluidStack) {
		
		if(fluidStack.getFluid() == FluidRegistry.WATER)
			return 0x3094CF;
		else if(fluidStack.getFluid() == FluidRegistry.LAVA)
			return 0xFFD700;
		return fluidStack.getFluid().getColor(fluidStack);
	}
	
}
