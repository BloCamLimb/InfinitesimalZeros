package infinitesimalzeros.client.gui.tab;

import java.io.IOException;
import java.util.List;

import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.common.container.ContainerEmpty;
import infinitesimalzeros.common.registry.RegistrySounds;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class GuiBasicTab extends GuiContainer {
	
	public List<GuiTabs> tabs;
	public GuiScreen parent;

	public GuiBasicTab(List<GuiTabs> tabs, GuiScreen g) {
		
		super(new ContainerEmpty());
		this.tabs = tabs;
		parent = g;
	}

	public abstract GuiTabs getGuiTab();
	
	public enum GuiTabs {
		HOME("Home"),
		NETWORK("Network setting");
		
		public String name;
		
		GuiTabs(String name) {
			this.name = name;
		}
		
		
		public String getName() {
			
			return name;
		}
		
		/*public Object getGuiScreen(List<GuiTabs> tabs) {
			switch(this) {
				case HOME:
					return new GuiTabHome(tabs, tileEntity);
				case NETWORK:
					return new GuiTabNetwork(tabs, tileEntity);
			}
			return null;
		}*/
		
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		/*int i = 1;
		for(GuiTabs tab : tabs) {
			
			//buttonList.add(new NavigationButton(this, tab, i, width/2-10, 60*i));
			i++;
		}*/
	}
	
	public void drawText(String text, int x, int y) {
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text)/2+11, y, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		super.actionPerformed(button);
		/*if(button instanceof NavigationButton) {
			switchTab(((NavigationButton)button).tab);
		}*/
	}
	
	public void switchTab(GuiTabs tab) {
		if(tab != getGuiTab()) {
			//FMLCommonHandler.instance().showGuiScreen(tab.getGuiScreen(tabs));
		}
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
	
}
