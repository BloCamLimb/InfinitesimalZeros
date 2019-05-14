package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT1;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntitySmeltingFactory extends TileEntityFunctionalMachineT1 {
	
	public TileEntitySmeltingFactory() {
		
		super("SmeltingFactory", 3000000);
		

	}

	@Override
	public void onPlace(EnumFacing side) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBreak() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public IFluidTank[] getAccessibleFluidTanks(EnumFacing side) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrainTankFrom(int iTank, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValid(int slot, ItemStack stack) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		// TODO Auto-generated method stub
		
	}


	
}
