package infinitesimalzeros.common.capabilities;

import infinitesimalzeros.api.interfaces.IBank;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityBankProvider implements ICapabilitySerializable {
	
	private IBank instance = Capabilities.BANK_SYSTEM.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == Capabilities.BANK_SYSTEM;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		return capability == Capabilities.BANK_SYSTEM ? (T) instance : null;
	}

	@Override
	public NBTBase serializeNBT() {
		
		return Capabilities.BANK_SYSTEM.writeNBT(instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		
		Capabilities.BANK_SYSTEM.readNBT(instance, null, nbt);
	}
	
}
