package infinitesimalzeros.client.gui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import infinitesimalzeros.api.interfaces.IGuiZero;
import infinitesimalzeros.client.gui.tab.GuiBasicTab.GuiTabs;
import infinitesimalzeros.client.gui.widget.GuiBasicWidget;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;


public abstract class GuiContainerCore extends GuiContainer implements IGuiZero {
	
	private Set<GuiBasicWidget> GUIWidgets = new HashSet<>();
	
	public GuiContainerCore(Container inventorySlotsIn) {
		
		super(inventorySlotsIn);
	}
	
	public abstract GuiTabs getGuiTab();
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		GUIWidgets.forEach(widget -> widget.clickEvent(xAxis, yAxis, mouseButton));
	}
	
	protected void addGUIWidget(GuiBasicWidget widget) {
		GUIWidgets.add(widget);
	}

	public abstract void drawText(String name, int x, int i);
	
	
	
}
