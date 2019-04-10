package infinitesimalzeros.common.tileentity;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.capability.Capabilities;
import infinitesimalzeros.common.util.interfaces.IAdvancedBoundingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;

@InterfaceList({ @Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"), @Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux") })

public class TileEntityAdvancedBoundingBox extends TileEntityBoundingBox implements ISidedInventory, IEnergyReceiver, IEnergyProvider {
	
	public IAdvancedBoundingBlock getAdvTile() {
		
		TileEntity tile = new Coord4D(posCore, world).getTileEntity(world);
		
		if(!world.isBlockLoaded(posCore) || tile == null || !(tile instanceof IAdvancedBoundingBlock)) {
			return null;
		}
		
		return (IAdvancedBoundingBlock) tile;
	}
	
	@Override
	public int getSizeInventory() {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getSizeInventory();
	}
	
	@Override
	public boolean isEmpty() {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return true;
		}
		
		return advTile.isEmpty();
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return ItemStack.EMPTY;
		}
		
		return advTile.getStackInSlot(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return ItemStack.EMPTY;
		}
		
		return advTile.decrStackSize(index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return ItemStack.EMPTY;
		}
		
		return advTile.removeStackFromSlot(index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return;
		}
		
		advTile.setInventorySlotContents(index, stack);
	}
	
	@Override
	public int getInventoryStackLimit() {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getInventoryStackLimit();
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.isUsableByPlayer(player);
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return;
		}
		
		advTile.openInventory(player);
		
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return;
		}
		
		advTile.closeInventory(player);
		
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.canBoundInsert(getPos(), index, stack);
	}
	
	@Override
	public int getField(int id) {
		
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
		
	}
	
	@Override
	public int getFieldCount() {
		
		return 0;
	}
	
	@Override
	public void clear() {
		
	}
	
	@Override
	public String getName() {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return "null";
		}
		
		return advTile.getName();
	}
	
	@Override
	public boolean hasCustomName() {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.hasCustomName();
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getEnergyStored(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getMaxEnergyStored(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getMaxEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.canBoundReceiveEnergy(getPos(), from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.extractEnergy(from, maxExtract, simulate);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null || !canConnectEnergy(from)) {
			return 0;
		}
		
		return advTile.receiveEnergy(from, maxReceive, simulate);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		int[] EMPTY = new int[] {};
		
		if(advTile == null) {
			return EMPTY;
		}
		
		return advTile.getBoundSlots(getPos(), side);
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		
		return isItemValidForSlot(index, itemStackIn);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.canBoundExtract(getPos(), index, stack, direction);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY) {
			return super.hasCapability(capability, facing);
		}
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return super.hasCapability(capability, facing);
		}
		
		return advTile.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY) {
			return super.getCapability(capability, facing);
		}
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return super.getCapability(capability, facing);
		}
		
		return advTile.getCapability(capability, facing);
	}
	
}
