package infinitesimalzeros.common.tileentities.advanced;

import infinitesimalzeros.common.core.InventoryHandlerZero;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

// 1 item input, 1 item output
public abstract class TileEntityFunctionalMachineT1 extends TileEntityFunctionalMachineT0 {

	public TileEntityFunctionalMachineT1(String name, double maxEnergy) {
		
		super(name, maxEnergy);
		
		size = 2;
		inventory = NonNullList.withSize(2, ItemStack.EMPTY);
		
		insertionHandler = new InventoryHandlerZero(1, this, 0, true, false);
		extractionHandler = new InventoryHandlerZero(1, this, 1, false, true);
	}
	
	@Override
	public IItemHandler getInsertionHandler() {
		
		return insertionHandler;
	}
	
	@Override
	public IItemHandler getExtractionHandler() {
		
		return extractionHandler;
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		
	}
	
	
	/*@Override
	public boolean isEmpty() {
		
		for(ItemStack stack : getInventory()) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		if(handleInventory()) {
			NBTTagList tagList = nbtTags.getTagList("Items", NBT.TAG_COMPOUND);
			inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
			
			for(int tagCount = 0; tagCount < tagList.tagCount(); tagCount++) {
				NBTTagCompound tagCompound = tagList.getCompoundTagAt(tagCount);
				byte slotID = tagCompound.getByte("Slot");
				
				if(slotID >= 0 && slotID < getSizeInventory()) {
					setInventorySlotContents(slotID, InventoryUtils.loadFromNBT(tagCompound));
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		if(handleInventory()) {
			NBTTagList tagList = new NBTTagList();
			
			for(int slotCount = 0; slotCount < getSizeInventory(); slotCount++) {
				ItemStack stackInSlot = getStackInSlot(slotCount);
				if(!stackInSlot.isEmpty()) {
					NBTTagCompound tagCompound = new NBTTagCompound();
					tagCompound.setByte("Slot", (byte) slotCount);
					stackInSlot.writeToNBT(tagCompound);
					tagList.appendTag(tagCompound);
				}
			}
			
			nbtTags.setTag("Items", tagList);
		}
		
		return nbtTags;
	}
	
	protected NonNullList<ItemStack> getInventory() {
		
		return inventory;
	}
	
	@Override
	public int getSizeInventory() {
		
		return getInventory() != null ? getInventory().size() : 0;
	}
	
	@Override
	public ItemStack getStackInSlot(int slotID) {
		
		return getInventory() != null ? getInventory().get(slotID) : ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack decrStackSize(int slotID, int amount) {
		
		if(getInventory() == null) {
			return ItemStack.EMPTY;
		}
		
		return ItemStackHelper.getAndSplit(getInventory(), slotID, amount);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int slotID) {
		
		if(getInventory() == null) {
			return ItemStack.EMPTY;
		}
		
		return ItemStackHelper.getAndRemove(getInventory(), slotID);
	}
	
	@Override
	public void setInventorySlotContents(int slotID, ItemStack itemstack) {
		
		getInventory().set(slotID, itemstack);
		
		if(!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
		
		markDirty();
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		
		return !isInvalid() && this.world.isBlockLoaded(this.pos);// prevent Containers from remaining valid after the chunk has unloaded;
	}
	
	@Override
	public String getName() {
		
		return LangUtils.localize(getBlockType().getUnlocalizedName() + "." + fullName + ".name");
	}
	
	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override
	public boolean hasCustomName() {
		
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
		
		return true;
	}
	
	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side) {
		
		return isItemValidForSlot(slotID, itemstack);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		
		return InventoryUtils.EMPTY;
	}
	
	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side) {
		
		return true;
	}
	
	@Override
	public void setInventory(NBTTagList nbtTags, Object... data) {
		
		if(nbtTags == null || nbtTags.tagCount() == 0 || !handleInventory()) {
			return;
		}
		
		inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		
		for(int slots = 0; slots < nbtTags.tagCount(); slots++) {
			NBTTagCompound tagCompound = (NBTTagCompound) nbtTags.getCompoundTagAt(slots);
			byte slotID = tagCompound.getByte("Slot");
			
			if(slotID >= 0 && slotID < inventory.size()) {
				inventory.set(slotID, InventoryUtils.loadFromNBT(tagCompound));
			}
		}
	}
	
	@Override
	public NBTTagList getInventory(Object... data) {
		
		NBTTagList tagList = new NBTTagList();
		
		if(handleInventory()) {
			for(int slots = 0; slots < inventory.size(); slots++) {
				if(!inventory.get(slots).isEmpty()) {
					NBTTagCompound tagCompound = new NBTTagCompound();
					tagCompound.setByte("Slot", (byte) slots);
					inventory.get(slots).writeToNBT(tagCompound);
					tagList.appendTag(tagCompound);
				}
			}
		}
		
		return tagList;
	}
	
	public boolean handleInventory() {
		
		return true;
	}*/
	
	/*@Override
	public int getField(int id) {
		
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {}
	
	@Override
	public int getFieldCount() {
		
		return 0;
	}
	
	@Override
	public void clear() {}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getName());
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	private CapabilityWrapperManager<ISidedInventory, ItemHandlerWrapper> itemManager = new CapabilityWrapperManager<>(ISidedInventory.class, ItemHandlerWrapper.class);
	
	/**
	 * Read only itemhandler for the null facing.
	 */
	/*private IItemHandler nullHandler = new InvWrapper(this) {
		
		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			
			return stack;
		}
		
		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			
			return ItemStack.EMPTY;
		}
		
		@Override
		public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
			
			// no
		}
	};
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(nullHandler);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemManager.getWrapper(this, facing));
		}
		
		return super.getCapability(capability, facing);
	}*/
	
}
