package infinitesimalzeros.common.capabilities;

import infinitesimalzeros.api.interfaces.ITileNetwork;
import infinitesimalzeros.common.capabilities.DefaultStorageHelper.NullStorage;
import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class DefaultTileNetwork implements ITileNetwork {
	
	@Override
	public void handlePacketData(ByteBuf dataStream) throws Exception {}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		return data;
	}
	
	public static void register() {
		
		CapabilityManager.INSTANCE.register(ITileNetwork.class, new NullStorage<>(), DefaultTileNetwork::new);
	}
	
}
