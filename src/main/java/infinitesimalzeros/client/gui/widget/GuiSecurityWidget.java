package infinitesimalzeros.client.gui.widget;

import com.google.common.collect.Lists;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IGuiZero;
import infinitesimalzeros.client.gui.tab.GuiBasicTab.GuiTabs;
import infinitesimalzeros.client.gui.tab.GuiTabNetwork;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketSimpleGui.SimpleGuiMessage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiSecurityWidget extends GuiBasicWidget<TileEntity> {
	
	public GuiSecurityWidget(IGuiZero gui, TileEntity tileEntity) {
		
		super(new ResourceLocation(InfinitesimalZeros.MODID + ":textures/gui/gui.png"), gui, tileEntity);
	}

	@Override
	public void clickEvent(int xAxis, int yAxis, int button) {
		
		if(button == 0 /*&& xAxis >= 30 && xAxis <= 40 && yAxis >=10 && yAxis <=20*/) {
			//PacketHandler.sendToServer(new SimpleGuiMessage(Coord4D.get(tileEntity), 0));
			//FMLCommonHandler.instance().showGuiScreen(new GuiTabNetwork(Lists.newArrayList(GuiTabs.NETWORK), tileEntity, mc.));
		}
	}
	
	
	
}
