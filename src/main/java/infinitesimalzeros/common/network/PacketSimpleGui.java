package infinitesimalzeros.common.network;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketSimpleGui.SimpleGuiMessage;
import infinitesimalzeros.common.tileentities.basis.TileEntityBasicBlock;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSimpleGui implements IMessageHandler<SimpleGuiMessage, IMessage> {

    @Override
    public IMessage onMessage(SimpleGuiMessage message, MessageContext context) {
    	
        EntityPlayer player = PacketHandler.getPlayer(context);

        PacketHandler.handlePacket(() -> {
        	
        	FMLNetworkHandler.openGui(player, InfinitesimalZeros.instance, message.guiId, player.world, message.coord4D.x, message.coord4D.y, message.coord4D.z);
        	
        }, player);

        return null;
    }

    public static class SimpleGuiMessage implements IMessage {

        public Coord4D coord4D;

        public int guiId;

        public SimpleGuiMessage() {}

        public SimpleGuiMessage(Coord4D coord, int gui) {
        	
            coord4D = coord;
            guiId = gui;
        }

        @Override
        public void toBytes(ByteBuf dataStream) {
        	
            coord4D.write(dataStream);
            dataStream.writeInt(guiId);
        }

        @Override
        public void fromBytes(ByteBuf dataStream) {
        	
            coord4D = Coord4D.read(dataStream);
            guiId = dataStream.readInt();
        }
    }
	
}
