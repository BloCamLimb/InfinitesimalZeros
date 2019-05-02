package infinitesimalzeros.common.tileentities.advanced;

import cofh.core.fluid.FluidTankCore;
import infinitesimalzeros.api.interfaces.IMultiblockMachineT2;
import infinitesimalzeros.common.core.InventoryHandlerZero;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidTank;

// 1 fluid input, 1 item output
public abstract class TileEntityFunctionalMachineT2 extends TileEntityFunctionalMachineT0 implements IMultiblockMachineT2 {

	public TileEntityFunctionalMachineT2(String name, double maxEnergy, double baseEnergyUsage, int baseTicksRequired) {
		
		super(name, maxEnergy, baseEnergyUsage, baseTicksRequired);
		
		size = 1;
		inventory = NonNullList.withSize(1, ItemStack.EMPTY);
		
		inputTank = new FluidTankCore(8000);
		
		extractionHandler = new InventoryHandlerZero(1, this, 0, false, true);
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setTag("TankIn", inputTank.writeToNBT(new NBTTagCompound()));
		
		return nbtTags;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		inputTank.readFromNBT(nbtTags.getCompoundTag("TankIn"));
	}
	
}
