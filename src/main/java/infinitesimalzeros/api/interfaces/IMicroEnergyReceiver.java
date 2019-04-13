package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface IMicroEnergyReceiver {
	
	/**
	 * Transfer a certain amount of energy to this acceptor.
	 * 
	 * @param amount   - amount to transfer
	 * @param simulate - if the operation should be simulated
	 * @return energy used
	 */
	double receiveEnergy(EnumFacing side, double amount, boolean simulate);
	
	/**
	 * Whether or not this tile entity can accept energy from a certain side.
	 * 
	 * @param side - side to check
	 * @return if tile entity accepts energy
	 */
	boolean canReceiveEnergy(EnumFacing side);
	
}
