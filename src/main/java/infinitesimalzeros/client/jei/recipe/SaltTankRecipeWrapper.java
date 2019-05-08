package infinitesimalzeros.client.jei.recipe;

import infinitesimalzeros.common.recipe.RecipeT2;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class SaltTankRecipeWrapper implements IRecipeWrapper {
	
	private RecipeT2 recipe;

	public SaltTankRecipeWrapper(IGuiHelper guiHelper, RecipeT2 recipe) {
		
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInput(VanillaTypes.FLUID, recipe.getInputFluid());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputItem());
		
	}
	
}
