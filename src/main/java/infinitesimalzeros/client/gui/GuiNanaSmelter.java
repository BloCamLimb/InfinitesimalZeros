package infinitesimalzeros.client.gui;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.button.GuiButtonCore;
import infinitesimalzeros.client.gui.tab.GuiBasicTab.GuiTabs;
import infinitesimalzeros.client.gui.tab.GuiTabNetwork;
import infinitesimalzeros.common.container.NanaFurnaceCon;
import infinitesimalzeros.common.registry.RegistrySounds;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiNanaSmelter extends GuiTileEntityCore<TileEntityBasicBlock> {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/guidefault.png");
	private static final ResourceLocation SLIDEBAR = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/slide_bar.png");
	
	private final InventoryPlayer player;
	private final TileEntitySmelter tileEntity;
	
	public List<GuiTabs> tabs = Lists.newArrayList(GuiTabs.HOME, GuiTabs.NETWORK);
	
	public int backgroundColor = 0xffffff;
	
	public boolean open;
	public boolean fullOpen;
	public int alpha = -1;
	public int aAlpha;
	public int color;
	
	public static int tabExpandSpeed;
	
	public int maxWidth = 99;
	public int barWidth;
	
	public int maxHeight = 140;
	public int barHeight;
	public int energyHeight;
	
	public GuiNanaSmelter(InventoryPlayer player, TileEntitySmelter tileEntity) {
		
		super(tileEntity, new NanaFurnaceCon(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		
		//addGUIWidget(new GuiSecurityWidget(this, tileEntityGui));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String tileName = this.tileEntity.getDisplayName().getFormattedText();
		//this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -8, 0xA8DAFF);
		this.fontRenderer.drawString("SilverStar Combiner", this.xSize / 2 - this.fontRenderer.getStringWidth("SilverStar Combiner") / 2 + 3, -12, 0xFFFFFF);
		// this.fontRenderer.drawString(this.player.getCurrentItem().getDisplayName(),
		// 122, this.ySize - 96 + 2, 4210752);

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(width / 2 - 128, height / 2 - 128, 0, 0, 256, 256);
		// this.drawTexturedModalRect((width-220)/2, (height-200)/2, 0, 0, 220, 200);
		
		/*
		 * if(NanaFurnaceTE.isBurning(tileentity)) { int k = this.getBurnLeftScaled(13); this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 54 + 12 -
		 * k, 176, 12 - k, 14, k + 1); }
		 */
		update();
		drawSlideBar();
		drawEnergyBar();
		String energy = ""+(int) this.tileEntity.getEnergy();
		if(fullOpen == true && color != 0) {
			this.fontRenderer.drawString("Energy ", width / 2 - 194, height / 2 + 37, color);
			this.fontRenderer.drawString(energy+" RF", width / 2 - 132 - this.fontRenderer.getStringWidth(energy), height / 2 + 37, color);
		}
		//InfinitesimalZeros.logger.info(currentHeight);
		// int l = this.getCookProgressScaled(24);
		// this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 36, 176, 14, l +
		// 1, 16);
	}
	
	protected void drawSlideBar() {
		
		float colorR = (backgroundColor >> 16 & 255) / 255.0F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F;
		float colorB = (backgroundColor & 255) / 255.0F;
		
		GlStateManager.color(colorR, colorG, colorB, 1.0F);
		GlStateManager.pushMatrix();
		this.mc.renderEngine.bindTexture(SLIDEBAR);
		this.drawTexturedModalRect(width / 2 - 108, height / 2 - 88, 100, 0, 4, barHeight);
		this.drawTexturedModalRect(width / 2 - 208, height / 2 - 88, barWidth - 100, 0, 100, 140);
		// this.drawTexturedModalRect(0, 4, 0, 256 - currentHeight + 4, 4, currentHeight
		// - 4);
		
		//this.drawTexturedModalRect(width /2 - 105, height /2 - 105, 0, 210 - currentHeight + 4, 220, currentHeight - 4);
		//this.drawTexturedModalRect((width-maxWidth)/2, (height-maxHeight)/2, 0, 0, 256, 256);
		// this.drawTexturedModalRect(0, 0, 0, 0, 4, 4);
		// this.drawTexturedModalRect(4, 4, 256 - currentWidth + 4, 256 - currentHeight
		// + 4, currentWidth - 4, currentHeight - 4);
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	protected void drawEnergyBar() {
		
		float colorR = (backgroundColor >> 16 & 255) / 255.0F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F;
		float colorB = (backgroundColor & 255) / 255.0F;
		
		GlStateManager.color(colorR, colorG, colorB, alpha / 255.0F);
		GlStateManager.pushMatrix();
		this.mc.renderEngine.bindTexture(SLIDEBAR);
		this.drawTexturedModalRect(width / 2 - 188, height / 2 + 10 - energyHeight, 105, 0, 12, energyHeight);
		if(fullOpen && color != 0)
			this.fontRenderer.drawString((int) Math.floor(energyHeight/0.9)+"%", width / 2 - 188, height / 2 - energyHeight, color);
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		tabExpandSpeed = Math.max(1, Math.round(maxHeight / Minecraft.getDebugFPS()));
		int i = 1;
		//buttonList.add(new GuiButton(1,0,0,20,20, ""));
		GuiButtons.add(new GuiButtonCore(1,0,0,20,20));
		for(GuiTabs tab : tabs) {
			
			//buttonList.add(new NavigationButton(this, tab, i, width/2-10, 60*i));
			i++;
		}
	}
	
	public void update() {
		
		if(!open || !fullOpen) {
			
			if(!open) {
				if(barHeight <= maxHeight - tabExpandSpeed * 2)
					barHeight += tabExpandSpeed * 2;
				else {
					barHeight = maxHeight;
					open = true;
				}
			} else {
				
				if(barWidth <= maxWidth - tabExpandSpeed)
					barWidth += tabExpandSpeed;
				else {
					barWidth = maxWidth;
					fullOpen = true;
				}
			}
		} else {
			
			if(energyHeight <= tileEntity.getScaledEnergyLevel(90) - tabExpandSpeed)
				energyHeight += tabExpandSpeed;
			else 
				energyHeight = tileEntity.getScaledEnergyLevel(90);
		}
		
	}
	
	@Override
	public void updateScreen() {
		
		if(fullOpen == true && alpha < 0xFF) {
			alpha += 16;
			aAlpha = alpha << 24;
			color = aAlpha + 0xFFFFFF;
		}
		
	}

	@Override
	public GuiTabs getGuiTab() {
		
		return GuiTabs.HOME;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0)
			for (GuiButtonCore guibutton : GuiButtons)
				if (guibutton.isMouseHovered(mc, mouseX, mouseY)) {
					FMLCommonHandler.instance().showGuiScreen(new GuiTabNetwork(Lists.newArrayList(GuiTabs.NETWORK), mc.currentScreen));
					mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
				}

	}
	
	public void switchTab(GuiTabs tab) {
		//FMLCommonHandler.instance().showGuiScreen(tab.getGuiScreen(tabs));
		
	}
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		
		super.setWorldAndResolution(mc, width, height);
		GuiButtons.clear();
		initGui();
	}
	
	@Override
	public void drawText(String text, int x, int y) {
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text)/2+11, y, 0xFFFFFF);
	}
	
}
