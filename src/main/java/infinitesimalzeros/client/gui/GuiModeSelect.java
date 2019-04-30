package infinitesimalzeros.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.core.handler.KeybindHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class GuiModeSelect extends GuiScreen {
	
	int timeIn = 0;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		int x = width / 2;
		int y = height / 2;
		int maxRadius = 80;

		double angle = (MathHelper.atan2(mouseY - y, mouseX - x) + Math.PI * 2) % (Math.PI * 2);

		int segments = 6;
		float step = (float) Math.PI / 180;
		float degPer = (float) Math.PI * 2 / segments;


		int slotSelected = -1;

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();

		
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

		/*for(int seg = 0; seg < segments; seg++) {
			
			float radius = Math.max(0F, Math.min((timeIn + partialTicks - seg * 20F / segments) * 40F, maxRadius));
			boolean mouseInSector = degPer * seg < angle && angle < degPer * (seg + 1);
			if (mouseInSector)
				radius *= 1.025f;

			int gs = 0x40;
			//if(seg % 2 == 0)
			//	gs = 0x19;
			int r = gs;
			int g = gs;
			int b = gs;
			int a = 0x66;


			if (seg == 0)
				buf.pos(x, y, 0).color(r, g, b, a).endVertex();

			if(mouseInSector) {
				slotSelected = seg;

					r = g = b = 0xFF;
			}
			
			for(float i = 0; i < degPer + step / 2; i += step) {
				float rad = i + seg * degPer;
				float xp = x + MathHelper.cos(rad) * radius;
				float yp = y + MathHelper.sin(rad) * radius;

				//if (i == 0)
				//	buf.pos(xp, yp, 0).color(r, g, b, a).endVertex();
				buf.pos(xp, yp, 0).color(r, g, b, a).endVertex();
			}
		}*/
		
		for(int i = 0; i < 8; i++) {
			double angle2 = (i*Math.PI/4);
			buf.pos(x+Math.sin(angle2)*60, y+Math.cos(angle2)*60, 0).color(5, 5, 5, 0x66).endVertex();
		}
		
		tess.draw();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		// TODO Auto-generated method stub
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void updateScreen() {
		
		super.updateScreen();
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_F))
			mc.player.closeScreen();
		timeIn++;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		
		return false;
	}
	
}
