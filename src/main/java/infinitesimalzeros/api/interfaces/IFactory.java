package infinitesimalzeros.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IFactory {
	
	void setRecipeType(int type, ItemStack itemStack);
	
	/*
	 * public static enum RecipeType implements IStringSerializable {
	 * 
	 * ;
	 * 
	 * private MachineType type;
	 * 
	 * @Override public String getName() { return name().toLowerCase(Locale.ROOT); }
	 * 
	 * public MachineType getType() { return type; }
	 * 
	 * }
	 */
	
}
