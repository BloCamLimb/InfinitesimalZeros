package infinitesimalzeros.api.interfaces;

public interface IMicroEnergyStorage {

	/**
	 * Get amount of energy currently stored in TileEntity.
	 * @return
	 */
	double getEnergy();

	/**
	 * Set energy to given value.
	 * @param energy
	 */
	void setEnergy(double energy);

	/**
	 * Get max energy can be stored in TileEntity.
	 * @return
	 */
	double getMaxEnergy();
	
}
