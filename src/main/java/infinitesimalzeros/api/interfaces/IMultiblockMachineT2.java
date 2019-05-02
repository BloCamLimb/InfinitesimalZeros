package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IMultiblockMachineT2 extends IMultiblockCore {
	
	IFluidTank[] getAccessibleFluidTanks(EnumFacing side);
	
	boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource);

	boolean canDrainTankFrom(int iTank, EnumFacing side);
	
}
