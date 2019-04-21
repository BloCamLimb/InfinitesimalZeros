package infinitesimalzeros.common.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class NanaSmelterRecipe {
	
	
	
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
