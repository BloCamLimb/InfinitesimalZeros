package infinitesimalzeros.common.blocks;

import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import cofh.core.util.RayTracer;
import cofh.core.util.helpers.WrenchHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IActiveState;
import infinitesimalzeros.api.interfaces.IBoundingBlock;
import infinitesimalzeros.api.interfaces.IEnergizedItem;
import infinitesimalzeros.api.interfaces.ISecurityComponent;
import infinitesimalzeros.api.interfaces.ISustainedData;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.blocks.state.BlockStateFacing;
import infinitesimalzeros.common.blocks.state.BlockStateMachine;
import infinitesimalzeros.common.blocks.state.BlockStateMachine.MachineBlockPredicate;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicMachine;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricBlock;
import infinitesimalzeros.common.util.IZUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntityCore extends Block {
	
	public BlockTileEntityCore() {
		
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(-1.0F);
		this.setResistance(1145141919810.0F);
		this.setCreativeTab(InfinitesimalZeros.proxy.creativeTab);
	}
	
	public abstract MachineSets getMachineBlock();
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateMachine(this, getTypeProperty());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		MachineTypes type = MachineTypes.get(getMachineBlock(), meta & 0xF);
		return getDefaultState().withProperty(getTypeProperty(), type);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		MachineTypes type = state.getValue(getTypeProperty());
		return type.meta;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		
		TileEntity tileEntity = IZUtils.getTileEntitySafe(worldIn, pos);
		
		if(tileEntity instanceof TileEntityBasicBlock && ((TileEntityBasicBlock) tileEntity).facing != null) {
			state = state.withProperty(BlockStateFacing.facingProperty, ((TileEntityBasicBlock) tileEntity).facing);
		}
		
		if(tileEntity instanceof IActiveState) {
			state = state.withProperty(BlockStateMachine.activeProperty, ((IActiveState) tileEntity).getActive());
		}
		
		return state;
	}
	
	// Each set is linked to the specific Registry Name, containing 16 different types of machines.
	public static enum MachineSets {
		Machine_Set_A,
		Machine_Set_B;
		
		PropertyEnum<MachineTypes> machineTypeProperty;
		
		public PropertyEnum<MachineTypes> getProperty() {
			
			if(machineTypeProperty == null) {
				machineTypeProperty = PropertyEnum.create("type", MachineTypes.class, new MachineBlockPredicate(this));
			}
			
			return machineTypeProperty;
		}

		public Block getBlock() {
			
            switch (this) {
                case Machine_Set_A:
                    return RegistryBlocks.TEBlock1;
                case Machine_Set_B:
                    return null;
                default:
                    return null;
            }
        }
		
	}
	
	// All machine types, matched their set and meta.
	public static enum MachineTypes implements IStringSerializable {
		Smelter(MachineSets.Machine_Set_A, 0, "NanaSmelter", 0, TileEntitySmelter.class, true, true, Plane.HORIZONTAL);
		//Smelter_Adv(MachineSets.Machine_Set_A, 1, "SmelterAdv", 2, TileEntitySmelterAdv.class, true, true, Plane.HORIZONTAL),
		//Smelting_Factory(MachineSets.Machine_Set_A, 2, "SmelterFactory", 0, TileEntitySmeltingFactory.class, true, true, Plane.HORIZONTAL),
		//Ori_Furnace(MachineSets.Machine_Set_B, 0, "OriSmelter", 0, NanaFurnaceTE.class, true, true, Plane.HORIZONTAL);
		
		public MachineSets block;
		public int meta;
		public String name;
		public int guiId;
		public Class<? extends TileEntity> tileClass;
		public boolean isElectric;
		public boolean hasActiveTexture;
		public Predicate<EnumFacing> facingPredicate;
		
		MachineTypes(MachineSets block, int meta, String name, int guiId, Class<? extends TileEntity> tile, boolean electric, boolean hasActiveTexture, Predicate<EnumFacing> predicate) {
			
			this.block = block;
			this.meta = meta;
			this.name = name;
			this.guiId = guiId;
			tileClass = tile;
			isElectric = electric;
			this.hasActiveTexture = hasActiveTexture;
			facingPredicate = predicate;
			
		}
		
		public static MachineTypes get(MachineSets block, int meta) {
			
			for(MachineTypes type : values()) {
				
				if(type.meta == meta && type.block == block) {
					return type;
				}
			}
			
			return null;
		}
		
		public static MachineTypes get(Block block, int meta) {
			
			if(block instanceof BlockTileEntityCore) {
				return get(((BlockTileEntityCore) block).getMachineBlock(), meta);
			}
			
			return null;
		}
		
		public static MachineTypes get(ItemStack stack) {
			
			return get(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
		}
		
		public ItemStack getStack() {
			
            return new ItemStack(block.getBlock(), 1, meta);
        }
		
		public TileEntity create() {
			
			try {
				return tileClass.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		@Override
		public String getName() {
			
			return name().toLowerCase(Locale.ROOT);
		}
		
		public boolean hasActiveTexture() {
			return hasActiveTexture;
		}
		
		public boolean canRotateTo(EnumFacing side) {
            return facingPredicate.apply(side);
        }

        public boolean hasRotations() {
            return !facingPredicate.equals(Predicates.alwaysFalse());
        }
		
	}
	
	public PropertyEnum<MachineTypes> getTypeProperty() {
		
		return getMachineBlock().getProperty();
	}
	
	public static BlockTileEntityCore getBlockMachine(MachineSets block) {
		
		return new BlockTileEntityCore() {
			
			public MachineSets getMachineBlock() {
				
				return block;
			}
		};
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		
		for(MachineTypes type : MachineTypes.values()) {
			if(type.block == getMachineBlock()) {
				items.add(new ItemStack(this, 1, type.meta));
			}
		}
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) worldIn.getTileEntity(pos);
		int side = MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		int height = Math.round(placer.rotationPitch);
		int change = 3;
		
		if(tileEntity == null) {
			return;
		}
		
		if(tileEntity.canSetFacing(0) && tileEntity.canSetFacing(1)) {
			
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
		
		tileEntity.setFacing((short) change);
		
		if(tileEntity instanceof IBoundingBlock) {
			
			((IBoundingBlock) tileEntity).onPlace(EnumFacing.getFront(change));
		}
		
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(worldIn.isRemote) {
			return true;
		}
		
		TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) worldIn.getTileEntity(pos);
		int metadata = state.getBlock().getMetaFromState(state);
		ItemStack stack = playerIn.getHeldItem(hand);
		
		RayTraceResult traceResult = RayTracer.retrace(playerIn);
		
		/*if(playerIn.isSneaking() && !stack.isEmpty() && (stack.getItem() == RegistryItems.wrench || WrenchHelper.isHoldingUsableWrench(playerIn, traceResult))) {
			
			dismantleMachine(state, worldIn, pos);
			
			return true;
		}
		
		if(playerIn.isSneaking() && !stack.isEmpty() && stack.getItem() == RegistryItems.card && tileEntity instanceof ISecurityComponent) {
			
			if(!((TileEntityBasicMachine)tileEntity).ownerUUID.equals("")) {
				
				((ISecurityComponent)tileEntity).setSecurityCode(stack);
			
				return true;
			}
		}*/
		
		if(tileEntity != null) {
			
			MachineTypes type = MachineTypes.get(getMachineBlock(), metadata);
			
			if(!playerIn.isSneaking() && type.guiId >= 0) {
				
				playerIn.openGui(InfinitesimalZeros.instance, type.guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public ItemStack dismantleMachine(IBlockState state, World world, BlockPos pos) {
		
		ItemStack itemStack = getPickBlock(state, null, world, pos, null);
		
		world.setBlockToAir(pos);
		
		float motion = 0.7F;
		double motionX = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
		double motionY = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
		double motionZ = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
		
		EntityItem entityItem = new EntityItem(world, pos.getX() + motionX, pos.getY() + motionY, pos.getZ() + motionZ, itemStack);
		
		world.spawnEntity(entityItem);
		
		return itemStack;
		
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) worldIn.getTileEntity(pos);
		
		if(tileEntity instanceof IBoundingBlock) {
			((IBoundingBlock) tileEntity).onBreak();
		}
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		/*if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			
			TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
			
			float motion = 0.7F;
			double motionX = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			double motionY = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			double motionZ = (world.rand.nextFloat() * motion) + (1.0F - motion) * 0.5D;
			
			EntityItem entityItem = new EntityItem(world, pos.getX() + motionX, pos.getY() + motionY, pos.getZ() + motionZ, getPickBlock(state, null, world, pos, player));
			
			world.spawnEntity(entityItem);
		}*/
		
		return false;// world.setBlockToAir(pos);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		
		TileEntityBasicBlock tileEntity = (TileEntityBasicBlock) world.getTileEntity(pos);
		ItemStack itemStack = new ItemStack(this, 1, state.getBlock().getMetaFromState(state));
		
		if(itemStack.getTagCompound() == null) {
			
			itemStack.setTagCompound(new NBTTagCompound());
		}
		
		if(tileEntity instanceof ISustainedData) {
			
			((ISustainedData) tileEntity).writeSustainedData(itemStack);
		}
		
		if(tileEntity instanceof TileEntitySmelter && ((TileEntitySmelter) tileEntity).inventory.size() > 0) {
			
			ISustainedInventory inventory = (ISustainedInventory) itemStack.getItem();
			inventory.setInventory(((ISustainedInventory) tileEntity).getRInventory(), itemStack);
		}
		
		if(tileEntity instanceof TileEntityElectricBlock) {
			
			IEnergizedItem energizedItem = (IEnergizedItem) itemStack.getItem();
			energizedItem.setEnergy(itemStack, ((TileEntityElectricBlock) tileEntity).getEnergy());
		}
		
		return itemStack;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		int metadata = state.getBlock().getMetaFromState(state);
		
		if(MachineTypes.get(getMachineBlock(), metadata) == null) {
			return null;
		}
		
		return MachineTypes.get(getMachineBlock(), metadata).create();
	}
	
	public static class MachineBlockStateMapper extends StateMapperBase {

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            BlockTileEntityCore block = (BlockTileEntityCore) state.getBlock();
            MachineTypes type = state.getValue(block.getTypeProperty());
            StringBuilder builder = new StringBuilder();
            String nameOverride = null;

            if (type.hasActiveTexture()) {
                builder.append(BlockStateMachine.activeProperty.getName());
                builder.append("=");
                builder.append(state.getValue(BlockStateMachine.activeProperty));
            }

            if (type.hasRotations()) {
                EnumFacing facing = state.getValue(BlockStateFacing.facingProperty);

                if (!type.canRotateTo(facing)) {
                    facing = EnumFacing.NORTH;
                }

                if (builder.length() > 0) {
                    builder.append(",");
                }

                builder.append(BlockStateFacing.facingProperty.getName());
                builder.append("=");
                builder.append(facing.getName());
            }

            if (builder.length() == 0) {
                builder.append("normal");
            }

            ResourceLocation baseLocation = new ResourceLocation(InfinitesimalZeros.MODID,
                  nameOverride != null ? nameOverride : type.getName());

            return new ModelResourceLocation(baseLocation, builder.toString());
        }
    }

}
