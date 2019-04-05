package infinitesimalzeros.api.energy;

public interface IMicroEnergyStorage {
	
	/**
	 * Gets the amount of energy this TileEntity is currently storing.
	 * @return stored energy
	 */
    double getEnergy();

	/**
	 * Sets the amount of stored energy of this TileEntity to a new amount.
	 * @param energy - new energy value
	 */
    void setEnergy(double energy);

	/**
	 * Gets the maximum amount of energy this TileEntity can store.
	 * @return maximum energy
	 */
    double getMaxEnergy();

}
