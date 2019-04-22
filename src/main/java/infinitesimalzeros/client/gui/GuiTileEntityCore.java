package infinitesimalzeros.client.gui;

import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import net.minecraft.inventory.Container;

public abstract class GuiTileEntityCore<TileEntityGui extends TileEntityBasicBlock> extends GuiContainerCore {
	
	protected TileEntityGui tileEntityGui;

	public GuiTileEntityCore(TileEntityGui tileEntity, Container inventorySlotsIn) {
		
		super(inventorySlotsIn);
		this.tileEntityGui = tileEntity;
	}
	
}
