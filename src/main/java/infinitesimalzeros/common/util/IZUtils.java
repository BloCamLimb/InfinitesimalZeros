package infinitesimalzeros.common.util;

import java.util.Collection;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IActiveState;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class IZUtils {
	
	/**
	 * Updates a block's light value and marks it for a render update.
	 * 
	 * @param world - world the block is in
	 * @param pos   Position of the block
	 */
	public static void updateBlock(World world, BlockPos pos) {
		
		if(!(world.getTileEntity(pos) instanceof IActiveState) || ((IActiveState) world.getTileEntity(pos)).renderUpdate()) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
		
		if(!(world.getTileEntity(pos) instanceof IActiveState) || ((IActiveState) world.getTileEntity(pos)).lightUpdate()) {
			updateAllLightTypes(world, pos);
		}
	}
	
	/**
	 * Updates all light types at the given coordinates.
	 * 
	 * @param world - the world to perform the lighting update in
	 * @param pos   - coordinates of the block to update
	 */
	public static void updateAllLightTypes(World world, BlockPos pos) {
		
		world.checkLightFor(EnumSkyBlock.BLOCK, pos);
		world.checkLightFor(EnumSkyBlock.SKY, pos);
	}
	
	/**
	 * Marks the chunk this TileEntity is in as modified. Call this method to be sure NBT is written by the defined tile entity.
	 * 
	 * @param tileEntity - TileEntity to save
	 */
	public static void saveChunk(TileEntity tileEntity) {
		
		if(tileEntity == null || tileEntity.isInvalid() || tileEntity.getWorld() == null) {
			return;
		}
		
		tileEntity.getWorld().markChunkDirty(tileEntity.getPos(), tileEntity);
	}
	
	/**
	 * Checks if a machine is in it's active state.
	 * 
	 * @param world World of the machine to check
	 * @param pos   The position of the machine
	 * @return if machine is active
	 */
	public static boolean isActive(IBlockAccess world, BlockPos pos) {
		
		TileEntity tileEntity = world.getTileEntity(pos);
		
		if(tileEntity != null) {
			if(tileEntity instanceof IActiveState) {
				return ((IActiveState) tileEntity).getActive();
			}
		}
		
		return false;
	}
	
	public static ResourceLocation getResource(ResourceType type, String name) {
		
		return new ResourceLocation("infinitesimalzeros", type.getPrefix() + name);
	}
	
	public enum ResourceType {
		GUI("gui"),
		GUI_ELEMENT("gui/elements"),
		SOUND("sound"),
		RENDER("render"),
		TEXTURE_BLOCKS("textures/blocks"),
		TEXTURE_ITEMS("textures/items"),
		MODEL("models"),
		INFUSE("infuse");
		
		private String prefix;
		
		ResourceType(String s) {
			
			prefix = s;
		}
		
		public String getPrefix() {
			
			return prefix + "/";
		}
	}
	
	public static TileEntity getTileEntitySafe(IBlockAccess worldIn, BlockPos pos) {
		
		return worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
	}
	
	public static void constructAdvBoundingBox(World world, BlockPos pos, Coord4D core, int index) {
		
		world.setBlockState(pos, RegistryBlocks.BBBlock.getDefaultState());
		
		if(!world.isRemote) {
			
			TileEntityAdvancedBoundingBox tile = (TileEntityAdvancedBoundingBox) world.getTileEntity(pos);
			
			tile.setCorePosition(core.getPos());
			
			switch(index) {
				case 1:
					tile.func = 1;
					break;
				case 2:
					tile.func = 2;
					break;
				default:
					break;
			}
		}
	}
	
	public static ItemStack loadFromNBT(NBTTagCompound nbtTags) {
		
		ItemStack r = new ItemStack(nbtTags);
		return r;
	}
	
	public static NonNullList<ItemStack> readInventory(NBTTagList tagList, int size) {
		
		NonNullList inventory = NonNullList.withSize(size, ItemStack.EMPTY);
		
		for(int count = 0; count < tagList.tagCount(); count++) {
			
			NBTTagCompound itemTag = tagList.getCompoundTagAt(count);
			byte slotID = itemTag.getByte("Slot");
			
			if(slotID >= 0 && slotID < size)
				inventory.set(slotID, loadFromNBT(itemTag));
			
		}
		
		return inventory;
	}
	
	public static NBTTagList writeInventory(Collection<ItemStack> inventory) {
		
		NBTTagList tagList = new NBTTagList();
		
		byte slotCount = 0;
		
		for(ItemStack stack : inventory) {
			
			if(!stack.isEmpty()) {
				
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", slotCount);
				stack.writeToNBT(itemTag);
				tagList.appendTag(itemTag);
			}
			
			slotCount++;
		}
		
		return tagList;
	}
	
}
