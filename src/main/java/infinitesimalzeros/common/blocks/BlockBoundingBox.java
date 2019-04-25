package infinitesimalzeros.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.TileEntityBoundingBox;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBoundingBox extends Block {
	
	public BlockBoundingBox() {
		
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(25.0F);
		this.setResistance(1145141919810.0F);
	}
	
	@Nullable
	private static BlockPos getCoreBlockPos(World world, BlockPos pos) {
		
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityBoundingBox && !((TileEntityBoundingBox) te).corePos.equals(pos)) {
			
			return ((TileEntityBoundingBox) te).corePos;
		}
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(worldIn.isRemote) {
			return true;
		}
		
		try {
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);
				
				return blockState.getBlock().onBlockActivated(worldIn, corePos, blockState, playerIn, hand, facing, hitX, hitY, hitZ);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
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
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);
				return blockState.getBlock().getPickBlock(blockState, target, worldIn, corePos, playerIn);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, boolean willHarvest) {
		
		try {
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);
				return blockState.getBlock().removedByPlayer(blockState, worldIn, corePos, playerIn, willHarvest);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
		}
		
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		
		return null;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		
		return false;
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
