package infinitesimalzeros.client.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.common.recipe.NanaSmelterRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class NanaSmelterRecipeWrapper implements IRecipeWrapper {
	
	private NanaSmelterRecipe recipe;
	
	public NanaSmelterRecipeWrapper(IGuiHelper guiHelper, NanaSmelterRecipe recipe) {
		
		this.recipe = recipe;
		
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getPrimaryOutput());
		
	}
	
	
	
}
