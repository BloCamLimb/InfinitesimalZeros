package infinitesimalzeros.client.jei;

import infinitesimalzeros.common.registry.RegistryBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICore implements IModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(RegistryBlocks.BBBlock));
		
	}
	
}
