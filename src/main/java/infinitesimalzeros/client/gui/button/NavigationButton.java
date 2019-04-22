package infinitesimalzeros.client.gui.button;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.tab.GuiTabNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class NavigationButton extends GuiButtonCore {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/navigation_button.png");
	
	public int buttonNavigationId;

	public NavigationButton(int buttonId, int x, int y, int buttonNavigationId, String text) {
		
		super(buttonId, x, y, 12, 12);
		this.buttonNavigationId = buttonNavigationId;
		this.text = text;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(x, y, 12 * buttonNavigationId, 12 * (buttonNavigationId + getHoverState(isMouseHovered(mc, mouseX, mouseY))), 12, 12);
		
		if(isMouseHovered(mc, mouseX, mouseY)) {
			FontRenderer fontRenderer = mc.fontRenderer;
			fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text)/2 + 6, y-10, 0xFFFFFF);
		}
		
	}
	
	public void switchTab(int i, GuiScreen parent) {

		switch(i) {
			case 0:
				FMLCommonHandler.instance().showGuiScreen(new GuiTabNetwork(parent));
		}
		
	}
	
}
