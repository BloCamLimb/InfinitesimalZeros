package infinitesimalzeros.common.tileentity;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentity.basis.TileEntityElectricMachine;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySmelter extends TileEntityElectricMachine {

	public TileEntitySmelter() {
		super("Smelter", 50000, 60, 200);
	}
	
}
