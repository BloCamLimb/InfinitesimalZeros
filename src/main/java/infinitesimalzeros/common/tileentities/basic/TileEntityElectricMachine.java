package infinitesimalzeros.common.tileentities.basic;

import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.common.core.InventoryHandlerZero;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.IZUtils.ResourceType;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityElectricMachine extends TileEntityBasicMachine {
	
	public TileEntityElectricMachine(String name, double maxEnergy) {
		
		super(maxEnergy);
		
	}
	
}
