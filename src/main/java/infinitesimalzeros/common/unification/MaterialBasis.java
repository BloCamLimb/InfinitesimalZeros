package infinitesimalzeros.common.unification;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IMaterialHandler;
import infinitesimalzeros.common.util.ControlledRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class MaterialBasis implements Comparable<MaterialBasis> {
	
	public static final ControlledRegistry<String, MaterialBasis> MATERIAL_REGISTRY = new ControlledRegistry<>(1000);
	private static final List<IMaterialHandler> materialHandlers = new ArrayList<>();
	
	public final int materialRGB;
	
	public final MaterialSetIcon materialIconSet;
	
	public static void freezeRegistry() {
		
		MATERIAL_REGISTRY.freezeRegistry();
	}
	
	public MaterialBasis(int metaItemSubId, String name, int materialRGB, MaterialSetIcon materialIconSet) {
		
		this.materialRGB = materialRGB;
		this.materialIconSet = materialIconSet;
		InfinitesimalZeros.logger.info("Now Register "+name);
		registerMaterial(metaItemSubId, name);
	}
	
	public static void runMaterialHandlers() {
		
		materialHandlers.forEach(IMaterialHandler::onMaterialsInit);
	}
	
	public static void registerMaterialHandler(IMaterialHandler materialHandler) {
		
		materialHandlers.add(materialHandler);
	}
	
	protected void registerMaterial(int metaItemSubId, String name) {
		
		MATERIAL_REGISTRY.register(metaItemSubId, name, this);
	}
	
	public String getUnlocalizedName() {
		
		return "material." + toString();
	}
	
	public int compareTo(MaterialBasis material) {
		
		return toString().compareTo(material.toString());
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName() {
		
		return I18n.format(getUnlocalizedName());
	}
}
