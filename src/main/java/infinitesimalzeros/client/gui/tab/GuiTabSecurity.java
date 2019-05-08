package infinitesimalzeros.client.gui.tab;

import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicBlock;
import net.minecraft.client.gui.GuiScreen;

public class GuiTabSecurity extends GuiTabCore {

	public GuiTabSecurity(GuiScreen g, TileEntityFunctionalMachineT0 t) {
		
		super(g, t);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String tileName = tileEntity.getDisplayName().getFormattedText();
		fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -12, 0xFFFFFF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		fontRenderer.drawString("BloCamLimb Creates World", width / 2 - 40, height / 2 - 30, 0xFFCCFF);
		fontRenderer.drawString("Security Verified: " + tileEntity.verified, width / 2 - 40, height / 2 - 50, 0xFFCCFF);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}

}
