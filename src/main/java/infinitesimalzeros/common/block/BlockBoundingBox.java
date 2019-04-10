package infinitesimalzeros.common.block;

import java.util.Random;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentity.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentity.TileEntityBoundingBox;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockBoundingBox extends Block {
	
	public BlockBoundingBox() {
		
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(25.0F);
		this.setResistance(999.9F);
	}
	
	private static BlockPos getMainBlockPos(World world, BlockPos pos) {
		
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityBoundingBox && !((TileEntityBoundingBox) te).posCore.equals(pos)) {
			return ((TileEntityBoundingBox) te).posCore;
		}
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		try {
			BlockPos mainPos = getMainBlockPos(worldIn, pos);
			
			if(mainPos != null) {
				IBlockState state1 = worldIn.getBlockState(mainPos);
				return state1.getBlock().onBlockActivated(worldIn, mainPos, state1, playerIn, hand, facing, hitX, hitY, hitZ);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error("Something went wrong", e);
		}
		
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		super.breakBlock(worldIn, pos, state);
		
		worldIn.removeTileEntity(pos);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World worldIn, BlockPos pos, EntityPlayer playerIn) {
		
		try {
			BlockPos mainPos = getMainBlockPos(worldIn, pos);
			
			if(mainPos != null) {
				IBlockState state1 = worldIn.getBlockState(mainPos);
				return state1.getBlock().getPickBlock(state1, target, worldIn, mainPos, playerIn);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error("Something went wrong", e);
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, boolean willHarvest) {
		
		try {
			BlockPos mainPos = getMainBlockPos(worldIn, pos);
			
			if(mainPos != null) {
				IBlockState state1 = worldIn.getBlockState(mainPos);
				return state1.getBlock().removedByPlayer(state1, worldIn, mainPos, playerIn, willHarvest);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error("Something went wrong", e);
		}
		
		return false;
	}
	
	@Override
	public int quantityDropped(Random random) {
		
		return 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		
		return null;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		/*if(state.getValue(BlockStateBounding.advancedProperty)) {
			return new TileEntityAdvancedBoundingBlock();
		} else {
			return new TileEntityBoundingBlock();
		}*/
		return new TileEntityAdvancedBoundingBox();
	}
	
}
