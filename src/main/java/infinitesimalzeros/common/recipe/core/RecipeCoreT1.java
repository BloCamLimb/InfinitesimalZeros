package infinitesimalzeros.common.recipe.core;

import java.util.Map;
import java.util.Map.Entry;

import cofh.core.inventory.ComparableItemStackValidated;
import cofh.core.inventory.OreValidator;
import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.recipe.NanaSmelterRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeCoreT1 {
	
	private static Map<ComparableItemStackValidated, NanaSmelterRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
	private static OreValidator oreValidator = new OreValidator();
	
	public static NanaSmelterRecipe getRecipe(ItemStack input) {

		if (input.isEmpty()) {
			return null;
		}
		
		ComparableItemStackValidated query = convertInput(input);

		NanaSmelterRecipe recipe = recipeMap.get(query);
		
		if (recipe == null) {
			query.metadata = OreDictionary.WILDCARD_VALUE;
			recipe = recipeMap.get(query);
		}
		
		return recipe;
	}

	public static boolean recipeExists(ItemStack input) {

		return getRecipe(input) != null;
	}

	public static NanaSmelterRecipe[] getRecipeList() {

		return recipeMap.values().toArray(new NanaSmelterRecipe[0]);
	}
	
	public static ComparableItemStackValidated convertInput(ItemStack stack) {

		return new ComparableItemStackValidated(stack, oreValidator);
	}
	
	public static void refresh() {

		Map<ComparableItemStackValidated, NanaSmelterRecipe> tempMap = new Object2ObjectOpenHashMap<>(recipeMap.size());
		NanaSmelterRecipe tempRecipe;

		for (Entry<ComparableItemStackValidated, NanaSmelterRecipe> entry : recipeMap.entrySet()) {
			tempRecipe = entry.getValue();
			tempMap.put(convertInput(tempRecipe.input), tempRecipe);
		}
		recipeMap.clear();
		recipeMap = tempMap;
	}

	/* ADD RECIPES */
	public static NanaSmelterRecipe addRecipe(int power, ItemStack input, ItemStack primaryOutput, int time) {

		if (input.isEmpty() || primaryOutput.isEmpty() || power <= 0 || recipeExists(input)) {
			return null;
		}
		
		NanaSmelterRecipe recipe = new NanaSmelterRecipe(input, primaryOutput, power, time);
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
	public static NanaSmelterRecipe removeRecipe(ItemStack input) {

		return recipeMap.remove(convertInput(input));
	}
	
}
