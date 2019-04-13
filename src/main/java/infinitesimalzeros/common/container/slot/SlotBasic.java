package infinitesimalzeros.common.container.slot;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class SlotBasic extends Slot {
	
	final Container container;
	
	public SlotBasic(Container container, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		
		super(inventoryIn, index, xPosition, yPosition);
		
		this.container = container;
	}
	
}
