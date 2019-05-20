package infinitesimalzeros.common.capabilities;

import infinitesimalzeros.api.interfaces.IBank;
import infinitesimalzeros.common.capabilities.DefaultStorageHelper.BankStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class DefaultBankSystem implements IBank {
	
	private float money = 300;

	@Override
	public void deposit(float money) {
		
		this.money += money;
	}

	@Override
	public void withdraw(float money) {
		
		this.money -= money;
	}

	@Override
	public void setMoney(float money) {
		
		this.money = money;
	}

	@Override
	public float getMoney() {
		
		return money;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		nbt.setFloat("money", money);
		
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		setMoney(nbt.getFloat("money"));
	}
	
	public static void register() {
		
		CapabilityManager.INSTANCE.register(IBank.class, new BankStorage(), DefaultBankSystem::new);
	}
	
}
