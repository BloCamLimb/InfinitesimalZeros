package infinitesimalzeros.common.block;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentity.TileEntitySmelter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class NanaGUI extends GuiContainer {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/gu.png");
	private final InventoryPlayer player;
	private final TileEntitySmelter tileentity;
	
	public int backgroundColor = 0xffffff;
	
	public boolean fullOpen;
	public int alpha = -1;
	public int aAlpha;
	public int color;
	
	public static int tabExpandSpeed;
	
	public int minWidth = 22;
	public int maxWidth = 180;
	public int currentWidth = 0;

	public int minHeight = 22;
	public int maxHeight = 220;
	public int currentHeight = 0;
	
	public NanaGUI(InventoryPlayer player, TileEntitySmelter tileentity) 
	{
		super(new NanaFurnaceCon(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		
		String tileName = this.tileentity.getDisplayName().getFormattedText();
		this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -8, 0xA8DAFF);
		//this.fontRenderer.drawString(this.player.getCurrentItem().getDisplayName(), 122, this.ySize - 96 + 2, 4210752);
		

		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		//this.drawTexturedModalRect((width-220)/2, (height-200)/2, 0, 0, 220, 200);
		
		/*if(NanaFurnaceTE.isBurning(tileentity))
		{
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 54 + 12 - k, 176, 12 - k, 14, k + 1);
		}*/
		if(fullOpen==true && color!=0) {
			this.fontRenderer.drawString("Energy", 150, 70, color);
			this.fontRenderer.drawString(this.tileentity.getEnergy()+"", 180, 70, color);
		}
		drawBackground();
		update();
		//int l = this.getCookProgressScaled(24);
		//this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 36, 176, 14, l + 1, 16);
	}

	protected void drawBackground() {
		
		float colorR = (backgroundColor >> 16 & 255) / 255.0F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F;
		float colorB = (backgroundColor & 255) / 255.0F;
		
		GlStateManager.color(colorR, colorG, colorB, 1.0F);

		this.mc.renderEngine.bindTexture(TEXTURES);
		
		//this.drawTexturedModalRect(0, 4, 0, 256 - currentHeight + 4, 4, currentHeight - 4);
		this.drawTexturedModalRect(width/2-90, height/2-100, 0, 256 - currentHeight + 4, 200, currentHeight - 4);
		//this.drawTexturedModalRect(0, 0, 0, 0, 4, 4);
		//this.drawTexturedModalRect(4, 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		tabExpandSpeed = Math.max(1, Math.round(maxHeight/Minecraft.getDebugFPS()));
	}
	
	public void update() {
		
		if (currentWidth <= maxWidth - tabExpandSpeed) {
			currentWidth += tabExpandSpeed;
		} else {
			currentWidth = maxWidth;
		}
		
		if (currentHeight <= maxHeight - tabExpandSpeed) {
			currentHeight += tabExpandSpeed;
		} else {
			currentHeight = maxHeight;
		}

		if (currentWidth == maxWidth && currentHeight == maxHeight) {
			fullOpen=true;
		}
	}
	
	@Override
	public void updateScreen() {
		
		if(fullOpen==true && alpha < 0xFF) {
			alpha += 32;
			aAlpha = alpha << 24;
			color = aAlpha + 0x00CCFF;
		}
		
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(1);
		if(i == 0) i = 200;
		return this.tileentity.getField(0) * pixels / i;
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.tileentity.getField(2);
		int j = this.tileentity.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

}
