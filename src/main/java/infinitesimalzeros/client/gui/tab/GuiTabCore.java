package infinitesimalzeros.client.gui.tab;

import java.io.IOException;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.GuiContainerCore;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.common.container.ContainerEmpty;
import infinitesimalzeros.common.registry.RegistrySounds;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class GuiTabCore extends GuiContainerCore {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/gui_blank.png");
	
	public static GuiScreen parent;
	
	public GuiTabCore() {
		
		super(new ContainerEmpty());
	}

	public GuiTabCore(GuiScreen g) {
		
		super(new ContainerEmpty());
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
		NavigationButtons.add(new NavigationButton(0, width / 2 - 60, height / 2 - 116, 0, "Security"));
		NavigationButtons.add(new NavigationButton(0, width / 2 - 75, height / 2 - 116, 1, "Home"));
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
	
	public static GuiScreen getParent() {
		return parent;
	}
	
}
