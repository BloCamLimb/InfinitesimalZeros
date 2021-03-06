package infinitesimalzeros.common.util;

import java.text.NumberFormat;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import net.minecraft.client.renderer.OpenGlHelper;
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
	
    private static float lightmapLastX;
    private static float lightmapLastY;
    private static boolean optifineBreak = false;
	
	/**
	 * Updates a block's light value and marks it for a render update.
	 * 
	 * @param world - world the block is in
	 * @param pos   Position of the block
	 */
	public static void updateBlock(World world, BlockPos pos) {
		
			world.markBlockRangeForRenderUpdate(pos, pos);
		
			updateAllLightTypes(world, pos);
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
			
			tile.func = index;
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
	
	public static void glowOn() {
        glowOn(15);
    }

    public static void glowOn(int glow) {
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

        try {
            lightmapLastX = OpenGlHelper.lastBrightnessX;
            lightmapLastY = OpenGlHelper.lastBrightnessY;
        } catch (NoSuchFieldError e) {
            optifineBreak = true;
        }

        float glowRatioX = Math.min((glow / 15F) * 240F + lightmapLastX, 240);
        float glowRatioY = Math.min((glow / 15F) * 240F + lightmapLastY, 240);

        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, glowRatioX, glowRatioY);
        }
    }

    public static void glowOff() {
        if (!optifineBreak) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapLastX, lightmapLastY);
        }

        GL11.glPopAttrib();
    }
    
    public static enum TypeNumberFormat {
        FULL,                   // Full format
        COMPACT,                // Compact format (like 3.5M)
        COMMAS,                 // Language dependent comma separated format
        NONE                    // No output (empty string)
    }
    
    public static String format(long in, TypeNumberFormat style, String suffix) {
        switch (style) {
            case FULL:
                return Long.toString(in) + suffix;
            case COMPACT: {
                int unit = 1000;
                if (in < unit) {
                    return Long.toString(in) + " " + suffix;
                }
                int exp = (int) (Math.log(in) / Math.log(unit));
                char pre;
                if (suffix.startsWith("m")) {
                    suffix = suffix.substring(1);
                    if (exp - 2 >= 0) {
                        pre = "kMGTPE".charAt(exp - 2);
                        return String.format("%.1f %s", in / Math.pow(unit, exp), pre) + suffix;
                    } else {
                        return String.format("%.1f %s", in / Math.pow(unit, exp), suffix);
                    }
                } else {
                    pre = "kMGTPE".charAt(exp - 1);
                    return String.format("%.1f %s", in / Math.pow(unit, exp), pre) + suffix;
                }
            }
            case COMMAS:
                return NumberFormat.getInstance().format(in) + suffix;
            case NONE:
                return suffix;
        }
        return Long.toString(in);
    }
	
}
