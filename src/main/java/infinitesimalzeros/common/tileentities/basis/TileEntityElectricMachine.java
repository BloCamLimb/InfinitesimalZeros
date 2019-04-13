package infinitesimalzeros.common.tileentities.basis;

import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.IZUtils.ResourceType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public abstract class TileEntityElectricMachine extends TileEntityBasicMachine {
	
	public TileEntityElectricMachine(double maxEnergy, double perTick, int ticksRequired) {
		
		super(maxEnergy, perTick, 3, ticksRequired, IZUtils.getResource(ResourceType.GUI, "GuiBasicMachine.png"));
		
	}
	
}
