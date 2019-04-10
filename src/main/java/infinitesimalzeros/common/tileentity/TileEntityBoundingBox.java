package infinitesimalzeros.common.tileentity;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.capability.Capabilities;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.util.handlers.PacketHandler;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBoundingBox extends TileEntity implements ITileNetwork {
	
	public BlockPos posCore = BlockPos.ORIGIN;
	
	public void setCorePosition(BlockPos pos) {
		
		if(!world.isRemote) {
			
			posCore = pos;
			
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
		
		compound.setInteger("main-X", posCore.getX());
		compound.setInteger("main-Y", posCore.getY());
		compound.setInteger("main-Z", posCore.getZ());
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		posCore = new BlockPos(compound.getInteger("main-X"), compound.getInteger("main-Y"), compound.getInteger("main-Z"));
		
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) throws Exception {
		
		if(!world.isRemote) {
			
			posCore = new BlockPos(dataStream.readInt(), dataStream.readInt(), dataStream.readInt());
		}
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		data.add(posCore.getX());
		data.add(posCore.getY());
		data.add(posCore.getZ());
		
		return null;
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
