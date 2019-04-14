package infinitesimalzeros.common.util;

import java.util.IllegalFormatException;

import net.minecraftforge.fluids.FluidStack;

public final class LangUtils {
	
	public static String localizeFluidStack(FluidStack fluidStack) {
		
		return (fluidStack == null || fluidStack.getFluid() == null) ? null : fluidStack.getFluid().getLocalizedName(fluidStack);
	}
	
}
