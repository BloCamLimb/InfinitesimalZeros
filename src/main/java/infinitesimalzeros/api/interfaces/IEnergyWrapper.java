package infinitesimalzeros.api.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

@InterfaceList({ @Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"), @Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux") })
public interface IEnergyWrapper extends IMicroEnergyReceiver, IMicroEnergyTransmitter, IMicroEnergyStorage, IEnergyReceiver, IEnergyProvider {
	
	boolean sideIsOutput(EnumFacing side);
	
	boolean sideIsConsumer(EnumFacing side);
	
	double getMaxOutput();
	
}