package infinitesimalzeros.client.gui;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiModConfig extends GuiConfig {
	
	public GuiModConfig(GuiScreen parent) {
		
		super(parent, getConfigElements(), InfinitesimalZeros.MODID, false, false, InfinitesimalZeros.NAME);
	}
	
	private static List<IConfigElement> getConfigElements() {
		
		List<IConfigElement> list = new ArrayList<>();
		
		list.add(new ConfigElement(Config.cfg.getCategory(Config.General).setLanguageKey("config.iz.general")));
		
		return list;
		
	}
	
	@Override
	public void onGuiClosed() {
		
		super.onGuiClosed();
		
		Config.cfg.save();
		
		Config.read();
	}
	
}
