package infinitesimalzeros.client.gui.button;

import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.tab.GuiTabCore;
import infinitesimalzeros.client.gui.tab.GuiTabSecurity;
import infinitesimalzeros.client.jei.CategoryIds;
import infinitesimalzeros.client.jei.JEICore;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class NavigationButton extends GuiButtonCore {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/navigation_button.png");
	
	public static final int SECURITY = 0;
	public static final int HOME = 1;
	public static final int RECIPE = 3;
	
	public int buttonNavigationId;

	public NavigationButton(int x, int y, int buttonNavigationId) {
		
		super(0, x, y, 12, 12);
		this.buttonNavigationId = buttonNavigationId;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(TEXTURE);
		drawTexturedModalRect(x, y, 12 * buttonNavigationId, 12 * getHoverState(isMouseHovered(mc, mouseX, mouseY)), 12, 12);
		
		if(isMouseHovered(mc, mouseX, mouseY)) {
			FontRenderer fontRenderer = mc.fontRenderer;
			String text = getTagName(buttonNavigationId);
			fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2 + 6, y - 10, 0xFFFFFF);
		}
		
	}
	
	private String getTagName(int id) {
		
		switch(id) {
			case HOME:
				return I18n.format("info.InfinitesimalZeros.Button.Home");
			case SECURITY:
				return I18n.format("info.InfinitesimalZeros.Button.Security");
			case RECIPE:
				return I18n.format("info.InfinitesimalZeros.Button.Recipe");
		}
		
		return "";
	}
	
	// In Home
	public void switchTab(int id, TileEntityFunctionalMachineT0 tile, GuiScreen parent) {

		switch(id) {
			case RECIPE:
				JEICore.showCraftingRecipes(InfinitesimalZeros.MODID + "." + tile.name.toLowerCase());
				break;
			case SECURITY:
				FMLCommonHandler.instance().showGuiScreen(new GuiTabSecurity(parent, tile));
				break;
		}
		
	}
	
	// Other Tabs
	public void switchTab(int id, TileEntityFunctionalMachineT0 tile) {

		switch(id) {
			case HOME:
				FMLCommonHandler.instance().showGuiScreen(GuiTabCore.getParent());
				break;
			case RECIPE:
				JEICore.showCraftingRecipes(InfinitesimalZeros.MODID + "." + tile.name.toLowerCase());
				break;
		}
		
	}
	
}
