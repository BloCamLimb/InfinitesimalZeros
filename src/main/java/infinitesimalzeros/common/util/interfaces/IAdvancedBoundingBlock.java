package infinitesimalzeros.common.util.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

@InterfaceList({
	@Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"),
	@Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")})

public interface IAdvancedBoundingBlock extends ICapabilityProvider, ISidedInventory, IBoundingBlock, IEnergyReceiver, IEnergyProvider {
	
	int[] getBoundSlots(BlockPos location, EnumFacing side);
	
	boolean canBoundInsert(BlockPos location, int i, ItemStack itemstack);

	boolean canBoundExtract(BlockPos location, int i, ItemStack itemstack, EnumFacing side);
	
	boolean canBoundReceiveEnergy(BlockPos location, EnumFacing side);
	
}
