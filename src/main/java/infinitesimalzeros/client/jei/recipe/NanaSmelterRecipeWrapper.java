package infinitesimalzeros.client.jei.recipe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cofh.core.util.helpers.ItemHelper;
import cofh.core.util.helpers.StringHelper;
import infinitesimalzeros.common.recipe.RecipeT1;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NanaSmelterRecipeWrapper implements IRecipeWrapper {
	
	private RecipeT1 recipe;
	
	private int energy;
	private int power;
	private int time;
	
	private List<List<ItemStack>> input;
	
	public NanaSmelterRecipeWrapper(IGuiHelper guiHelper, RecipeT1 recipe) {
		
		this.recipe = recipe;
		
		this.energy = recipe.getEnergy();
		this.power = recipe.getPower();
		this.time = recipe.getTime();
		List<ItemStack> recipeInputs = new ArrayList<>();
		
		int oreID = RecipeCoreT1.convertInput(recipe.getInput()).oreID;
		
		if (oreID != -1) {
			for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
				recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().getCount()));
			}
		} else
			recipeInputs.add(recipe.getInput());
		input = Collections.singletonList(recipeInputs);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getPrimaryOutput());
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		String energy1 = "Energy: " + StringHelper.formatNumber(energy) + " RF";
		minecraft.fontRenderer.drawString(energy1, 35, 38, 0xFFFFFF);
		String power1 = "Power: " + StringHelper.formatNumber(power) + " RF/t";
		minecraft.fontRenderer.drawString(power1, 35, 46, 0xFFFFFF);
		String time1 = "Time: " + StringHelper.formatNumber(time/20) + " s";
		minecraft.fontRenderer.drawString(time1, 35, 54, 0xFFFFFF);
	}
	
	
	
}
