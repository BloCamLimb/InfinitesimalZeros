package infinitesimalzeros.common.items;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import cofh.core.util.helpers.ServerHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.blocks.BlockBoundingBox;
import infinitesimalzeros.common.blocks.BlockTileEntityCore;
import infinitesimalzeros.common.registry.ItemRegister;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemWrench extends ItemRegister implements IToolHammer {

	public ItemWrench(String name) {
		
		super(name);
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		
		return true;
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		if (!world.isRemote && player.isSneaking() && (block instanceof BlockTileEntityCore || block instanceof BlockBoundingBox)) {
			
			if(block instanceof BlockTileEntityCore)
				((BlockTileEntityCore) block).dismantleMachine(state, world, pos);
			
			if(block instanceof BlockBoundingBox) {
				
				BlockPos cPos = ((BlockBoundingBox) block).getCoreBlockPos(world, pos);
				IBlockState cState = world.getBlockState(cPos);
				BlockTileEntityCore cBlock = ((BlockBoundingBox) block).getCoreBlock(world, pos);
				
				if(cBlock != null)
					cBlock.dismantleMachine(cState, world, cPos);
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos) {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity) {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity) {
		
		// TODO Auto-generated method stub
		
	}
	
}
