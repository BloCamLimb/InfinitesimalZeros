package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface IBoundingBlock {
	
	/**
	 * Get TileEntity localized name, there's an error when using NBTEdit which not compatible with ITextComponent in some cases.
	 */
	String getName();
	
	void onPlace(EnumFacing side);
	
	void onBreak();
	
}
