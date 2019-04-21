package infinitesimalzeros.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonCore extends Gui {
	
	/** Button width in pixels */
    public int width;
    /** Button height in pixels */
    public int height;
    /** The x position of this control. */
    public int x;
    /** The y position of this control. */
    public int y;
    public int id;
    /** True if this control is enabled, false to disable. */
    public boolean enabled;
    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;
    
    public GuiButtonCore(int buttonId, int x, int y, int widthIn, int heightIn) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
    }
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		//FontRenderer fontrenderer = mc.fontRenderer;
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        //this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        //int i = this.getHoverState(this.hovered);
        //GlStateManager.enableBlend();
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        //this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);

	}
	
	protected int getHoverState(boolean mouseOver) {
		
        int i = 1;

        if (mouseOver) {
            i = 2;
        }

        return i;
    }
	
    public boolean isMouseHovered(Minecraft mc, int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }
}
