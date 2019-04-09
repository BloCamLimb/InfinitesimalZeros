package infinitesimalzeros.common.tileentity.basis;

import net.minecraft.util.ResourceLocation;

public class TileEntityBasicMachine extends TileEntityOperationalMachine {
	
	public ResourceLocation guiLocation;
	
	// public RECIPE cachedRecipe = null;
	
	/**
	 * The foundation of all machines - a simple tile entity with a facing, active state, initialized state, sound effect, and animated texture.
	 * 
	 * @param soundPath         - location of the sound effect
	 * @param name              - full name of this machine
	 * @param maxEnergy         - how much energy this machine can store
	 * @param baseTicksRequired - how many ticks it takes to run a cycle
	 */
	public TileEntityBasicMachine(String name, double maxEnergy, double baseEnergyUsage, int upgradeSlot, int baseTicksRequired, ResourceLocation location) {
		
		super(name, maxEnergy, baseEnergyUsage, upgradeSlot, baseTicksRequired);
		
		guiLocation = location;
	}
	
}
