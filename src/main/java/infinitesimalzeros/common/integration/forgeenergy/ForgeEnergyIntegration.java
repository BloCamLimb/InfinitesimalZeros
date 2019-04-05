package infinitesimalzeros.common.integration.forgeenergy;

import infinitesimalzeros.common.util.interfaces.IEnergyWrapper;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class ForgeEnergyIntegration implements IEnergyStorage
{
	public IEnergyWrapper tileEntity;
	
	public EnumFacing side;
	
	public ForgeEnergyIntegration(IEnergyWrapper tile, EnumFacing facing)
	{
		tileEntity = tile;
		side = facing;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) 
	{
		return IZToForge(tileEntity.receiveEnergy(side, forgeToIZ(maxReceive), simulate));
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) 
	{
		return IZToForge(tileEntity.transEnergy(side, forgeToIZ(maxExtract), simulate));
	}

	@Override
	public int getEnergyStored() 
	{
		return Math.min(Integer.MAX_VALUE, IZToForge(tileEntity.getEnergy()));
	}

	@Override
	public int getMaxEnergyStored()
	{
		return Math.min(Integer.MAX_VALUE, IZToForge(tileEntity.getMaxEnergy()));
	}

	@Override
	public boolean canExtract() 
	{
		return tileEntity.sideIsOutput(side);
	}

	@Override
	public boolean canReceive() 
	{
		return tileEntity.sideIsConsumer(side);
	}

	public static double forgeToIZ(int forge){
		return forge;
	}

	public static int IZToForge(double iz){
		return (int)Math.round(iz);
	}
}
