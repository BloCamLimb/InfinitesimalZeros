package infinitesimalzeros.common.container;

import infinitesimalzeros.common.container.slot.SlotOutput;
import infinitesimalzeros.common.tileentities.TileEntityDryingPool;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSmelterAdv extends ContainerBasic {
	
	public ContainerSmelterAdv(InventoryPlayer player, TileEntityDryingPool tileEntity) {
		
		super(player, tileEntity);
		
		this.tileEntity = tileEntity;
		
		tileEntity.open(player.player);
		
		inv.openInventory(player.player);
		
		//addSlotToContainer(new Slot(inv, 0, 56, 10));
		//addSlotToContainer(new Slot(inv, 1, 76, 10));
		//addSlotToContainer(new Slot(inv, 2, 96, 10));
		//addSlotToContainer(new Slot(inv, 3, 116, 10));
		//addSlotToContainer(new Slot(inv, 4, 56, 30));
		//addSlotToContainer(new Slot(inv, 5, 76, 30));
		//addSlotToContainer(new Slot(inv, 6, 96, 30));
		//addSlotToContainer(new Slot(inv, 7, 116, 30));
		//addSlotToContainer(new Slot(inv, 8, 56, 50));
		//addSlotToContainer(new Slot(inv, 9, 76, 50));
		//addSlotToContainer(new Slot(inv, 10, 96, 50));
		//addSlotToContainer(new SlotOutput(this, inv, 11, 116, 50));
		
		//slotCount = inv.getSizeInventory();
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
		
	}
	
	
	
}
