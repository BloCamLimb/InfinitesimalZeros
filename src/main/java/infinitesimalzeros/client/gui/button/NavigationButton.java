package infinitesimalzeros.client.gui.button;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.GuiContainerCore;
import infinitesimalzeros.client.gui.tab.GuiBasicTab;
import infinitesimalzeros.client.gui.tab.GuiBasicTab.GuiTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class NavigationButton extends GuiButtonCore {
	
	public static final ResourceLocation texture = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/button.png");
	public GuiTabs tab;
	public GuiBasicTab gui;

	public NavigationButton(GuiBasicTab gui, GuiTabs tab, int buttonId, int x, int y) {
		
		super(buttonId, x, y, 20, 20);
		this.tab = tab;
		this.gui = gui;
		
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		if(visible) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = x >= this.x && y >= this.y && x < this.x + 20 + 1 && y < this.y + 20 + 1;
			mc.getTextureManager().bindTexture(texture);
			drawTexturedModalRect(x, y, x, gui.getGuiTab() == tab ? y : y+30, 20, 20);
			
			
		}
		
		if(mouseX >= this.x && mouseY >= this.y && mouseX < this.x + width && mouseY < this.y + height) {
			gui.drawText(tab.getName(), x, y-10);
		}
		
		
	}
	
}
