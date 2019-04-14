package infinitesimalzeros.common.unification;

import static infinitesimalzeros.common.unification.OrePrefix.Flags.DISALLOW_RECYCLING;
import static infinitesimalzeros.common.unification.OrePrefix.Flags.ENABLE_UNIFICATION;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import infinitesimalzeros.common.unification.material.DustMaterial;
import infinitesimalzeros.common.unification.material.IngotMaterial;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum OrePrefix {
	
	dust("Dusts", 3268800, null, MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial),
	ingot("Ingots", 3268800, null, MaterialIconType.ingot, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof IngotMaterial);
	
	public final String categoryName;
	public final long materialAmount;
	
	public final boolean isUnificationEnabled;
	public final boolean isRecyclingDisallowed;
	
	public final @Nullable MaterialIconType materialIconType;
	public final @Nullable Predicate<MaterialBasis> generationCondition;
	
	public byte maxStackSize = 64;
	
	public static class Flags {
		
		public static final long ENABLE_UNIFICATION = 1;
		public static final long DISALLOW_RECYCLING = 2;
	}
	
	OrePrefix(String categoryName, long materialAmount, MaterialBasis material, MaterialIconType materialIconType, long flags, Predicate<MaterialBasis> condition) {
		
		this.categoryName = categoryName;
		this.materialAmount = materialAmount;
		this.isUnificationEnabled = (flags & ENABLE_UNIFICATION) != 0;
		this.isRecyclingDisallowed = (flags & DISALLOW_RECYCLING) != 0;
		this.materialIconType = materialIconType;
		this.generationCondition = condition;
	}
	
	private final Set<MaterialBasis> ignoredMaterials = new HashSet<>();
	
	public static OrePrefix getPrefix(String prefixName) {
		
		return getPrefix(prefixName, null);
	}
	
	public static OrePrefix getPrefix(String prefixName, @Nullable OrePrefix replacement) {
		
		try {
			return Enum.valueOf(OrePrefix.class, prefixName);
		} catch (IllegalArgumentException invalidPrefixName) {
			return replacement;
		}
	}
	
	public boolean doGenerateItem(MaterialBasis material) {
		
		return generationCondition != null && !isIgnored(material) && generationCondition.test(material);
	}
	
	public boolean isIgnored(MaterialBasis material) {
		
		return ignoredMaterials.contains(material);
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalNameForItem(MaterialBasis material) {
		
		String specfiedUnlocalized = "item." + material.toString() + "." + this.name();
		if(I18n.hasKey(specfiedUnlocalized))
			return I18n.format(specfiedUnlocalized);
		String unlocalized = "item.material.oreprefix." + this.name();
		String matLocalized = material.getLocalizedName();
		String formatted = I18n.format(unlocalized, matLocalized);
		return formatted.equals(unlocalized) ? matLocalized : formatted;
	}
	
}
