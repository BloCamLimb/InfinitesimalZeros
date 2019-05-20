package infinitesimalzeros.common.registry;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.recipe.core.RecipeCoreT2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RegistryRecipes {
	
	public static void initMainRecipes() {
		RecipeCoreT1.initialize();
		RecipeCoreT2.initialize();
		regsiterCraftingRecipe();
	}

	public static void regsiterCraftingRecipe() {
		
		addShapedOreRecipe(new ItemStack(RegistryItems.card), new Object[] {"   ", "RSL", "   ", 'R', "dustRedstone", 'S', "ingotIron", 'L', "gemLapis"});
		addShapedOreRecipe(new ItemStack(RegistryItems.wrench), new Object[] {" GO", " OG", "O  ", 'G', "ingotGold", 'O', "obsidian"});
		addShapedOreRecipe(new ItemStack(RegistryItems.TEBlockItem1, 1, 1), new Object[] {"   ", "O O", "BBB", 'O', "obsidian", 'B', new ItemStack(Blocks.CONCRETE, 1, 15)});
	}
	
	private static final Map<String, Integer> RECIPE_COUNT_MAP = new HashMap<String, Integer>();
	
	public static void addShapedOreRecipe(Object out, Object... inputs) {
		registerRecipe(ShapedOreRecipe.class, out, inputs);
	}
	
	public static void addShapelessOreRecipe(Object out, Object... inputs) {
		registerRecipe(ShapelessOreRecipe.class, out, inputs);
	}

	
	public static ItemStack fixItemStack(Object object) {
		if (object instanceof ItemStack) {
			ItemStack stack = ((ItemStack) object).copy();
			if (stack.getCount() == 0) {
				stack.setCount(1);
			}
			return stack;
		} else if (object instanceof Item) {
			return new ItemStack((Item) object, 1);
		} else {
			if (!(object instanceof Block)) {
				throw new RuntimeException(String.format("Invalid ItemStack: %s", object));
			}
			return new ItemStack((Block) object, 1);
		}
	}
	
	public static void registerRecipe(Class<? extends IRecipe> clazz, Object out, Object... inputs) {
		if (out == null || Arrays.asList(inputs).contains(null)) return;
		ItemStack outStack = fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null) {
			String outName = outStack.getUnlocalizedName();
			if (RECIPE_COUNT_MAP.containsKey(outName)) {
				int count = RECIPE_COUNT_MAP.get(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;	
			} else RECIPE_COUNT_MAP.put(outName, 1);
			ResourceLocation location = new ResourceLocation(InfinitesimalZeros.MODID, outName);
			try {
				IRecipe recipe = newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			} catch (Exception e) {
				
			}
		}
	}
	
	public static <T> T newInstance(Class<T> clazz, Object... args) throws Exception {
		Constructor<T> constructor = clazz.getConstructor(getClasses(args));
		return constructor.newInstance(args);
	}
	
	public static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; i++) classes[i] = objects[i].getClass();
		return classes;
	}
}
