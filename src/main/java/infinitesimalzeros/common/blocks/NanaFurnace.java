package infinitesimalzeros.common.blocks;

import java.util.Random;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.ISustainedData;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.client.gui.GuiTestTE;
import infinitesimalzeros.common.registry.BlockRegister;
import infinitesimalzeros.common.tileentities.basis.TileEntityFunctionalMachineT1;
import infinitesimalzeros.common.util.IZUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.ItemStackHandler;

public class NanaFurnace extends Block implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public static final PropertyBool RUNNING = PropertyBool.create("running");
	
	public NanaFurnace() {
		
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(1);
		this.setResistance(999);
		this.setHarvestLevel("pickaxe", 2);
		// this.setDefaultState(this.blockState.getBaseState().withProperty(RUNNING, false));
	}
	
	// Disable vanilla behavior to dropped the block item
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
	}
	
	/*
	 * @Override public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) { return new ItemStack(RegistryBlocks.NanaFurnace); }
	 */
	
	@Override
	public BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!worldIn.isRemote) {
			playerIn.openGui(InfinitesimalZeros.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		//FMLCommonHandler.instance().showGuiScreen(new GuiTestTE(playerIn.inventory, (NanaFurnaceTE) worldIn.getTileEntity(pos)));
		
		return true;
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		TileEntity tile = IZUtils.getTileEntitySafe(worldIn, pos);
		
		if(tile instanceof NanaFurnaceTE && ((NanaFurnaceTE) tile).facing != null) {
			state = state.withProperty(FACING, ((NanaFurnaceTE) tile).facing);
		}
		return state;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		NanaFurnaceTE te = (NanaFurnaceTE) worldIn.getTileEntity(pos);
		int side = MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		int height = Math.round(placer.rotationPitch);
		int change = 3;
		
		if(te == null) {
			return;
		}
		
		if(te.canSetFacing(0) && te.canSetFacing(1)) {
			if(height >= 65) {
				change = 1;
			} else if(height <= -65) {
				change = 0;
			}
		}
		
		if(change != 0 && change != 1) {
			switch(side) {
				case 0:
					change = 2;
					break;
				case 1:
					change = 5;
					break;
				case 2:
					change = 3;
					break;
				case 3:
					change = 4;
					break;
			}
		}
		
		te.setFacing((short) change);
		
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
	}
	
	/*
	 * @Override public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) { if(!worldIn.isRemote) { IBlockState north =
	 * worldIn.getBlockState(pos.north()); IBlockState south = worldIn.getBlockState(pos.south()); IBlockState west = worldIn.getBlockState(pos.west());
	 * IBlockState east = worldIn.getBlockState(pos.east()); EnumFacing face = (EnumFacing)state.getValue(FACING);
	 * 
	 * if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.NORTH; else if (face == EnumFacing.SOUTH &&
	 * south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.SOUTH; else if (face == EnumFacing.WEST && west.isFullBlock() &&
	 * !east.isFullBlock()) face = EnumFacing.WEST; else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.EAST;
	 * worldIn.setBlockState(pos, state.withProperty(FACING, face), 2); } }
	 */
	
	/*
	 * @Override public void breakBlock(World worldIn, BlockPos pos, IBlockState state) { NanaFurnaceTE te = (NanaFurnaceTE)worldIn.getTileEntity(pos);
	 * InventoryHelper.dropInventoryItems(worldIn, pos, te); super.breakBlock(worldIn, pos, state); }
	 */
	
	/*
	 * public static void setState(boolean active, World worldIn, BlockPos pos) { IBlockState state = worldIn.getBlockState(pos); TileEntity te =
	 * worldIn.getTileEntity(pos); if(active) worldIn.setBlockState(pos, RegistryBlocks.NanaFurnace.getDefaultState().withProperty(RUNNING, true), 3);
	 * else worldIn.setBlockState(pos, RegistryBlocks.NanaFurnace.getDefaultState().withProperty(RUNNING, false), 3);
	 * 
	 * if(te != null) { te.validate(); worldIn.setTileEntity(pos, te); }
	 * 
	 * }
	 */
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new NanaFurnaceTE();
	}
	
	/*
	 * @Override public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
	 * EntityLivingBase placer, EnumHand hand) { return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()); }
	 * 
	 * @Override public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	 * worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2); }
	 */
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.INVISIBLE;
	}
	
	/*
	 * @Override public IBlockState withRotation(IBlockState state, Rotation rot) { return state.withProperty(FACING,
	 * rot.rotate((EnumFacing)state.getValue(FACING))); }
	 * 
	 * @Override public IBlockState withMirror(IBlockState state, Mirror mirrorIn) { return
	 * state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING))); }
	 */
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return super.createTileEntity(world, state);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		// EnumFacing facing = EnumFacing.getFront(2);
		return this.getDefaultState()/* .withProperty(BlockStateFacing.facingProperty, facing) */;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return 0/* ((EnumFacing)state.getValue(BlockStateFacing.facingProperty)).getIndex() */;
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			NanaFurnaceTE tileEntity = (NanaFurnaceTE) world.getTileEntity(pos);
			
			float motion = 0.7F;
			double motionX = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			double motionY = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			double motionZ = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			
			EntityItem entityItem = new EntityItem(world, pos.getX() + motionX, pos.getY() + motionY, pos.getZ() + motionZ, getPickBlock(state, null, world, pos, player));
			
			world.spawnEntity(entityItem);
		}
		
		return world.setBlockToAir(pos);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		
		NanaFurnaceTE tileEntity = (NanaFurnaceTE) world.getTileEntity(pos);
		ItemStack itemStack = new ItemStack(this, 1, state.getBlock().getMetaFromState(state));
		
		// Force to generate NBT Tag
		if(itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		if(tileEntity instanceof ISustainedData) {
			((ISustainedData) tileEntity).writeSustainedData(itemStack);
		}
		
		itemStack.getTagCompound().setTag("InputItems",tileEntity.inputSlots.serializeNBT());
		itemStack.getTagCompound().setTag("OutItems",tileEntity.outputSlots.serializeNBT());
		
		
		return itemStack;
	}
	
	
}
