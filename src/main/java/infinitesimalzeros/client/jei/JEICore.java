package infinitesimalzeros.client.jei;

import infinitesimalzeros.client.jei.category.NanaSmelterRecipeCategory;
import infinitesimalzeros.common.registry.RegistryBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEICore implements IModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(RegistryBlocks.BBBlock));
		NanaSmelterRecipeCategory.init(registry);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipeCategories(new NanaSmelterRecipeCategory(guiHelper));
		
		
	}
	
}
