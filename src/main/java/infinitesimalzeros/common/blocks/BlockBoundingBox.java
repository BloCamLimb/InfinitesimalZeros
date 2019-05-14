package infinitesimalzeros.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicBlock;
import infinitesimalzeros.common.tileentities.basic.TileEntityBoundingBox;
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
		this.setHardness(-1.0F);
		this.setResistance(1145141919810.0F);
	}
	
	public static BlockPos getCoreBlockPos(World world, BlockPos pos) {
		
		TileEntity tile = world.getTileEntity(pos);
		
		if(tile instanceof TileEntityBoundingBox && !((TileEntityBoundingBox) tile).corePos.equals(pos)) {
			
			return ((TileEntityBoundingBox) tile).corePos;
		}
		
		return BlockPos.ORIGIN;
	}
	
	public static BlockTileEntityCore getCoreBlock(World world, BlockPos pos) {
		
		BlockPos corePos = getCoreBlockPos(world, pos);
		
		if(!corePos.equals(BlockPos.ORIGIN))
			return (BlockTileEntityCore) world.getBlockState(corePos).getBlock();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(worldIn.isRemote) {
			return true;
		}
		
		BlockPos corePos = getCoreBlockPos(worldIn, pos);
		IBlockState blockState = worldIn.getBlockState(corePos);
		
		return blockState.getBlock().onBlockActivated(worldIn, corePos, blockState, playerIn, hand, facing, hitX, hitY, hitZ);
		
		/*try {
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);	
				return blockState.getBlock().onBlockActivated(worldIn, corePos, blockState, playerIn, hand, facing, hitX, hitY, hitZ);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
		}
		
		return false;*/
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		
		/*TileEntityBoundingBox tile = (TileEntityBoundingBox) worldIn.getTileEntity(pos);
		
		if(tile != null) {
			
		}*/
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		worldIn.removeTileEntity(pos);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World worldIn, BlockPos pos, EntityPlayer playerIn) {
		
		BlockPos corePos = getCoreBlockPos(worldIn, pos);
		
		if(corePos.equals(BlockPos.ORIGIN))
			PacketHandler.sendToServer(new DataRequestMessage(Coord4D.get(worldIn.getTileEntity(pos))));
		
		IBlockState blockState = worldIn.getBlockState(corePos);
		
		return blockState.getBlock().getPickBlock(blockState, target, worldIn, corePos, playerIn);
		
		/*try {
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);
				return blockState.getBlock().getPickBlock(blockState, target, worldIn, corePos, playerIn);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
		}
		
		return ItemStack.EMPTY;*/
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, boolean willHarvest) {
		
		/*BlockPos corePos = getCoreBlockPos(worldIn, pos);
		IBlockState blockState = worldIn.getBlockState(corePos);
		
		return blockState.getBlock().removedByPlayer(blockState, worldIn, corePos, playerIn, willHarvest);*/
		
		/*try {
			BlockPos corePos = getCoreBlockPos(worldIn, pos);
			
			if(corePos != null) {
				IBlockState blockState = worldIn.getBlockState(corePos);
				return blockState.getBlock().removedByPlayer(blockState, worldIn, corePos, playerIn, willHarvest);
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error(e);
		}*/
		
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
