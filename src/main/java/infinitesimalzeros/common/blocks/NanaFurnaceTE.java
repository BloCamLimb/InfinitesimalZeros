package infinitesimalzeros.common.blocks;

import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class NanaFurnaceTE extends TileEntityBasicBlock implements ITickable, IInventoryZero, IEnergyStorage {
	
	public EnumFacing facing = EnumFacing.NORTH;
	
	public EnumFacing clientFacing = facing;
	
	public NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
	
	public ItemStackHandler inputSlots = new ItemStackHandler(3) {
		protected void onContentsChanged(int slot) {NanaFurnaceTE.this.markDirty();};
	};
	public ItemStackHandler outputSlots = new ItemStackHandler(1) {
		protected void onContentsChanged(int slot) {NanaFurnaceTE.this.markDirty();};
	};
	
	private CombinedInvWrapper combine = new CombinedInvWrapper(inputSlots, outputSlots);
	
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime = 200;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(facing==null) {
			return (T) this.combine;
			} else if(facing==EnumFacing.UP) {
				return (T) this.inputSlots;
			} else {
				return (T) this.outputSlots;
			}
		}
		if(capability == CapabilityEnergy.ENERGY)
			return (T) this;
			
		return super.getCapability(capability, facing);
	}
	
	public boolean hasCustomName() {
		
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("Nana Furnace");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		this.inputSlots.deserializeNBT((NBTTagCompound) compound.getTag("InputItems"));
		this.outputSlots.deserializeNBT((NBTTagCompound) compound.getTag("OutputItems"));
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		//this.currentBurnTime = getItemBurnTime((ItemStack) this.handler.getStackInSlot(2));
		if(compound.hasKey("facing")) {
			facing = EnumFacing.getFront(compound.getInteger("facing"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.burnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setTag("InputItems", this.inputSlots.serializeNBT());
		compound.setTag("OutputItems", this.outputSlots.serializeNBT());
		if(facing != null) {
			compound.setInteger("facing", facing.ordinal());
		}
		
		return compound;
	}
	
	public void setFacing(short direction) {
		
		if(canSetFacing(direction)) {
			facing = EnumFacing.getFront(direction);
		}
		
		if(!(facing == clientFacing || world.isRemote)) {
			// PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
			markDirty();
			clientFacing = facing;
		}
	}
	
	public boolean canSetFacing(int facing) {
		
		return true;
	}
	
	
	
	public boolean isBurning() {
		
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(NanaFurnaceTE te) {
		
		return te.getField(0) > 0;
	}
	
	public void update() {
		/*
		 * if(this.isBurning()) { --this.burnTime; NanaFurnace.setState(true, world, pos); }
		 */
		
		//ItemStack[] inputs = new ItemStack[] { handler.getStackInSlot(0), handler.getStackInSlot(1) };
		//ItemStack fuel = this.handler.getStackInSlot(2);
		//InfinitesimalZeros.logger.info(handler.serializeNBT());
		/*
		 * if(this.isBurning() || !fuel.isEmpty() && !this.handler.getStackInSlot(0).isEmpty() || this.handler.getStackInSlot(1).isEmpty()) {
		 * if(!this.isBurning() && this.canSmelt()) { this.burnTime = getItemBurnTime(fuel); this.currentBurnTime = burnTime;
		 * 
		 * if(this.isBurning() && !fuel.isEmpty()) { Item item = fuel.getItem(); fuel.shrink(1);
		 * 
		 * if(fuel.isEmpty()) { ItemStack item1 = item.getContainerItem(fuel); this.handler.setStackInSlot(2, item1); } } } }
		 * 
		 * if(this.isBurning() && this.canSmelt() && cookTime > 0) { cookTime++; if(cookTime == totalCookTime) { if(handler.getStackInSlot(3).getCount() > 0)
		 * { handler.getStackInSlot(3).grow(1); } else { handler.insertItem(3, smelting, false); }
		 * 
		 * smelting = ItemStack.EMPTY; cookTime = 0; return; } } else { if(this.canSmelt() && this.isBurning()) { ItemStack output =
		 * SinteringFurnaceRecipes.getInstance().getSinteringResult(inputs[0], inputs[1]); if(!output.isEmpty()) { smelting = output; cookTime++;
		 * inputs[0].shrink(1); inputs[1].shrink(1); handler.setStackInSlot(0, inputs[0]); handler.setStackInSlot(1, inputs[1]); } } }
		 */
	}
	
	/*private boolean canSmelt() {
		
		if(((ItemStack) this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack) this.handler.getStackInSlot(1)).isEmpty())
			return false;
		else {
			ItemStack result = SinteringFurnaceRecipes.getInstance().getSinteringResult((ItemStack) this.handler.getStackInSlot(0), (ItemStack) this.handler.getStackInSlot(1));
			if(result.isEmpty())
				return false;
			else {
				ItemStack output = (ItemStack) this.handler.getStackInSlot(3);
				if(output.isEmpty())
					return true;
				if(!output.isItemEqual(result))
					return false;
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}*/
	
	public static int getItemBurnTime(ItemStack fuel) {
		
		if(fuel.isEmpty())
			return 0;
		else {
			Item item = fuel.getItem();
			
			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);
				
				if(block == Blocks.WOODEN_SLAB)
					return 150;
				if(block.getDefaultState().getMaterial() == Material.WOOD)
					return 300;
				if(block == Blocks.COAL_BLOCK)
					return 16000;
			}
			
			if(item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName()))
				return 200;
			if(item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName()))
				return 200;
			if(item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName()))
				return 200;
			if(item == Items.STICK)
				return 100;
			if(item == Items.COAL)
				return 1600;
			if(item == Items.LAVA_BUCKET)
				return 20000;
			if(item == Item.getItemFromBlock(Blocks.SAPLING))
				return 100;
			if(item == Items.BLAZE_ROD)
				return 2400;
			
			return GameRegistry.getFuelValue(fuel);
		}
	}
	
	public static boolean isItemFuel(ItemStack fuel) {
		
		return getItemBurnTime(fuel) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	public int getField(int id) {
		
		switch(id) {
			case 0:
				return this.burnTime;
			case 1:
				return this.currentBurnTime;
			case 2:
				return this.cookTime;
			case 3:
				return this.totalCookTime;
			default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		
		switch(id) {
			case 0:
				this.burnTime = value;
				break;
			case 1:
				this.currentBurnTime = value;
				break;
			case 2:
				this.cookTime = value;
				break;
			case 3:
				this.totalCookTime = value;
		}
	}

	@Override
	public void onUpdate() {
		
		// TODO Auto-generated method stub
		
	}
	
	public void setInventory(NBTTagCompound nbt) {
		inputSlots.deserializeNBT(nbt);
	}
	
	public void setInventoryOut(NBTTagCompound nbt) {
		outputSlots.deserializeNBT(nbt);
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		
		return inventory;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStackValid(int slot, ItemStack stack) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyStored() {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canExtract() {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive() {
		
		// TODO Auto-generated method stub
		return false;
	}
	
}
