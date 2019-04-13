package infinitesimalzeros.common.items;

import infinitesimalzeros.common.blocks.NanaFurnaceTE;
import infinitesimalzeros.common.util.ItemDataUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTestMachine extends ItemBlock {

	public ItemTestMachine(Block block) {
		
		super(block);
	}
	
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		
		if(super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
			
			NanaFurnaceTE tileEntity = (NanaFurnaceTE) world.getTileEntity(pos);
			if(stack.getTagCompound() != null) {
				tileEntity.setInventory(stack.getTagCompound().getCompoundTag("InputItems"));
				tileEntity.setInventoryOut(stack.getTagCompound().getCompoundTag("OutputItems"));
			}
			
			return true;
		};
		
		return false;
		
		
	}
	
	
}
