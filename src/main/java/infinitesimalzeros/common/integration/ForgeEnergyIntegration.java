package infinitesimalzeros.common.integration;

import infinitesimalzeros.api.interfaces.IEnergyWrapper;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgeEnergyIntegration implements IEnergyStorage {
	
	public IEnergyWrapper tileEntity;
	
	public EnumFacing side;
	
	public ForgeEnergyIntegration(IEnergyWrapper tile, EnumFacing facing) {
		
		tileEntity = tile;
		side = facing;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		
		return URToForge(tileEntity.receiveMicroEnergy(side, forgeToUR(maxReceive), simulate));
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		
		return URToForge(tileEntity.transMicroEnergy(side, forgeToUR(maxExtract), simulate));
	}
	
	@Override
	public int getEnergyStored() {
		
		return Math.min(Integer.MAX_VALUE, URToForge(tileEntity.getEnergy()));
	}
	
	@Override
	public int getMaxEnergyStored() {
		
		return Math.min(Integer.MAX_VALUE, URToForge(tileEntity.getMaxEnergy()));
	}
	
	@Override
	public boolean canExtract() {
		
		return tileEntity.sideIsOutput(side);
	}
	
	@Override
	public boolean canReceive() {
		
		return tileEntity.sideIsConsumer(side);
	}
	
	public static double forgeToUR(int forge) {
		
		return forge;
	}
	
	public static int URToForge(double ur) {
		
		return (int) Math.round(ur);
	}
}
