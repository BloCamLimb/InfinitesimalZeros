package infinitesimalzeros.client.gui;

import java.io.IOException;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.client.gui.button.PowerButton;
import infinitesimalzeros.common.container.ContainerCore;
import infinitesimalzeros.common.container.ContainerSaltTank;
import infinitesimalzeros.common.container.ContainerNanaSmelter;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.registry.RegistrySounds;
import infinitesimalzeros.common.tileentities.TileEntitySaltTank;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSaltTank extends GuiTileEntityCore<TileEntityFunctionalMachineT0> {
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/guidefault.png");
	private static final ResourceLocation SLIDEBAR = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/slide_bar.png");
	private static final ResourceLocation LAFFY = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/lafei_g.png");
	
	private final InventoryPlayer player;
	private final TileEntitySaltTank tileEntity;
	
	public int backgroundColor = 0xffffff;
	
	public boolean open;
	public boolean fullOpen;
	public boolean energyInit;
	public boolean close;
	public int alpha = -1;
	public int aAlpha;
	public int color;
	
	public static int tabExpandSpeed;
	
	public int maxWidth = 99;
	public int barWidth;
	
	public int maxHeight = 140;
	public int barHeight;
	public int energyHeight;
	
	public GuiSaltTank(InventoryPlayer player, TileEntitySaltTank tileEntity) {
		
		super(tileEntity, new ContainerSaltTank(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
		
		//addGUIWidget(new GuiSecurityWidget(this, tileEntityGui));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String tileName = this.tileEntity.getDisplayName().getFormattedText();
		//this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -8, 0xA8DAFF);
		this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -12, 0xFFFFFF);
		// this.fontRenderer.drawString(this.player.getCurrentItem().getDisplayName(),
		// 122, this.ySize - 96 + 2, 4210752);

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
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
		if(fullOpen)
			drawCat();
			drawEnergyBar();
		String energy = ""+(int) this.tileEntity.getEnergy();
		if(fullOpen == true && color != 0) {
			this.fontRenderer.drawString("Energy ", width / 2 - 194, height / 2 + 39, color);
			this.fontRenderer.drawString(energy+" RF", width / 2 - 132 - this.fontRenderer.getStringWidth(energy), height / 2 + 39, color);
		}
		//InfinitesimalZeros.logger.info(currentHeight);
		// int l = this.getCookProgressScaled(24);
		// this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 36, 176, 14, l +
		// 1, 16);
	}
	
	protected void drawCat() {
		
		float colorR = (backgroundColor >> 16 & 255) / 255.0F;
		float colorG = (backgroundColor >> 8 & 255) / 255.0F;
		float colorB = (backgroundColor & 255) / 255.0F;
		
		GlStateManager.pushMatrix();
		
		GlStateManager.color(colorR, colorG, colorB, alpha / 255.0F);
		
		this.mc.getTextureManager().bindTexture(LAFFY);
		this.drawTexturedModalRect(width / 2 - 168, height / 2 - 40, 0, 0, 53, 60);
		
		GlStateManager.popMatrix();
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
		this.drawTexturedModalRect(width / 2 - 188, height / 2 + 22 - energyHeight, 105, 0, 12, energyHeight);
		if(fullOpen && color != 0) {
			this.fontRenderer.drawString(energyHeight+"%", width / 2 - 182 - this.fontRenderer.getStringWidth(energyHeight+"%") / 2, height / 2 + 13 - energyHeight, color);
			this.mc.renderEngine.bindTexture(SLIDEBAR);
			this.fontRenderer.drawString(Math.round(tileEntity.getScaledProgress()*100)+"%", width / 2 - 160, height / 2 + 27, color);
			this.fontRenderer.drawString(!tileEntity.masterControl ? "Offline" : tileEntity.isActive ? "Online" : "Sleep", width / 2 - 136, height / 2 + 27, color);
		}
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		tabExpandSpeed = Math.max(1, Math.round(maxHeight / Minecraft.getDebugFPS()));
		//int i = 1;
		//buttonList.add(new GuiButton(1,0,0,20,20, ""));
		NavigationButtons.add(new NavigationButton(width / 2 - 75, height / 2 - 116, NavigationButton.HOME));
		NavigationButtons.add(new NavigationButton(width / 2 - 60, height / 2 - 116, NavigationButton.SECURITY));
		NavigationButtons.add(new NavigationButton(width / 2 - 45, height / 2 - 116, NavigationButton.RECIPE));
		PowerButtons.add(new PowerButton(0, width / 2 + 60, height / 2 - 116));
		/*for(GuiTabs tab : tabs) {
			
			//buttonList.add(new NavigationButton(this, tab, i, width/2-10, 60*i));
			i++;
		}*/
	}
	
	public void update() {
		
		if((!open || !fullOpen) && !close) {
			
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
		} else if(fullOpen) {
			
			if(energyHeight <= tileEntity.getScaledEnergyLevel(100) - tabExpandSpeed)
				energyHeight += tabExpandSpeed;
			else {
				energyHeight = tileEntity.getScaledEnergyLevel(100);
			}
		}
		
		if(close) {
			
			if(barWidth >= tabExpandSpeed) {
				barWidth -= tabExpandSpeed;
				energyHeight = 0;
				fullOpen = false;
			}
			else {
				barWidth = 0;
				//this.mc.player.closeScreen();
			}
		}
		
	}
	
	@Override
	public void updateScreen() {
		
		if(fullOpen == true) {
			if(alpha < 0xFF) {
				alpha += 16;
				aAlpha = alpha << 24;
				color = aAlpha + 0xFFFFFF;
			}
		} else {
			color = 0;
			alpha = -1;
		}
		
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		super.keyTyped(typedChar, keyCode);
		
		if(keyCode == 20 && open/* || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)*/) {
			close = close ? false : true;
        }
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if(mouseButton == 0)
			for (PowerButton buttons : PowerButtons)
				if(buttons.isMouseHovered(mc, mouseX, mouseY)) {
					
					if(tileEntity.masterControl) {
						TileNetworkList data = TileNetworkList.withContents(1);
						PacketHandler.sendToServer(new TileEntityMessage(Coord4D.get(tileEntity), data));
					} else {
						TileNetworkList data = TileNetworkList.withContents(2);
						PacketHandler.sendToServer(new TileEntityMessage(Coord4D.get(tileEntity), data));
					}
					
					mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
				}
	}
		
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		
		super.setWorldAndResolution(mc, width, height);
		
	}
	
}
