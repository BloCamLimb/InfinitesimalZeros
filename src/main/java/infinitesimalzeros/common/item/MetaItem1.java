package infinitesimalzeros.common.item;

import infinitesimalzeros.common.unification.OrePrefix;
import infinitesimalzeros.common.item.MetaItems;

public class MetaItem1 extends MaterialMetaItem {
	
	public MetaItem1() {
		
		super(null, null, OrePrefix.dust, null, null, null, null, null, null, null, OrePrefix.ingot, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null);
	}
	
	@Override
	public void registerSubItems() {
		
		MetaItem<?>.MetaValueItem CREDIT_COPPER = addItem(0, "credit.copper");
	}
	
}
