package infinitesimalzeros.common.tileentities;

import javax.annotation.Nullable;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IMultiblockCore;
import infinitesimalzeros.api.interfaces.IMultiblockMachineT1;
import infinitesimalzeros.api.interfaces.IMultiblockMachineT2;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.items.CapabilityItemHandler;

@InterfaceList({ @Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"), @Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux") })

public class TileEntityAdvancedBoundingBox extends TileEntityBoundingBox implements IEnergyReceiver, IEnergyProvider {
	
	public int func;
	
	public IMultiblockMachineT1 getT1() {
		
		IMultiblockCore tile = (IMultiblockCore) new Coord4D(corePos, world).getTileEntity(world);
		
		if(tile == null) {
			return null;
		}
		
		if(!(tile instanceof IMultiblockMachineT1)) {
			return null;
		}
		
		return (IMultiblockMachineT1) tile;
	}
	
	public IMultiblockMachineT2 getT2() {
		
		IMultiblockMachineT2 tile = (IMultiblockMachineT2) new Coord4D(corePos, world).getTileEntity(world);
		
		if(tile == null) {
			return null;
		}
		
		if(!(tile instanceof IMultiblockMachineT2)) {
			return null;
		}
		
		return (IMultiblockMachineT2) tile;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		func = compound.getInteger("function");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		compound.setInteger("function", func);
		
		return compound;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			func = dataStream.readInt();
		
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		data.add(func);
		
		return data;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getT1().getName());
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getEnergyStored(EnumFacing from) {
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getMaxEnergyStored(EnumFacing from) {
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getMaxEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(advTile == null) {
			return false;
		}
		
		return advTile.canConnectEnergy(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.extractEnergy(from, maxExtract, simulate);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(advTile == null || !canConnectEnergy(from)) {
			return 0;
		}
		
		return advTile.receiveEnergy(from, maxReceive, simulate);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY)
			return super.hasCapability(capability, facing);
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && func != 0)
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		
		if(advTile == null)
			return super.hasCapability(capability, facing);
		
		return advTile.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY)
			return super.getCapability(capability, facing);
		
		IMultiblockMachineT1 advTile = getT1();
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && func == 1)
			return (T) advTile.getInsertionHandler();
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && func == 2)
			return (T) advTile.getExtractionHandler();
		
		if(advTile == null)
			return super.getCapability(capability, facing);
		
		return advTile.getCapability(capability, facing);
	}
	
}
