package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface IMicroEnergyTransmitter {
	
	/**
	 * Simulate a TileEntity transmit energy.
	 * @param side to transmit
	 * @param amount of energy
	 * @param is simulate
	 * @return
	 */
	double transmitMicroEnergy(EnumFacing side, double amount, boolean simulate);
	
	/**
	 * Can transmit energy for given side.
	 * @param side to transmit
	 * @return
	 */
	boolean canTransmitMicroEnergy(EnumFacing side);
	
}
