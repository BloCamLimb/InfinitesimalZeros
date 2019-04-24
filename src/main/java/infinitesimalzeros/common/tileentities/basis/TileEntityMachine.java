package infinitesimalzeros.common.tileentities.basis;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.api.interfaces.IActiveState;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class TileEntityMachine extends TileEntityElectricBlock implements IActiveState {
	
	public int updateDelay;
	
	public boolean isActive;
	
	public boolean clientActive;
	
	public double prevEnergy;
	
	public double BASE_ENERGY_PER_TICK;
	
	public double energyPerTick;
	
	public TileEntityMachine(double maxEnergy, double baseEnergyUsage) {
		
		super(maxEnergy);
		
		energyPerTick = BASE_ENERGY_PER_TICK = baseEnergyUsage;
		
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(world.isRemote && updateDelay > 0) {
			updateDelay--;
			
			if(updateDelay == 0 && clientActive != isActive) {
				isActive = clientActive;
				IZUtils.updateBlock(world, getPos());
			}
		}
		
		if(!world.isRemote) {
			if(updateDelay > 0) {
				updateDelay--;
				
				if(updateDelay == 0 && clientActive != isActive) {
					PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
				}
			}
		}
	}
	
	@Override
	public boolean canSetFacing(int facing) {
		
		return facing != 0 && facing != 1;
	}
	
	@Override
	public void setActive(boolean active) {
		
		isActive = active;
		
		if(clientActive != active && updateDelay == 0) {
			PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
			
			updateDelay = 10;
			clientActive = active;
		}
	}
	
	@Override
	public boolean getActive() {
		
		return isActive;
	}
	
	@Override
	public boolean renderUpdate() {
		
		return true;
	}
	
	@Override
	public boolean lightUpdate() {
		
		return true;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			clientActive = dataStream.readBoolean();
			energyPerTick = dataStream.readDouble();
			maxEnergy = dataStream.readDouble();
			
			if(updateDelay == 0 && clientActive != isActive) {
				updateDelay = 10;
				isActive = clientActive;
				IZUtils.updateBlock(world, getPos());
			}
		}
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		data.add(isActive);
		data.add(energyPerTick);
		data.add(maxEnergy);
		
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		isActive = nbtTags.getBoolean("isActive");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setBoolean("isActive", isActive);
		
		return nbtTags;
	}
	
}
