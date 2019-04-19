package infinitesimalzeros.client.gui.tab;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class GuiTabHome extends GuiBasicTab {

	public GuiTabHome(List<GuiTabs> tabs, GuiScreen g) {
		
		super(tabs, g);
	}

	@Override
	public GuiTabs getGuiTab() {
		
		return GuiTabs.HOME;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		drawDefaultBackground();
		
		
	}
	
	
	
}
