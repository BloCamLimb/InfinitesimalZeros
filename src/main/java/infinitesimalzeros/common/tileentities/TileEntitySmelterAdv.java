package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.common.core.handler.InventoryHandler;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TileEntitySmelterAdv extends TileEntityElectricMachine {
	
	public TileEntitySmelterAdv() {
		
		super("AdvS", 500000, 60, 200);
		
		size = 12;
		inventory = NonNullList.withSize(12, ItemStack.EMPTY);
		
		insertionHandler = new InventoryHandler(11, this, 0, true, false);
		extractionHandler = new InventoryHandler(1, this, 11, false, true);
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlace() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBreak() {
		
		// TODO Auto-generated method stub
		
	}
	
}
