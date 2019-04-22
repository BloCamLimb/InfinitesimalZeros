package infinitesimalzeros.client.gui.tab;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class GuiTabNetwork extends GuiTabCore {

	public GuiTabNetwork(GuiScreen g) {
		
		super(g);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		fontRenderer.drawString("BloCamLimb Creates World", width / 2 - 30, height / 2 - 30, 0xFFCCFF);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}

}
