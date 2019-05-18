package infinitesimalzeros.common.config;

import java.util.Set;

import infinitesimalzeros.client.gui.GuiModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class ConfigGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}

	@Override
	public boolean hasConfigGui() {
		
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		
		return new GuiModConfig(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		
		return null;
	}
	
}
