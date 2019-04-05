package infinitesimalzeros.common.unification;

import com.google.common.base.CaseFormat;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraft.util.ResourceLocation;

public enum MaterialIconType {
	
	dust,
	ingot;

    public ResourceLocation getItemModelPath(MaterialSetIcon materialIconSet) {
        String iconSet = materialIconSet.name().toLowerCase();
        String iconType = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name());
        return new ResourceLocation(InfinitesimalZeros.MODID, "material_sets/" + iconSet + "/" + iconType);
    }

}
