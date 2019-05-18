package infinitesimalzeros.api.interfaces;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;

public interface IEnergyWrapper extends IMicroEnergyReceiver, IMicroEnergyTransmitter, IMicroEnergyStorage {
	
	/**
	 * If given side can input energy.
	 * @param side
	 * @return
	 */
	boolean sideIsInput(EnumFacing side);
	
	/**
	 * If given side can output energy.
	 * @param side
	 * @return
	 */
	boolean sideIsOutput(EnumFacing side);
	
	/**
	 * Get max output.
	 * @return
	 */
	double getMaxOutput();
	
}
