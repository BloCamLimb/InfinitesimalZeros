package infinitesimalzeros.common.tileentities.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.api.interfaces.ITileComponent;
import infinitesimalzeros.api.interfaces.ITileNetwork;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class TileEntityBasicBlock extends TileEntity implements ITickable, ITileNetwork {
	
	public EnumFacing facing = EnumFacing.NORTH;
	
	public HashSet<EntityPlayer> playersUsing = new HashSet<>();
	
	public boolean doAutoSync = true;
	
	public boolean doAutoAntiCheat = true;
	
	protected long c = 0;
	
	protected boolean cheated;
	
	protected boolean checkedSecurity;
	
	@Override
	public void update() {
		
		onUpdate();
		
		if(!world.isRemote && doAutoSync && playersUsing.size() > 0)
			sendPackets();
			
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		return new SPacketUpdateTileEntity(pos, -1, writeNetworkedNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return writeToNBT(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		
		readFromNBT(tag);
	}
	
	protected void sendPackets() {
		
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
	
	public void open(EntityPlayer player) {
		
		playersUsing.add(player);
	}
	
	public void close(EntityPlayer player) {
		
		playersUsing.remove(player);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		if(compound.hasKey("facing")) {
			facing = EnumFacing.getFront(compound.getInteger("facing"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		if(facing != null) {
			compound.setInteger("facing", facing.ordinal());
		}
		
		return compound;
	}

	protected NBTTagCompound writeNetworkedNBT(NBTTagCompound nbtTags) {
		
		writeToNBT(nbtTags);
		
		return nbtTags;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {}
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		return data;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == Capabilities.TILE_NETWORK_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY)
			return (T) this;
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void invalidate() {
		
		super.invalidate();
	}
	
	@Override
	public void validate() {
		
		super.validate();
		
		if(world.isRemote) {
			PacketHandler.network.sendToServer(new DataRequestMessage(Coord4D.get(this)));
		}
	}
	
	public abstract void onUpdate();
	
	public boolean canSetFacing(int facing) {
		
		return true;
	}
	
	public void setFacing(short direction) {
		
		facing = EnumFacing.getFront(direction);
	}
	
}
