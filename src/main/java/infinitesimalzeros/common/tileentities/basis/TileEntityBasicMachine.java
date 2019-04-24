package infinitesimalzeros.common.tileentities.basis;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import net.minecraft.util.ResourceLocation;

public abstract class TileEntityBasicMachine extends TileEntityOperationalMachine {
	
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
	public TileEntityBasicMachine(double maxEnergy, double baseEnergyUsage, int baseTicksRequired) {
		
		super(maxEnergy, baseEnergyUsage, baseTicksRequired);
		
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		
	}
	
	@Override
    public void setActive(boolean active) {
		
        boolean stateChange = (isActive != active);

        if (stateChange) {
            isActive = active;
            PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
        }
    }
	
}
