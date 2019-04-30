package infinitesimalzeros.client.gui;

import java.io.IOException;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.button.NavigationButton;
import infinitesimalzeros.common.registry.RegistrySounds;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import infinitesimalzeros.common.tileentities.basis.TileEntityFunctionalMachineT0;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.inventory.Container;

public abstract class GuiTileEntityCore<TileEntityT0 extends TileEntityFunctionalMachineT0> extends GuiContainerCore {
	
	protected TileEntityT0 tileEntityT0;

	public GuiTileEntityCore(TileEntityT0 tileEntity, Container inventorySlotsIn) {
		
		super(inventorySlotsIn);
		this.tileEntityT0 = tileEntity;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0)
			for (NavigationButton buttons : NavigationButtons)
				if (buttons.isMouseHovered(mc, mouseX, mouseY))
					if(mc.currentScreen instanceof GuiTileEntityCore) {
						buttons.switchTab(buttons.buttonNavigationId, mc.currentScreen, tileEntityT0);
						mc.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RegistrySounds.BUTTONCLICK, 1.0F));
					}
	}
	
}
