package infinitesimalzeros.api.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

@InterfaceList({
	@Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"),
	@Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")})
public interface IMultiblockCore extends ICapabilityProvider, IInventoryZero, IEnergyReceiver, IEnergyProvider {
	
	/**
	 * Get TileEntity localized name, there's an error when using NBTEdit which not compatible with ITextComponent in some cases.
	 */
	String getName();
	
	void onPlace(EnumFacing side);
	
	void onBreak();
	
}
