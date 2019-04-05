package infinitesimalzeros.common.capability;

import infinitesimalzeros.api.energy.IMicroEnergyReceiver;
import infinitesimalzeros.api.energy.IMicroEnergyStorage;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {
	
	@CapabilityInject(IMicroEnergyStorage.class)
    public static Capability<IMicroEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;
	
    @CapabilityInject(IMicroEnergyReceiver.class)
    public static Capability<IMicroEnergyReceiver> ENERGY_ACCEPTOR_CAPABILITY = null;

    @CapabilityInject(IMicroEnergyReceiver.class)
    public static Capability<IMicroEnergyReceiver> ENERGY_OUTPUTTER_CAPABILITY = null;
	
	@CapabilityInject(ITileNetwork.class)
	public static Capability<ITileNetwork> TILE_NETWORK_CAPABILITY = null;

}
