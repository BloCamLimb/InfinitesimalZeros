package infinitesimalzeros.common.network;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.capability.Capabilities;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.util.CapabilityUtils;
import infinitesimalzeros.common.util.handlers.PacketHandler;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileEntity implements IMessageHandler<TileEntityMessage, IMessage> {

	@Override
	public IMessage onMessage(TileEntityMessage message, MessageContext context) 
	{
		EntityPlayer player = PacketHandler.getPlayer(context);
		
		if(player == null)
		{
			return null;
		}
		
		PacketHandler.handlePacket(() ->
		{
            TileEntity tileEntity = message.coord4D.getTileEntity(player.world);

            if(CapabilityUtils.hasCapability(tileEntity, Capabilities.TILE_NETWORK_CAPABILITY, null))
            {
                ITileNetwork network = CapabilityUtils.getCapability(tileEntity, Capabilities.TILE_NETWORK_CAPABILITY, null);

                try {
                    network.handlePacketData(message.storedBuffer);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                message.storedBuffer.release();
            }
        }, player);
		
		return null;
	}
	
	public static class TileEntityMessage implements IMessage
	{
		public Coord4D coord4D;
	
		public TileNetworkList parameters;
		
		public ByteBuf storedBuffer = null;
		
		public TileEntityMessage() {}
	
		public TileEntityMessage(Coord4D coord, TileNetworkList params)
		{
			coord4D = coord;
			parameters = params;
		}
	
		@Override
		public void toBytes(ByteBuf dataStream)
		{
			coord4D.write(dataStream);
			
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			
			if(server != null)
			{
				World world = server.getWorld(coord4D.dimensionId);
			}
			
			PacketHandler.encode(parameters.toArray(), dataStream);
		}
	
		@Override
		public void fromBytes(ByteBuf dataStream)
		{
			coord4D = Coord4D.read(dataStream);
			
			storedBuffer = dataStream.copy();
		}
	}
}
