package infinitesimalzeros.common.items.behaviour;

import infinitesimalzeros.api.interfaces.IItemColorProvider;
import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.unification.material.IngotMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;

public abstract class AbstractMaterialPartBehaviour implements IItemColorProvider {
	
	/*
	 * public IngotMaterial getPartMaterial(ItemStack stack) { NBTTagCompound nbt = stack.getTagCompound(); String materialName =
	 * nbt.getString("Material"); MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObject(materialName); return (IngotMaterial) material; }
	 * 
	 * @Override public int getItemStackColor(ItemStack stack, int colorIn) { IngotMaterial material = getPartMaterial(stack); return
	 * material.materialRGB; }
	 */
}
