package infinitesimalzeros.common.unification.material;

import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.unification.MaterialSetIcon;

public class IngotMaterial extends DustMaterial {
	
	public int materialRGB;
	
	public IngotMaterial(int metaItemSubId, String name, int materialRGB, MaterialSetIcon materialIconSet) {
		
		super(metaItemSubId, name, materialRGB, materialIconSet);
		
	}
	
}
