package infinitesimalzeros.common.container;

import infinitesimalzeros.common.container.slot.SlotOutput;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.tileentities.TileEntityDryingPool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDryingBed extends ContainerBasic {

	public ContainerDryingBed(InventoryPlayer player, TileEntityDryingPool tileEntity) {
		
		super(player, tileEntity);
		
		this.tileEntity = tileEntity;
		
		tileEntity.open(player.player);
		
		inv.openInventory(player.player);
		
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, x * 19 + 4, 96 + y * 19));
			
		
		
		for(int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(player, x, 4 + x * 19, 159));
		
	}
	
}
