package infinitesimalzeros.client.gui.tab;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class GuiTabNetwork extends GuiBasicTab {

	public GuiTabNetwork(List<GuiTabs> tabs, GuiScreen g) {
		
		super(tabs, g);
	}

	@Override
	public GuiTabs getGuiTab() {
		
		return GuiTabs.NETWORK;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		fontRenderer.drawString("BloCamLimb Creates World", 20, 30, 0xFFCCFF);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}

}
