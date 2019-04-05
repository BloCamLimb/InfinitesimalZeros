package infinitesimalzeros.common.tileentity.basis;

import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.IZUtils.ResourceType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public abstract class TileEntityElectricMachine extends TileEntityBasicMachine {
	
	public TileEntityElectricMachine(String name, double maxEnergy, double perTick, int ticksRequired) {
		
		super(name, maxEnergy, perTick, 3, ticksRequired, IZUtils.getResource(ResourceType.GUI, "GuiBasicMachine.png"));
		
		inventory = NonNullList.withSize(4, ItemStack.EMPTY);
		
	}

}
