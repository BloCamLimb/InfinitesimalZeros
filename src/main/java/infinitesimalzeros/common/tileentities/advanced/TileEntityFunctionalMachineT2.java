package infinitesimalzeros.common.tileentities.advanced;

import cofh.core.fluid.FluidTankCore;
import infinitesimalzeros.api.interfaces.ISustainedData;
import infinitesimalzeros.common.core.InventoryHandlerZero;
import infinitesimalzeros.common.util.ItemDataUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

// 1 fluid input, 1 item output
public abstract class TileEntityFunctionalMachineT2 extends TileEntityFunctionalMachineT0 implements ISustainedData {
	
	public TileEntityFunctionalMachineT2(String name) {
		
		super(name, 0);
		
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
	
	@Override
	public void writeSustainedData(ItemStack itemStack) {
		
		if(inputTank.getFluid() != null)
			ItemDataUtils.setCompound(itemStack, "inputTank", inputTank.getFluid().writeToNBT(new NBTTagCompound()));
		
	}
	
	@Override
	public void readSustainedData(ItemStack itemStack) {
		
		inputTank.setFluid(FluidStack.loadFluidStackFromNBT(ItemDataUtils.getCompound(itemStack, "inputTank")));
		
	}
	
}
