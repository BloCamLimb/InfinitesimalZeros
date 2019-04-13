package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.api.interfaces.ITileNetwork;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBoundingBox extends TileEntity implements ITileNetwork {
	
	public BlockPos corePos = BlockPos.ORIGIN;
	
	public void setCorePosition(BlockPos pos) {
		
		if(!world.isRemote) {
			
			corePos = pos;
			
			PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
		}
	}
	
	@Override
	public void validate() {
		
		super.validate();
		
		if(world.isRemote) {
			
			PacketHandler.sendToServer(new DataRequestMessage(Coord4D.get(this)));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		compound.setInteger("main-X", corePos.getX());
		compound.setInteger("main-Y", corePos.getY());
		compound.setInteger("main-Z", corePos.getZ());
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		corePos = new BlockPos(compound.getInteger("main-X"), compound.getInteger("main-Y"), compound.getInteger("main-Z"));
		
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		if(world.isRemote) {
			
			corePos = new BlockPos(dataStream.readInt(), dataStream.readInt(), dataStream.readInt());
		}
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		data.add(corePos.getX());
		data.add(corePos.getY());
		data.add(corePos.getZ());
		
		return data;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == Capabilities.TILE_NETWORK_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY) {
			return (T) this;
		}
		
		return super.getCapability(capability, facing);
	}
	
}
