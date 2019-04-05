package infinitesimalzeros.common.tileentity.basis;

import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityOperationalMachine extends TileEntityMachine {
	
	public int operatingTicks;

	public int BASE_TICKS_REQUIRED;

	public int ticksRequired;
	
	public TileEntityOperationalMachine(String name, double maxEnergy, double baseEnergyUsage, int upgradeSlot, int baseTicksRequired)
	{
		super(name, maxEnergy, baseEnergyUsage, upgradeSlot);
		
		ticksRequired = BASE_TICKS_REQUIRED = baseTicksRequired;
	}

	public double getScaledProgress()
	{
		return ((double)operatingTicks) / (double)ticksRequired;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream)
	{
		super.handlePacketData(dataStream);

		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			operatingTicks = dataStream.readInt();
			ticksRequired = dataStream.readInt();
		}
	}

	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data)
	{
		super.getNetworkedData(data);

		data.add(operatingTicks);
		data.add(ticksRequired);

		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTags)
	{
		super.readFromNBT(nbtTags);

		operatingTicks = nbtTags.getInteger("operatingTicks");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags)
	{
		super.writeToNBT(nbtTags);

		nbtTags.setInteger("operatingTicks", operatingTicks);
		
		return nbtTags;
	}
	
}
