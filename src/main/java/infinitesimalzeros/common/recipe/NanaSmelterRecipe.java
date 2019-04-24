package infinitesimalzeros.common.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class NanaSmelterRecipe {
	
	public final ItemStack input;
	public final ItemStack primaryOutput;
	public final int energy;

	public NanaSmelterRecipe(ItemStack input, ItemStack primaryOutput, int energy) {

		this.input = input;
		this.primaryOutput = primaryOutput;
		this.energy = energy;
	}

	public ItemStack getInput() {

		return input;
	}

	public ItemStack getPrimaryOutput() {

		return primaryOutput;
	}

	public int getEnergy() {

		return energy;
	}
	
	
	
	public static class NanaSmelterRecipeCore {
		
		protected ItemStack input;
		protected List<ItemStack> output;
		protected int energy;
		protected int time;
		
		
		public NanaSmelterRecipeCore(ItemStack input, List<ItemStack> output, int energy, int time) {
			
			this.input = input;
			this.output = new ArrayList<>();
			if (output != null) {
				this.output.addAll(output);
			}
			this.energy = energy;
			this.time = time;
			
		}
		
		public ItemStack getInput() {
			
			return input.copy();
		}
		
		public List<ItemStack> getOutput() {
			
			return output;
		}
		
		public int getEnergy() {
			
			return energy;
		}

		public int getTime() {
			
			return time;
		}

	}
	
}
