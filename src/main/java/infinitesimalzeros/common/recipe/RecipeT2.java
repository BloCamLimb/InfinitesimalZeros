package infinitesimalzeros.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeT2 {
	
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
