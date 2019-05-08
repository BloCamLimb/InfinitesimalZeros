package infinitesimalzeros.client.gui.button;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.tab.GuiTabCore;
import infinitesimalzeros.client.gui.tab.GuiTabSecurity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PowerButton extends GuiButtonCore {

	public static final ResourceLocation TEXTURE = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/power_button_smooth.png");
	
	public PowerButton(int buttonId, int x, int y) {
		
		super(buttonId, x, y, 12, 12);
		this.text = "Power";
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(x, y, 0, 12 * getHoverState(isMouseHovered(mc, mouseX, mouseY)), 12, 12);
		
		if(isMouseHovered(mc, mouseX, mouseY)) {
			FontRenderer fontRenderer = mc.fontRenderer;
			fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text)/2 + 6, y - 10, 0xFFFFFF);
		}
		
	}
	
}
