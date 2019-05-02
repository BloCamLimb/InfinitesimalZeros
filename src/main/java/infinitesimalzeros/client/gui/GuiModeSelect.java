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

		double angle = (MathHelper.atan2(mouseY - y , mouseX - x ) + Math.PI * 2) % (Math.PI * 2);

		int s = 8;
		float p = (float) Math.PI / 180;
		float dp = (float) Math.PI * 2 / s;
		
		int selected = -1;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		
		for(int i = 0; i < s; i++) {
			
			double angle2 = (i*Math.PI/4);
			
			int R = 60;
			
			boolean mouseHover = dp * i < angle && angle < dp * (i + 1);
			
			int a = Math.min(0x66, timeIn * 0xF + 0x1B);
			
			int r = 0xB9, g = 0xCD, b = 0xF6;
			
			if (i == 0)
				buffer.pos(x, y, 0).color(r, g, b, a).endVertex();
			
			if(i % 2 != 0)
				a += 0x20;
			
			if(mouseHover) {
				R = 63;
				r = 0x90;
				g = 0x82;
			}

			for(float j = 0; j < dp + p ; j += p) {
				
				float rad = j + i * dp;
				float xp = x + MathHelper.cos(rad) * R;
				float yp = y + MathHelper.sin(rad) * R;

				buffer.pos(xp, yp, 0).color(r, g, b, a).endVertex();
			}
		}
		
		tessellator.draw();
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
