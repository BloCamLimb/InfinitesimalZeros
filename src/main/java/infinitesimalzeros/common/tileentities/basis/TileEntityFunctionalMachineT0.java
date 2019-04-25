package infinitesimalzeros.common.tileentities.basis;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IBoundingBlock;
import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityFunctionalMachineT0 extends TileEntityBasicMachine implements IBoundingBlock {
	
	// Display name in GUI.
	public String name;
	
	// Control sleep state.
	public boolean isHighActivity;
	
	public boolean masterControl;
	
	// Machine item inventory list.
	public NonNullList<ItemStack> inventory;
	
	// Inventory size.
	public int size;
	
	// Primary Item Input Handler.
	public IItemHandler insertionHandler;
	
	// Primary Item Output Handler.
	public IItemHandler extractionHandler;
	
	public TileEntityFunctionalMachineT0(String name, double maxEnergy, double baseEnergyUsage, int baseTicksRequired) {
		
		super(maxEnergy, baseEnergyUsage, baseTicksRequired);
		
		this.name = name;
	}
	
	@Override
	public String getName() {
		
		return I18n.format(getBlockType().getUnlocalizedName() + "." + name + ".name");
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getName());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setBoolean("masterControl", masterControl);
		
		return nbtTags;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		masterControl = nbtTags.getBoolean("masterControl");
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		data.add(masterControl);
		
		return data;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			masterControl = dataStream.readBoolean();
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			int type = dataStream.readInt();
			switch(type) {
				case 1:
					masterControlOff();
					break;
				case 2:
					masterControlOn();
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(world.isRemote)
			return;
		
		if(!masterControl)
			return;
		
		if(isHighActivity)
			process();
		else if(isKeyTime() && canProcess())
			turnOn();
				
	}
	
	protected boolean canProcess() {
		
		return false;
	}
	
	protected void process() {
		
		if(electricityStored < energyPerTick)
			return;
		
		electricityStored -= energyPerTick;
        operatingTicks++;
        
		if(operatingTicks < ticksRequired)
			return;
		
		doFinish();
	}
	
	protected void doFinish() {
		
		operatingTicks = 0;
	}
	
	protected void turnOn() {
		
		isActive = true;
		isHighActivity = true;
	}
	
	protected void turnOff() {
		
		operatingTicks = 0;
		ticksRequired = 0;
		isActive = false;
		isHighActivity = false;
	}
	
	public void masterControlOn() {
		
		masterControl = true;
	}
	
	public void masterControlOff() {
		
		masterControl = false;
		turnOff();
	}
	
	protected boolean isKeyTime() {
		
		return world.getWorldTime() % 4 == 0;
	}
}
