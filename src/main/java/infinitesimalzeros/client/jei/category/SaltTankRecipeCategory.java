package infinitesimalzeros.client.jei.category;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.client.gui.GuiNanaSmelter;
import infinitesimalzeros.client.jei.CategoryIds;
import infinitesimalzeros.client.jei.RecipeCategoryCore;
import infinitesimalzeros.client.jei.recipe.SaltTankRecipeWrapper;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import infinitesimalzeros.common.recipe.core.RecipeCoreT2;
import infinitesimalzeros.common.recipe.core.RecipeCoreT2.RecipeT2;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;

public class SaltTankRecipeCategory extends RecipeCategoryCore<SaltTankRecipeWrapper> {
	
	public SaltTankRecipeCategory(IGuiHelper guiHelper) {
		
		name = I18n.format("tile.InfinitesimalZeros.Machine1.SaltTank.name");
		background = guiHelper.drawableBuilder(GuiNanaSmelter.TEXTURES, 64, 64, 128, 64).build();
	}
	
	public static void init(IModRegistry registry) {
		
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipes(getRecipes(guiHelper), CategoryIds.SaltTank);
		//registry.addRecipeClickArea(GuiDryingBed.class, 50, 50, 20, 20, CategoryIds.SaltTank);
		registry.addRecipeCatalyst(MachineTypes.SaltTank.getStack(), CategoryIds.SaltTank);
	}
	
	public static List<SaltTankRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

		List<SaltTankRecipeWrapper> recipes = new ArrayList<>();

		for (RecipeT2 recipe : RecipeCoreT2.getRecipeList()) {
			recipes.add(new SaltTankRecipeWrapper(guiHelper, recipe));
		}

		return recipes;
	}

	@Override
	public String getUid() {
		
		return CategoryIds.SaltTank;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SaltTankRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		guiItemStacks.init(0, false, 95, 22);
		guiFluidStacks.init(0, true, 15, 32, 70, 10, 500, true, null);
		
		guiItemStacks.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		guiFluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		
	}
	
}
