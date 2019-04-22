package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface IMicroEnergyTransmitter {
	
	/**
	 * Pulls a certain amount of energy from this outputter.
	 * 
	 * @param amount   - amount to pull
	 * @param simulate - if the operation should be simulated
	 * @return energy sent
	 */
	double transMicroEnergy(EnumFacing side, double amount, boolean simulate);
	
	/**
	 * Whether or not this tile entity can output energy on a specific side.
	 * 
	 * @param side - side to check
	 * @return if the tile entity outputs energy
	 */
	boolean canOutputMicroEnergy(EnumFacing side);
	
}
