package infinitesimalzeros.common.tileentities.basis;

import infinitesimalzeros.api.interfaces.IBoundingBlock;
import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityFunctionalMachineT0 extends TileEntityBasicMachine implements IBoundingBlock {
	
	// Display name in GUI.
	public String name;
	
	// Control sleep state.
	public boolean isHighActivity;
	
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
	public void onUpdate() {
		
		super.onUpdate();
		
		if(isHighActivity)
			if(canProcess())
				process();
			else
				turnOff();
		else if(isKeyTime() && canProcess())
			turnOn();
				
	}
	
	protected boolean canProcess() {
		
		return getEnergy() >= energyPerTick;
	}
	
	protected void process() {
		
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
		isActive = false;
		isHighActivity = false;
	}
	
	protected boolean isKeyTime() {
		
		return world.getWorldTime() % 4 == 0;
	}
}
