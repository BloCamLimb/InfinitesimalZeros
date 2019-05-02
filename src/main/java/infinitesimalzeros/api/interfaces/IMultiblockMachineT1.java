package infinitesimalzeros.api.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import infinitesimalzeros.common.core.InventoryHandlerZero;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.items.IItemHandler;

public interface IMultiblockMachineT1 extends IMultiblockCore {
	
	/**
	 * Get first item handler for insertion.
	 */
	IItemHandler getInsertionHandler();
	
	/**
	 * Get first item handler for extraction.
	 */
	IItemHandler getExtractionHandler();
	
}
