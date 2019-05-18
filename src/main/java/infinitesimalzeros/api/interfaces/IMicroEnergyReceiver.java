package infinitesimalzeros.api.interfaces;

import net.minecraft.util.EnumFacing;

public interface IMicroEnergyReceiver {
	
	/**
	 * Simulate a TileEntity receive energy.
	 * @param side to receive
	 * @param amount of energy
	 * @param is simulate
	 * @return
	 */
	double receiveMicroEnergy(EnumFacing side, double amount, boolean simulate);
	
	/**
	 * Can receive energy for given side.
	 * @param side to receive
	 * @return
	 */
	boolean canReceiveMicroEnergy(EnumFacing side);
	
}
