package infinitesimalzeros.common.unification.material;

import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.unification.MaterialSetIcon;

public class DustMaterial extends MaterialBasis {
	
    public long burnTime;

	public DustMaterial(int metaItemSubId, String name, int materialRGB, MaterialSetIcon materialIconSet) {
        super(metaItemSubId, name, materialRGB, materialIconSet);
    }

}
