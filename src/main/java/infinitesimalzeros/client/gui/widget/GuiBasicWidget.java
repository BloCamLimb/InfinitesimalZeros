package infinitesimalzeros.client.gui.widget;

import infinitesimalzeros.api.interfaces.IGuiZero;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBasicWidget<TileEntityWidget extends TileEntity> {
	
	protected final TileEntityWidget tileEntity;
	protected final ResourceLocation resource;
	protected final IGuiZero gui;
	
	public GuiBasicWidget(ResourceLocation resource, IGuiZero gui, TileEntityWidget tileEntity) {
		
		this.resource = resource;
		this.gui = gui;
		this.tileEntity = tileEntity;
	}
	
	public abstract void clickEvent(int xAxis, int yAxis, int button);
	
}
