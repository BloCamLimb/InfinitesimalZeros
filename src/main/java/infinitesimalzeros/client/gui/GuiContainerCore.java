package infinitesimalzeros.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/*import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.element.ElementBase;
import infinitesimalzeros.client.gui.element.tab.TabBase;
import infinitesimalzeros.client.gui.element.tab.TabTracker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;*/

public abstract class GuiContainerCore extends GuiContainer {

	public GuiContainerCore(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	/*public ArrayList<TabBase> tabs = new ArrayList<>();
	protected ArrayList<ElementBase> elements = new ArrayList<>();
	
	protected ResourceLocation texture;
	
	protected int mouseX = 0;
	protected int mouseY = 0;

	public GuiContainerCore(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		tabs.clear();
	}
	
	public TabBase addTab(TabBase tab) {
		
		int yOffset = 4;
		for (TabBase tab1 : tabs) {
			if (tab1.side == tab.side && tab1.isVisible()) {
				yOffset += tab1.currentHeight;
			}
		}
		tab.setPosition(tab.side == TabBase.LEFT ? 0 : xSize, yOffset);
		tabs.add(tab);
		
		if (TabTracker.getOpenedLeftTab() != null && tab.getClass().equals(TabTracker.getOpenedLeftTab())) {
			tab.setFullyOpen();
		} else if (TabTracker.getOpenedRightTab() != null && tab.getClass().equals(TabTracker.getOpenedRightTab())) {
			tab.setFullyOpen();
		}
		return tab;
	}
	
	public void drawSizedModalRect(int x1, int y1, int x2, int y2, int color) {

		int temp;

		if (x1 < x2) {
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1 < y2) {
			temp = y1;
			y1 = y2;
			y2 = temp;
		}

		float a = (color >> 24 & 255) / 255.0F;
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(r, g, b, a);

		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		buffer.pos(x1, y2, this.zLevel).endVertex();
		buffer.pos(x2, y2, this.zLevel).endVertex();
		buffer.pos(x2, y1, this.zLevel).endVertex();
		buffer.pos(x1, y1, this.zLevel).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public void drawSizedTexturedModalRect(int x, int y, int u, int v, int width, int height, float texW, float texH) {

		float texU = 1 / texW;
		float texV = 1 / texH;
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(x, y + height, this.zLevel).tex((u) * texU, (v + height) * texV).endVertex();
		buffer.pos(x + width, y + height, this.zLevel).tex((u + width) * texU, (v + height) * texV).endVertex();
		buffer.pos(x + width, y, this.zLevel).tex((u + width) * texU, (v) * texV).endVertex();
		buffer.pos(x, y, this.zLevel).tex((u) * texU, (v) * texV).endVertex();
		Tessellator.getInstance().draw();
	}
	
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void drawIcon(TextureAtlasSprite icon, int x, int y) {
		GlStateManager.color(1, 1, 1, 1);
		drawTexturedModalRect(x, y, icon, 16, 16);
	}
	
	public void bindTexture(ResourceLocation texture) {
		mc.renderEngine.bindTexture(texture);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {

		GlStateManager.color(1, 1, 1, 1);
		bindTexture(texture);

		if (xSize > 256 || ySize > 256) {
			drawSizedTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
		} else {
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, 0.0F);
		drawElements(partialTick, false);
		drawTabs(partialTick, false);
		GlStateManager.popMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawTabs(0, true);
	}
	
	protected void drawElements(float partialTick, boolean foreground) {

		if (foreground) {
			for (ElementBase element : elements) {
				if (element.isVisible()) {
					element.drawForeground(mouseX, mouseY);
				}
			}
		} else {
			for (ElementBase element : elements) {
				if (element.isVisible()) {
					element.drawBackground(mouseX, mouseY, partialTick);
				}
			}
		}
	}
	
	public ElementBase addElement(ElementBase element) {

		elements.add(element);
		return element;
	}
	
	protected void drawTabs(float partialTick, boolean foreground) {
		
		int yPosRight = 4;
		int yPosLeft = 4;
		
		if (foreground) {
			for (TabBase tab : tabs) {
				tab.update();
				if (!tab.isVisible()) {
					continue;
				}
				if (tab.side == TabBase.LEFT) {
					tab.drawForeground(mouseX, mouseY);
					yPosLeft += tab.currentHeight;
				} else {
					tab.drawForeground(mouseX, mouseY);
					yPosRight += tab.currentHeight;
				}
			}
		} else {
			for (TabBase tab : tabs) {
				tab.update();
				if (!tab.isVisible()) {
					continue;
				}
				if (tab.side == TabBase.LEFT) {
					tab.setPosition(0, yPosLeft);
					tab.drawBackground(mouseX, mouseY, partialTick);
					yPosLeft += tab.currentHeight;
				} else {
					tab.setPosition(xSize, yPosRight);
					tab.drawBackground(mouseX, mouseY, partialTick);
					yPosRight += tab.currentHeight;
				}
			}
		}
	}*/
	
}
