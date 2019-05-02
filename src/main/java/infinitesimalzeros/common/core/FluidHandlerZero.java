package infinitesimalzeros.common.core;

import javax.annotation.Nullable;

import infinitesimalzeros.api.interfaces.IMultiblockMachineT2;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidHandlerZero implements IFluidHandler {
	
	final IMultiblockMachineT2 tile;
	final EnumFacing side;
	
	public FluidHandlerZero(IMultiblockMachineT2 multiblock, EnumFacing side) {
		
		this.tile = multiblock;
		this.side = side;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		
		IFluidTank[] tanks = this.tile.getAccessibleFluidTanks(side);
		IFluidTankProperties[] array = new IFluidTankProperties[tanks.length];
		for(int i = 0; i < tanks.length; i++)
			array[i] = new FluidTankProperties(tanks[i].getFluid(), tanks[i].getCapacity());
		return array;
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		
		IFluidTank[] tanks = this.tile.getAccessibleFluidTanks(side);
		int fill = -1;
		for(int i = 0; i < tanks.length; i++) {
			IFluidTank tank = tanks[i];
			if(tank != null && this.tile.canFillTankFrom(i, side, resource) && tank.getFluid() != null && tank.getFluid().isFluidEqual(resource)) {
				fill = tank.fill(resource, doFill);
				if(fill > 0)
					break;
			}
		}
		if(fill == -1)
			for(int i = 0; i < tanks.length; i++) {
				IFluidTank tank = tanks[i];
				if(tank != null && this.tile.canFillTankFrom(i, side, resource)) {
					fill = tank.fill(resource, doFill);
					if(fill > 0)
						break;
				}
			}

		return fill < 0 ? 0 : fill;
	}
	
	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		
		IFluidTank[] tanks = this.tile.getAccessibleFluidTanks(side);
		FluidStack drain = null;
		for(int i = 0; i < tanks.length; i++) {
			IFluidTank tank = tanks[i];
			if(tank != null && this.tile.canDrainTankFrom(i, side)) {
				if(tank instanceof IFluidHandler)
					drain = ((IFluidHandler) tank).drain(resource, doDrain);
				else
					drain = tank.drain(resource.amount, doDrain);
				if(drain != null)
					break;
			}
		}

		return drain;
	}
	
	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		
		IFluidTank[] tanks = this.tile.getAccessibleFluidTanks(side);
		FluidStack drain = null;
		for(int i = 0; i < tanks.length; i++) {
			IFluidTank tank = tanks[i];
			if(tank != null && this.tile.canDrainTankFrom(i, side)) {
				drain = tank.drain(maxDrain, doDrain);
				if(drain != null)
					break;
			}
		}

		return drain;
	}
	
}
