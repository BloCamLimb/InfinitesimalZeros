package infinitesimalzeros.client.jei;

import java.util.Arrays;
import java.util.List;

import infinitesimalzeros.client.jei.category.NanaSmelterRecipeCategory;
import infinitesimalzeros.client.jei.category.SaltTankRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class JEICore implements IModPlugin {
	
	private static IJeiRuntime jeiRuntime;
	
	@Override
	public void register(IModRegistry registry) {
		
		//registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(RegistryBlocks.BBBlock));
		NanaSmelterRecipeCategory.init(registry);
		SaltTankRecipeCategory.init(registry);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipeCategories(new NanaSmelterRecipeCategory(guiHelper));
		registry.addRecipeCategories(new SaltTankRecipeCategory(guiHelper));
		
		
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		
		this.jeiRuntime = jeiRuntime;
	}
	
	public static void showCraftingRecipes(String... id) {
		
	    jeiRuntime.getRecipesGui().showCategories(Arrays.asList(id));
	  }
	
}
