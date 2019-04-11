package infinitesimalzeros.common.network;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.capability.Capabilities;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.util.CapabilityUtils;
import infinitesimalzeros.common.util.handlers.PacketHandler;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDataRequest implements IMessageHandler<DataRequestMessage, IMessage> {
	
	@Override
	public IMessage onMessage(DataRequestMessage message, MessageContext context) {
		
		EntityPlayer player = PacketHandler.getPlayer(context);
		
		PacketHandler.handlePacket(() -> {
			World worldServer = DimensionManager.getWorld(message.coord4D.dimensionId);
			
			if(worldServer != null) {
				TileEntity tileEntity = message.coord4D.getTileEntity(worldServer);

                if(CapabilityUtils.hasCapability(tileEntity, Capabilities.TILE_NETWORK_CAPABILITY, null))
                {
                    ITileNetwork network = CapabilityUtils.getCapability(tileEntity, Capabilities.TILE_NETWORK_CAPABILITY, null);

                    PacketHandler.sendTo(new TileEntityMessage(Coord4D.get(tileEntity), network.getNetworkedData(new TileNetworkList())), (EntityPlayerMP)player);
                }
			}
		}, player);
		
		return null;
	}
	
	public static class DataRequestMessage implements IMessage {
		
		public Coord4D coord4D;
		
		public DataRequestMessage() {}
		
		public DataRequestMessage(Coord4D coord) {
			
			coord4D = coord;
		}
		
		@Override
		public void toBytes(ByteBuf dataStream) {
			
			coord4D.write(dataStream);
		}
		
		@Override
		public void fromBytes(ByteBuf dataStream) {
			
			coord4D = Coord4D.read(dataStream);
		}
	}
	
}
