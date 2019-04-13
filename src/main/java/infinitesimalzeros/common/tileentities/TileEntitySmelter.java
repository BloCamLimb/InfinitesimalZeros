package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IAdvancedBoundingBlock;
import infinitesimalzeros.api.interfaces.IInventoryZ;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.core.handler.InventoryHandler;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.LangUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntitySmelter extends TileEntityElectricMachine implements IAdvancedBoundingBlock, IInventoryZ, ISustainedInventory {
	
	public int size = 12;
	
	public NonNullList<ItemStack> inventory = NonNullList.withSize(size, ItemStack.EMPTY);
	
	public TileEntitySmelter() {
		
		super(50000, 60, 200);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		inventory = IZUtils.readInventory(nbtTags.getTagList("Items", NBT.TAG_COMPOUND), size);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setTag("Items", IZUtils.writeInventory(inventory));
		
		return nbtTags;
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		
		return inventory;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(LangUtils.localize(getBlockType().getUnlocalizedName() + ".Smelter.name"));
	}
	
	@Override
	public void setInventory(NBTTagList nbtTags, Object... data) {
		
		if(nbtTags == null || nbtTags.tagCount() == 0) {
			return;
		}
		
		for(int slots = 0; slots < nbtTags.tagCount(); slots++) {
			NBTTagCompound tagCompound = (NBTTagCompound) nbtTags.getCompoundTagAt(slots);
			byte slotID = tagCompound.getByte("Slot");
			
			if(slotID >= 0 && slotID < inventory.size()) {
				inventory.set(slotID, IZUtils.loadFromNBT(tagCompound));
			}
		}
	}
	
	@Override
	public NBTTagList getRInventory(Object... data) {
		
		NBTTagList tagList = new NBTTagList();
		

			for(int slots = 0; slots < inventory.size(); slots++) {
				if(!inventory.get(slots).isEmpty()) {
					NBTTagCompound tagCompound = new NBTTagCompound();
					tagCompound.setByte("Slot", (byte) slots);
					inventory.get(slots).writeToNBT(tagCompound);
					tagList.appendTag(tagCompound);
				}
			}
		
		
		return tagList;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	IItemHandler insertionHandler = new InventoryHandler(11, this, 0, true, false);
	IItemHandler extractionHandler = new InventoryHandler(1, this, 11, false, true);
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(facing==EnumFacing.UP)
				return (T) insertionHandler;
			else 
				return (T) extractionHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void onPlace() {
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					if(x == 0 && y == 0 && z == 0) {
						
						continue;
					}
					
					BlockPos pos = getPos().add(x,y,z);
					IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this));
					world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
				}
			}
		}
		
	}

	@Override
	public void onBreak() {
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					world.setBlockToAir(getPos().add(x,y,z));
				}
			}
		}
		
	}

	@Override
	public int[] getBoundSlots(BlockPos location, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canBoundInsert(BlockPos location, int i, ItemStack itemstack) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBoundExtract(BlockPos location, int i, ItemStack itemstack, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBoundReceiveEnergy(BlockPos location, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}
	
}
