package infinitesimalzeros.common.items;

import cofh.redstoneflux.api.IEnergyContainerItem;
import infinitesimalzeros.api.interfaces.IEnergizedItem;
import infinitesimalzeros.api.interfaces.ISustainedData;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricBlock;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.ItemDataUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Method;

public class ItemBlockMachine extends ItemBlock implements IEnergizedItem, ISustainedInventory, IEnergyContainerItem {
	
	public ItemBlockMachine(Block block) {
		
		super(block);
		setHasSubtypes(true);
		setNoRepair();
		setMaxStackSize(1);
	}
	
	@Override
	public int getMetadata(int i) {
		
		return i;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		
		if(MachineTypes.get(itemstack) != null) {
			return getUnlocalizedName() + "." + MachineTypes.get(itemstack).name;
		}
		
		return "null";
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		
		boolean placeable = true;
		
		MachineTypes type = MachineTypes.get(stack);
		
		if(type == MachineTypes.Smelting_Factory) {
			
			BlockPos.MutableBlockPos iPos = new BlockPos.MutableBlockPos();
			
			for(int x = -1; x <= 1; x++) {
				
				for(int y = 0; y <= 3; y++) {
					
					for(int z = -1; z <= 1; z++) {
						
						iPos.setPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
						Block iBlock = world.getBlockState(iPos).getBlock();
						
						if(!world.isValid(iPos) || !world.isBlockLoaded(iPos, false) || !iBlock.isReplaceable(world, iPos)) {
							placeable = false;
						}
					}
				}
			}
		}
		
		if(placeable && super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
			
			TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
			
			if(tileEntity instanceof ISustainedData) {
				if(stack.getTagCompound() != null) {
					((ISustainedData) tileEntity).readSustainedData(stack);
				}
			}
			
			if(tileEntity instanceof ISustainedInventory) {
				((ISustainedInventory) tileEntity).setInventory(getRInventory(stack));
			}
			
			if(tileEntity instanceof TileEntityElectricBlock) {
				((TileEntityElectricBlock) tileEntity).electricityStored = getEnergy(stack);
			}
			
			return true;
		}
		
		return false;
	}
	
	public void setInventory(NBTTagList nbtTags, Object... data) {
		
		if(data[0] instanceof ItemStack) {
			ItemDataUtils.setList((ItemStack) data[0], "Items", nbtTags);
		}
	}
	
	public NBTTagList getRInventory(Object... data) {
		
		if(data[0] instanceof ItemStack) {
			return ItemDataUtils.getList((ItemStack) data[0], "Items");
		}
		
		return null;
	}
	
	@Override
	public double getEnergy(ItemStack itemStack) {
		
		if(!MachineTypes.get(itemStack).isElectric) {
			return 0;
		}
		
		return ItemDataUtils.getDouble(itemStack, "energyStored");
	}
	
	@Override
	public void setEnergy(ItemStack itemStack, double amount) {
		
		if(!MachineTypes.get(itemStack).isElectric) {
			return;
		}
		
		ItemDataUtils.setDouble(itemStack, "energyStored", Math.max(Math.min(amount, getMaxEnergy(itemStack)), 0));
	}
	
	@Override
	public double getMaxEnergy(ItemStack itemStack) {
		
		MachineTypes machineType = MachineTypes.get(Block.getBlockFromItem(itemStack.getItem()), itemStack.getItemDamage());
		
		return 10000000;
	}
	
	@Override
	public double getMaxTransfer(ItemStack itemStack) {
		
		return getMaxEnergy(itemStack) * 0.005;
	}
	
	@Override
	public boolean canReceive(ItemStack itemStack) {
		
		return MachineTypes.get(itemStack).isElectric;
	}
	
	@Override
	public boolean canSend(ItemStack itemStack) {
		
		return false;
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int receiveEnergy(ItemStack theItem, int energy, boolean simulate) {
		
		if(canReceive(theItem)) {
			double energyNeeded = getMaxEnergy(theItem) - getEnergy(theItem);
			double toReceive = Math.min(energy, energyNeeded);
			
			if(!simulate) {
				setEnergy(theItem, getEnergy(theItem) + toReceive);
			}
			
			return (int) Math.round(toReceive);
		}
		
		return 0;
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int extractEnergy(ItemStack theItem, int energy, boolean simulate) {
		
		if(canSend(theItem)) {
			double energyRemaining = getEnergy(theItem);
			double toSend = energyRemaining;
			
			if(!simulate) {
				setEnergy(theItem, getEnergy(theItem) - toSend);
			}
			
			return (int) Math.round(toSend);
		}
		
		return 0;
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getEnergyStored(ItemStack theItem) {
		
		return (int) (getEnergy(theItem));
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getMaxEnergyStored(ItemStack theItem) {
		
		return (int) (getMaxEnergy(theItem));
	}
	
}
