package infinitesimalzeros.common.capabilities;

import infinitesimalzeros.api.interfaces.ITileNetwork;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {
	
	@CapabilityInject(ITileNetwork.class)
	public static Capability<ITileNetwork> TILE_NETWORK_CAPABILITY = null;
	
	public static void register() {
		
		DefaultTileNetwork.register();
	}
	
}
