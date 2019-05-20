package infinitesimalzeros.common.recipe.core;

import java.util.Map;
import java.util.Map.Entry;

import cofh.core.inventory.ComparableItemStackValidated;
import cofh.core.inventory.OreValidator;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import infinitesimalzeros.InfinitesimalZeros;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeCoreT1 {
	
	private static Map<ComparableItemStackValidated, RecipeT1> recipeMap = new Object2ObjectOpenHashMap<>();
	private static OreValidator oreValidator = new OreValidator();
	
	static {
		oreValidator.addPrefix("ore");
	}
	
	public static RecipeT1 getRecipe(ItemStack input) {

		if (input.isEmpty()) {
			return null;
		}
		
		ComparableItemStackValidated query = convertInput(input);

		RecipeT1 recipe = recipeMap.get(query);
		
		if (recipe == null) {
			query.metadata = OreDictionary.WILDCARD_VALUE;
			recipe = recipeMap.get(query);
		}
		
		return recipe;
	}

	public static boolean recipeExists(ItemStack input) {

		return getRecipe(input) != null;
	}

	public static RecipeT1[] getRecipeList() {

		return recipeMap.values().toArray(new RecipeT1[0]);
	}
	
	public static ComparableItemStackValidated convertInput(ItemStack stack) {

		return new ComparableItemStackValidated(stack, oreValidator);
	}
	
	public static void refresh() {

		Map<ComparableItemStackValidated, RecipeT1> tempMap = new Object2ObjectOpenHashMap<>(recipeMap.size());
		RecipeT1 tempRecipe;

		for (Entry<ComparableItemStackValidated, RecipeT1> entry : recipeMap.entrySet()) {
			tempRecipe = entry.getValue();
			tempMap.put(convertInput(tempRecipe.input), tempRecipe);
		}
		recipeMap.clear();
		recipeMap = tempMap;
	}

	/* ADD RECIPES */
	public static RecipeT1 addRecipe(int power, ItemStack input, ItemStack primaryOutput, int time) {

		if (input.isEmpty() || primaryOutput.isEmpty() || power <= 0 || recipeExists(input)) {
			return null;
		}
		
		RecipeT1 recipe = new RecipeT1(input, primaryOutput, power, time);
		recipeMap.put(convertInput(input), recipe);
		return recipe;
	}
	
	public static void initialize() {

		addRecipe(30000, new ItemStack(Items.APPLE), new ItemStack(Items.NETHER_STAR), 200);
		{
			String oreType;
			for (String oreName : OreDictionary.getOreNames()) {
				if (oreName.startsWith("ore")) {
					oreType = oreName.substring(3, oreName.length());
					addDefaultRecipes(oreType);
				}
			}
		}
	}
	
	private static void addDefaultRecipes(String oreType) {

		if (oreType == null || oreType.isEmpty()) {
			return;
		}
		String suffix = StringHelper.titleCase(oreType);

		String oreName = "ore" + suffix;
		String gemName = "gem" + suffix;
		String dustName = "dust" + suffix;
		String ingotName = "ingot" + suffix;
		String blockName = "block" + suffix;

		String oreNetherName = "oreNether" + suffix;
		String oreEndName = "oreEnd" + suffix;

		ItemStack ore = ItemHelper.getOre(oreName);
		ItemStack gem = ItemHelper.getOre(gemName);
		ItemStack dust = ItemHelper.getOre(dustName);
		ItemStack ingot = ItemHelper.getOre(ingotName);
		ItemStack block = ItemHelper.getOre(blockName);

		ItemStack oreNether = ItemHelper.getOre(oreNetherName);
		ItemStack oreEnd = ItemHelper.getOre(oreEndName);


		addRecipe(2400, ore, ItemHelper.cloneStack(block, 1), 60);
	}

	/* REMOVE RECIPES */
	public static RecipeT1 removeRecipe(ItemStack input) {

		return recipeMap.remove(convertInput(input));
	}
	
	public static class RecipeT1 {
		
		public final ItemStack input;
		public final ItemStack primaryOutput;
		public final int power;
		public final int time;

		public RecipeT1(ItemStack input, ItemStack primaryOutput, int power, int time) {

			this.input = input;
			this.primaryOutput = primaryOutput;
			this.power = power;
			this.time = time;
		}

		public ItemStack getInput() {

			return input;
		}

		public ItemStack getPrimaryOutput() {

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
