package infinitesimalzeros.client.gui;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import infinitesimalzeros.api.interfaces.IGuiZero;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.client.gui.button.PowerButton;
import infinitesimalzeros.common.registry.RegistrySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;


public abstract class GuiContainerCore extends GuiContainer implements IGuiZero {
	
	protected List<NavigationButton> NavigationButtons = Lists.newArrayList();
	protected List<PowerButton> PowerButtons = Lists.newArrayList();
	
	public GuiContainerCore(Container inventorySlotsIn) {
		
		super(inventorySlotsIn);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		for(NavigationButton buttons : NavigationButtons) {
			buttons.drawButton(mc, mouseX, mouseY);
		}
		
		for(PowerButton buttons : PowerButtons) {
			buttons.drawButton(mc, mouseX, mouseY);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		
		super.setWorldAndResolution(mc, width, height);
		
		NavigationButtons.clear();
		PowerButtons.clear();
		initGui();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0)
			for (NavigationButton buttons : NavigationButtons)
				if (buttons.isMouseHovered(mc, mouseX, mouseY)) {
					if(mc.currentScreen instanceof GuiTileEntityCore)
						buttons.switchTab(buttons.buttonNavigationId, mc.currentScreen);
					else
						buttons.switchTab(buttons.buttonNavigationId);
					mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
				}
		
	}
	
	
}
