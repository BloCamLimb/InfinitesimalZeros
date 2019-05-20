package infinitesimalzeros.api.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A virtual money storage system used for merchandise trading.
 * 
 * @author BloCamLimb
 *
 */
public interface IBank {
	
	/**
	 * Deposit money into the system.
	 * 
	 * @param money Amount to deposit
	 */
	void deposit(float money);
	
	/**
	 * Withdraw money from the system.
	 * 
	 * @param money Amount to withdraw
	 */
	void withdraw(float money);
	
	/**
	 * Set amount of the money to specific value.
	 * 
	 * @param money Amount to set
	 */
	void setMoney(float money);
	
	/**
	 * Get amount of the money that currently stored.
	 * 
	 * @return Amount of the money
	 */
	float getMoney();
	
	NBTTagCompound writeToNBT(NBTTagCompound nbt);
	
	void readFromNBT(NBTTagCompound nbt);
	
}
