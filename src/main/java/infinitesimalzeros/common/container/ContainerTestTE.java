package infinitesimalzeros.common.container;

import infinitesimalzeros.common.blocks.NanaFurnaceTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerTestTE extends ContainerCore {
	
	private final NanaFurnaceTE tileEntity;
	
	public ContainerTestTE(InventoryPlayer player, NanaFurnaceTE tileEntity) {
		
		super(player, tileEntity);
		
		this.tileEntity = tileEntity;
		
		tileEntity.open(player.player);
		
		addSlotToContainer(new Slot(inv, 0, 56, 50));
		//addSlotToContainer(new SlotOutput(tileEntity, 1, 106, 50));
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		
		super.onContainerClosed(playerIn);
		tileEntity.close(playerIn);
		//tileEntity.closeInventory(playerIn);
	}
	
}
