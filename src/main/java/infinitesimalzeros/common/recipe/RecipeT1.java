package infinitesimalzeros.common.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeT1 {
	
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
