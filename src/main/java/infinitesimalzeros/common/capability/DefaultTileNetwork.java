package infinitesimalzeros.common.capability;

import infinitesimalzeros.common.capability.DefaultStorageHelper.NullStorage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class DefaultTileNetwork implements ITileNetwork {

	@Override
	public void handlePacketData(ByteBuf dataStream) throws Exception {}

	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data){
		return data;
	}
	
	public static void register(){
        CapabilityManager.INSTANCE.register(ITileNetwork.class, new NullStorage<>(), DefaultTileNetwork::new);
	}
	
}
