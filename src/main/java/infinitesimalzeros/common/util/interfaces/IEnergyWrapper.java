package infinitesimalzeros.common.util.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import infinitesimalzeros.api.energy.IMicroEnergyReceiver;
import infinitesimalzeros.api.energy.IMicroEnergyStorage;
import infinitesimalzeros.api.energy.IMicroEnergyTransmitter;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

@InterfaceList({ @Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"), @Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux") })
public interface IEnergyWrapper extends IMicroEnergyStorage, IMicroEnergyReceiver, IMicroEnergyTransmitter, IEnergyReceiver, IEnergyProvider, IInventory {
	
	boolean sideIsOutput(EnumFacing side);
	
	boolean sideIsConsumer(EnumFacing side);
	
	double getMaxOutput();
	
}
