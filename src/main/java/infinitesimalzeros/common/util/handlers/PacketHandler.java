package infinitesimalzeros.common.util.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.network.PacketDataRequest;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("InfinitesimalZeros");
	
	public static void Init() {
		
		network.registerMessage(PacketTileEntity.class, TileEntityMessage.class, 5, Side.CLIENT);
		network.registerMessage(PacketTileEntity.class, TileEntityMessage.class, 5, Side.SERVER);
		network.registerMessage(PacketDataRequest.class, DataRequestMessage.class, 7, Side.SERVER);
	}
	
	public static EntityPlayer getPlayer(MessageContext context) {
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			return context.getServerHandler().player;
		} else {
			return Minecraft.getMinecraft().player;
		}
	}
	
	public static void handlePacket(Runnable runnable, EntityPlayer player) {
		
		if(player == null || player.world.isRemote) {
			Minecraft.getMinecraft().addScheduledTask(runnable);
		} else if(player != null && !player.world.isRemote) {
			if(player.world instanceof WorldServer) {
				((WorldServer) player.world).addScheduledTask(runnable);
			} else {
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				if(server != null) {
					server.addScheduledTask(runnable);
				}
			}
		}
	}
	
	/**
	 * Encodes an Object[] of data into a DataOutputStream.
	 * 
	 * @param dataValues - an Object[] of data to encode
	 * @param output     - the output stream to write to
	 */
	public static void encode(Object[] dataValues, ByteBuf output) {
		
		try {
			for(Object data : dataValues) {
				if(data instanceof Byte) {
					output.writeByte((Byte) data);
				} else if(data instanceof Integer) {
					output.writeInt((Integer) data);
				} else if(data instanceof Short) {
					output.writeShort((Short) data);
				} else if(data instanceof Long) {
					output.writeLong((Long) data);
				} else if(data instanceof Boolean) {
					output.writeBoolean((Boolean) data);
				} else if(data instanceof Double) {
					output.writeDouble((Double) data);
				} else if(data instanceof Float) {
					output.writeFloat((Float) data);
				} else if(data instanceof String) {
					writeString(output, (String) data);
				} else if(data instanceof EnumFacing) {
					output.writeInt(((EnumFacing) data).ordinal());
				} else if(data instanceof ItemStack) {
					writeStack(output, (ItemStack) data);
				} else if(data instanceof NBTTagCompound) {
					writeNBT(output, (NBTTagCompound) data);
				} else if(data instanceof int[]) {
					for(int i : (int[]) data) {
						output.writeInt(i);
					}
				} else if(data instanceof byte[]) {
					for(byte b : (byte[]) data) {
						output.writeByte(b);
					}
				} else if(data instanceof ArrayList) {
					encode(((ArrayList<?>) data).toArray(), output);
				} else if(data instanceof NonNullList) {
					encode(((NonNullList) data).toArray(), output);
				} else {
					throw new RuntimeException("Un-encodable data passed to encode(): " + data + ", full data: " + Arrays.toString(dataValues));
				}
			}
		} catch (Exception e) {
			InfinitesimalZeros.logger.error("Error while encoding packet data.");
			e.printStackTrace();
		}
	}
	
	public static void writeString(ByteBuf output, String s) {
		
		ByteBufUtils.writeUTF8String(output, s);
	}
	
	public static String readString(ByteBuf input) {
		
		return ByteBufUtils.readUTF8String(input);
	}
	
	public static void writeStack(ByteBuf output, ItemStack stack) {
		
		ByteBufUtils.writeItemStack(output, stack);
	}
	
	public static ItemStack readStack(ByteBuf input) {
		
		return ByteBufUtils.readItemStack(input);
	}
	
	public static void writeNBT(ByteBuf output, NBTTagCompound nbtTags) {
		
		ByteBufUtils.writeTag(output, nbtTags);
	}
	
	public static NBTTagCompound readNBT(ByteBuf input) {
		
		return ByteBufUtils.readTag(input);
	}
	
	public static void sendTo(IMessage message, EntityPlayerMP player) {
		
		network.sendTo(message, player);
	}
	
	public static void sendToReceivers(IMessage message, Range4D range) {
		
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		if(server != null) {
			for(EntityPlayerMP player : (List<EntityPlayerMP>) server.getPlayerList().getPlayers()) {
				if(player.dimension == range.dimensionId && Range4D.getChunkRange(player).intersects(range)) {
					sendTo(message, player);
				}
			}
		}
	}
	
}
