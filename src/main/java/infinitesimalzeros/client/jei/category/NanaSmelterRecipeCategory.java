package infinitesimalzeros.client.jei.category;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.client.gui.GuiNanaSmelter;
import infinitesimalzeros.client.jei.CategoryIds;
import infinitesimalzeros.client.jei.RecipeCategoryCore;
import infinitesimalzeros.client.jei.recipe.NanaSmelterRecipeWrapper;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1.RecipeT1;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class NanaSmelterRecipeCategory extends RecipeCategoryCore<NanaSmelterRecipeWrapper> {
	
	public NanaSmelterRecipeCategory(IGuiHelper guiHelper) {
		
		name = I18n.format("tile.InfinitesimalZeros.Machine1.NanaSmelter.name");
		background = guiHelper.drawableBuilder(GuiNanaSmelter.TEXTURES, 64, 64, 128, 64).build();
	}
	
	public static void init(IModRegistry registry) {
		
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipes(getRecipes(guiHelper), CategoryIds.NanaSmelter);
		//registry.addRecipeClickArea(GuiNanaSmelter.class, 50, 50, 20, 20, CategoryIds.NanaSmelter);
		registry.addRecipeCatalyst(MachineTypes.Smelter.getStack(), CategoryIds.NanaSmelter);
	}
	
	public static List<NanaSmelterRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

		List<NanaSmelterRecipeWrapper> recipes = new ArrayList<>();

		for (RecipeT1 recipe : RecipeCoreT1.getRecipeList()) {
			recipes.add(new NanaSmelterRecipeWrapper(guiHelper, recipe));
		}
		return recipes;
	}

	@Override
	public String getUid() {
		
		return CategoryIds.NanaSmelter;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, NanaSmelterRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 42, 22);
		guiItemStacks.init(1, false, 95, 22);
		
		guiItemStacks.set(ingredients);
		
	}
	
	
	
}
