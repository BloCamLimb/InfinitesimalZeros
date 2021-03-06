package infinitesimalzeros.common.tileentities.basic;

import infinitesimalzeros.api.interfaces.IEnergyWrapper;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.capabilities.CapabilityWrapperManager;
import infinitesimalzeros.common.integration.ForgeEnergyIntegration;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional.Method;

public abstract class TileEntityElectricBlock extends TileEntityBasicBlock implements IEnergyWrapper {
	
	public double electricityStored;
	
	public double maxEnergy;
	
	public TileEntityElectricBlock(double baseMaxEnergy) {
		
		maxEnergy = baseMaxEnergy;
	}
	
	@Override
	public void onLoad() {
		
		super.onLoad();
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public boolean sideIsOutput(EnumFacing side) {
		
		return false;
	}
	
	@Override
	public boolean sideIsInput(EnumFacing side) {
		
		return true;
	}
	
	@Override
	public double getMaxOutput() {
		
		return 0;
	}
	
	@Override
	public double getEnergy() {
		
		return electricityStored;
	}
	
	@Override
	public void setEnergy(double energy) {
		
		electricityStored = Math.max(Math.min(energy, getMaxEnergy()), 0);
	}
	
	@Override
	public double getMaxEnergy() {
		
		return maxEnergy;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			//setEnergy(dataStream.readDouble());
		}
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		//data.add(getEnergy());
		
		return data;
	}
	
	@Override
	public void onChunkUnload() {
		
		super.onChunkUnload();
	}
	
	@Override
	public void invalidate() {
		
		super.invalidate();
	}
	
	@Override
	public void validate() {
		
		super.validate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		electricityStored = nbtTags.getDouble("electricityStored");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setDouble("electricityStored", getEnergy());
		
		return nbtTags;
	}
	
	/**
	 * Gets the scaled energy level for the GUI.
	 * 
	 * @param i - multiplier
	 * @return scaled energy
	 */
	public int getScaledEnergyLevel(int i) {
		
		return (int) (getEnergy() * i / getMaxEnergy());
	}
	
	@Override
	public boolean canTransmitMicroEnergy(EnumFacing side) {
		
		return sideIsOutput(side);
	}
	
	@Override
	public boolean canReceiveMicroEnergy(EnumFacing side) {
		
		return sideIsInput(side);
	}
	
	/*@Override
	@Method(modid = "redstoneflux")
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		
		return (int) Math.round(Math.min(Integer.MAX_VALUE, receiveMicroEnergy(from, maxReceive, simulate)));
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		
		return (int) Math.round(Math.min(Integer.MAX_VALUE, transmitMicroEnergy(from, maxExtract, simulate)));
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		
		return sideIsInput(from) || sideIsOutput(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getEnergyStored(EnumFacing from) {
		
		return (int) Math.round(Math.min(Integer.MAX_VALUE, getEnergy()));
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getMaxEnergyStored(EnumFacing from) {
		
		return (int) Math.round(Math.min(Integer.MAX_VALUE, getMaxEnergy()));
	}*/
	
	@Override
	public double receiveMicroEnergy(EnumFacing side, double amount, boolean simulate) {
		
		double toUse = Math.min(getMaxEnergy() - getEnergy(), amount);
		
		if(toUse < 0.0001 || (side != null && !sideIsInput(side))) {
			return 0;
		}
		
		if(!simulate) {
			setEnergy(getEnergy() + toUse);
		}
		
		return toUse;
	}
	
	@Override
	public double transmitMicroEnergy(EnumFacing side, double amount, boolean simulate) {
		
		double toGive = Math.min(getEnergy(), amount);
		
		if(toGive < 0.0001 || (side != null && !sideIsOutput(side))) {
			return 0;
		}
		
		if(!simulate) {
			setEnergy(getEnergy() - toGive);
		}
		
		return toGive;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY && maxEnergy > 0)
			return capability == CapabilityEnergy.ENERGY;
		
		return super.hasCapability(capability, facing);
	}
	
	private CapabilityWrapperManager<IEnergyWrapper, ForgeEnergyIntegration> forgeEnergyManager = new CapabilityWrapperManager<>(IEnergyWrapper.class, ForgeEnergyIntegration.class);
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY & maxEnergy > 0) {
			return (T) forgeEnergyManager.getWrapper(this, facing);
		}
		
		return super.getCapability(capability, facing);
	}
	
}
