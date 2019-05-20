package infinitesimalzeros.common.recipe.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cofh.core.inventory.ComparableItemStackValidated;
import cofh.core.util.helpers.FluidHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeCoreT2 {
	
	private static Map<Integer, RecipeT2> recipeMap = new Object2ObjectOpenHashMap<>();

	public static RecipeT2 getRecipe(FluidStack input) {

		return input == null ? null : recipeMap.get(FluidHelper.getFluidHash(input));
	}

	public static boolean recipeExists(FluidStack input) {

		return getRecipe(input) != null;
	}

	public static RecipeT2[] getRecipeList() {

		return recipeMap.values().toArray(new RecipeT2[0]);
	}

	public static void initialize() {

		addRecipe(0, new FluidStack(FluidRegistry.WATER, 240), new ItemStack(Items.COOKED_FISH));
	}

	public static void refresh() {

	}

	public static RecipeT2 addRecipe(int energy, FluidStack input, ItemStack output) {

		if (input == null || output.isEmpty() || recipeExists(input)) {
			return null;
		}
		RecipeT2 recipe = new RecipeT2(input, output, energy, 20);
		recipeMap.put(FluidHelper.getFluidHash(input), recipe);
		return recipe;
	}

	public static RecipeT2 removeRecipe(FluidStack input) {

		return recipeMap.remove(FluidHelper.getFluidHash(input));
	}
	
	public static class RecipeT2 {
		
		public final FluidStack input;
		public final ItemStack primaryOutput;
		public final int power;
		public final int time;

		public RecipeT2(FluidStack input, ItemStack primaryOutput, int power, int time) {

			this.input = input;
			this.primaryOutput = primaryOutput;
			this.power = power;
			this.time = time;
		}

		public FluidStack getInputFluid() {

			return input;
		}

		public ItemStack getOutputItem() {

			return primaryOutput;
		}

		public int getPower() {

			return power;
		}
		
		public int getTime() {
			
			return time;
		}
		
		public int getEnergy() {
			
			return power * time;
		}
		
	}
	
}
