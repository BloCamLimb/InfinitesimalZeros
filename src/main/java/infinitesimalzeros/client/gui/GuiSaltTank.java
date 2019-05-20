package infinitesimalzeros.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import cofh.core.util.helpers.RenderHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.client.gui.button.PowerButton;
import infinitesimalzeros.common.capabilities.Capabilities;
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
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiSaltTank extends GuiTileEntityCore<TileEntityFunctionalMachineT0> {
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/guidefault.png");
	private static final ResourceLocation WEATHER = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/weather_icon.png");
	private static final ResourceLocation GUI = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/salt_tank_gui.png");
	
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
	
	public int maxWidth = 99;
	public float barWidth;
	
	public int maxHeight = 140;
	public float barHeight;
	public int energyHeight;
	
	public GuiSaltTank(InventoryPlayer player, TileEntitySaltTank tileEntity) {
		
		super(tileEntity, new ContainerSaltTank(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;

	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String tileName = this.tileEntity.getDisplayName().getFormattedText();

		this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 3, -12, 0xFFFFFF);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		
		this.mc.getTextureManager().bindTexture(TEXTURES);
		
		this.drawTexturedModalRect(width / 2 - 128, height / 2 - 128, 0, 0, 256, 256);
		
		drawWeather();
		drawMain();

	}
	
	private void drawWeather() {
		
		GlStateManager.pushMatrix();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha / 320F);
		
		this.mc.getTextureManager().bindTexture(WEATHER);
		
		drawTexturedModalRect(width / 2 - 70, height / 2 - 80, tileEntity.getWorld().isRaining() ? 24 : tileEntity.isDaytime ? 0 : 48, 0, 24, 24);
		
		GlStateManager.popMatrix();
	}
	
	private void drawMain() {
		
		GlStateManager.pushMatrix();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(GUI);
		
		drawTexturedModalRect(width / 2 - 50, height / 2 - 30, 0, 0, 100, 16);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha / 255F);
		
		drawTexturedModalRect(width / 2 - 40, height / 2 - 50, tileEntity.isActive ? 100 : 130, 0, 30, 16);
		drawTexturedModalRect(width / 2 + 10, height / 2 - 50, tileEntity.isActive ? 100 : 130, 0, 30, 16);
		
		FluidStack fluid = tileEntity.inputTank.getFluid();
		
		if (fluid != null) {
			GlStateManager.pushMatrix();
			
			RenderHelper.setBlockTextureSheet();
			
			int color = fluid.getFluid().getColor(fluid);
			
			RenderHelper.setGLColorFromInt(color);
			
			int l = (int) Math.ceil(13 * fluid.amount / 8000.0);
			
			GlStateManager.color(1.0F, 1.0F, 1.0F, 0.8F);
			
			for(int i = 0; i < 7; i++) {
				drawTexturedModalRect(width / 2 - 49 + 14 * i, height / 2 - 16 - l, RenderHelper.getTexture(fluid.getFluid().getStill(fluid)), 14, l);
			}
			
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public void initGui() {
		
		super.initGui();

		NavigationButtons.add(new NavigationButton(width / 2 - 75, height / 2 - 116, NavigationButton.HOME));
		NavigationButtons.add(new NavigationButton(width / 2 - 60, height / 2 - 116, NavigationButton.SECURITY));
		NavigationButtons.add(new NavigationButton(width / 2 - 45, height / 2 - 116, NavigationButton.RECIPE));
		PowerButtons.add(new PowerButton(0, width / 2 + 60, height / 2 - 116));

	}
	
	@Override
	public void updateScreen() {
		
		if(alpha < 0xFF) {
			alpha += 16;
			aAlpha = alpha << 24;
			color = aAlpha + 0xFFFFFF;
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		super.keyTyped(typedChar, keyCode);

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
