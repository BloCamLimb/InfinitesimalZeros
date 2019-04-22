package infinitesimalzeros.client.gui.tab;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class GuiTabNetwork extends GuiTabCore {
	
	public GuiTabNetwork() {}

	public GuiTabNetwork(GuiScreen g) {
		
		super(g);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		fontRenderer.drawString("S-SilverStar Injector", this.xSize / 2 - this.fontRenderer.getStringWidth("S-SilverStar Injector") / 2 + 3, -12, 0xFFFFFF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		fontRenderer.drawString("BloCamLimb Creates World", width / 2 - 40, height / 2 - 30, 0xFFCCFF);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}

}
