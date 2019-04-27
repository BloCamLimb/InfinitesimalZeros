package infinitesimalzeros.common.registry;

import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RegistryRecipes implements IRecipe {
	
	public static void initMainRecipes() {
		RecipeCoreT1.initialize();
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getRegistryName() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFit(int width, int height) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getRecipeOutput() {
		
		// TODO Auto-generated method stub
		return null;
	}
}
