package infinitesimalzeros.client.gui.tab;

import java.io.IOException;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.client.gui.GuiContainerCore;
import infinitesimalzeros.client.gui.GuiTileEntityCore;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.client.gui.button.PowerButton;
import infinitesimalzeros.common.container.ContainerEmpty;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.registry.RegistrySounds;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicBlock;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class GuiTabCore extends GuiTileEntityCore {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/gui_blank.png");
	
	public static GuiScreen parent;
	//public static TileEntityFunctionalMachineT0 tileEntity;
	
	/*public GuiTabCore() {
		
		super(new ContainerEmpty());
	}

	public GuiTabCore(GuiScreen g) {
		
		super(new ContainerEmpty());
		parent = g;
	}*/
	
	public GuiTabCore(GuiScreen g, TileEntityFunctionalMachineT0 t) {
		
		super(t, new ContainerEmpty());
		parent = g;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(width / 2 - 128, height / 2 - 128, 0, 0, 256, 256);
		
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		NavigationButtons.add(new NavigationButton(width / 2 - 75, height / 2 - 116, NavigationButton.HOME));
		NavigationButtons.add(new NavigationButton(width / 2 - 60, height / 2 - 116, NavigationButton.SECURITY));
		NavigationButtons.add(new NavigationButton(width / 2 - 45, height / 2 - 116, NavigationButton.RECIPE));
		PowerButtons.add(new PowerButton(0, width / 2 + 60, height / 2 - 116));
		/*int i = 1;
		for(GuiTabs tab : tabs) {
			
			//buttonList.add(new NavigationButton(this, tab, i, width/2-10, 60*i));
			i++;
		}*/
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		super.actionPerformed(button);
		/*if(button instanceof NavigationButton) {
			switchTab(((NavigationButton)button).tab);
		}*/
		
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		//super.keyTyped(typedChar, keyCode);
		
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))
        {
			
			//PacketHandler.sendToServer(new SimpleGuiMessage(Coord4D.get(tileEntity), 0));
			mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
			FMLCommonHandler.instance().showGuiScreen(parent);
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
	
	public static GuiScreen getParent() {
		return parent;
	}
	
}
