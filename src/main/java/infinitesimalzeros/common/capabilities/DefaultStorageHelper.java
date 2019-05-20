package infinitesimalzeros.common.capabilities;

import infinitesimalzeros.api.interfaces.IBank;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.INBTSerializable;

public class DefaultStorageHelper {
	
	public static class DefaultStorage<T> implements IStorage<T> {
		
		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			
			if(instance instanceof INBTSerializable)
				return ((INBTSerializable<?>) instance).serializeNBT();
			return new NBTTagCompound();
		}
		
		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
			
			if(instance instanceof INBTSerializable) {
				
				Class<? extends NBTBase> nbtClass = ((INBTSerializable<?>) instance).serializeNBT().getClass();
				
				if(nbtClass.isInstance(nbt)) {
					((INBTSerializable) instance).deserializeNBT(nbtClass.cast(nbt));
				}
			}
		}
	}
	
	public static class BankStorage implements IStorage<IBank> {

		@Override
		public NBTBase writeNBT(Capability<IBank> capability, IBank instance, EnumFacing side) {
			
			return instance.writeToNBT(new NBTTagCompound());
		}

		@Override
		public void readNBT(Capability<IBank> capability, IBank instance, EnumFacing side, NBTBase nbt) {
			
			instance.readFromNBT((NBTTagCompound)nbt);
		}
		
	}
	
	public static class NullStorage<T> implements IStorage<T> {
		
		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			
			return new NBTTagCompound();
		}
		
		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {}
		
	}
	
}
