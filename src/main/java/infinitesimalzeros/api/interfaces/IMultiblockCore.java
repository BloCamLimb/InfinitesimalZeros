package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;

public interface IMultiblockCore extends ICapabilityProvider, ITileNetwork, IInventoryZero, IMicroEnergyReceiver, IMicroEnergyTransmitter {
	
	/**
	 * Get TileEntity localized name, there's an error when using NBTEdit which not compatible with ITextComponent in some cases. (Bug Fix)
	 */
	String getName();
	
	void onPlace(EnumFacing side);
	
	void onBreak();
	
	/**
	 * Get first item handler for insertion.
	 */
	IItemHandler getInsertionHandler();
	
	/**
	 * Get first item handler for extraction.
	 */
	IItemHandler getExtractionHandler();
	
	IFluidTank[] getAccessibleFluidTanks(EnumFacing side);
	
	boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource);

	boolean canDrainTankFrom(int iTank, EnumFacing side);
	
}
