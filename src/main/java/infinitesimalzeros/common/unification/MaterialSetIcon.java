package infinitesimalzeros.common.unification;

import com.google.common.base.CaseFormat;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraft.util.ResourceLocation;

public enum MaterialSetIcon {
	
	NONE,
    METALLIC,
    DULL,
    MAGNETIC,
    QUARTZ,
    DIAMOND,
    EMERALD,
    SHINY,
    SHARDS,
    ROUGH,
    FINE,
    SAND,
    FLINT,
    RUBY,
    LAPIS,
    FLUID,
    GAS,
    LIGNITE,
    OPAL,
    GLASS,
    WOOD,
    LEAF,
    GEM_HORIZONTAL,
    GEM_VERTICAL,
    PAPER,
    NETHERSTAR;

    public String getName() {
        return name().toLowerCase();
    }

    public static MaterialSetIcon getByName(String name) {
        return valueOf(name.toUpperCase());
    }

    @Override
    public String toString() {
        return super.toString();
    }
    

}
