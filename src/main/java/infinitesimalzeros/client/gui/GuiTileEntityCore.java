package infinitesimalzeros.client.gui;

import java.io.IOException;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.client.gui.tab.GuiTabCore;
import infinitesimalzeros.common.registry.RegistrySounds;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicBlock;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.inventory.Container;

public abstract class GuiTileEntityCore<TileEntityT0 extends TileEntityFunctionalMachineT0> extends GuiContainerCore {
	
	protected TileEntityT0 tileEntity;

	public GuiTileEntityCore(TileEntityT0 tileEntity, Container inventorySlotsIn) {
		
		super(inventorySlotsIn);
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0)
			for (NavigationButton buttons : NavigationButtons)
				if (buttons.isMouseHovered(mc, mouseX, mouseY))
					if(mc.currentScreen instanceof GuiTabCore) {
						buttons.switchTab(buttons.buttonNavigationId, tileEntity);
						mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
						
					} else {
						buttons.switchTab(buttons.buttonNavigationId, tileEntity, mc.currentScreen);
						mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
					}
	}
	
}
