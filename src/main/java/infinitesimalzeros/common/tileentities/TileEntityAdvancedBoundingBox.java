package infinitesimalzeros.common.tileentities;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IAdvancedBoundingBlock;
import infinitesimalzeros.common.capabilities.Capabilities;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.items.CapabilityItemHandler;

@InterfaceList({ @Interface(iface = "cofh.redstoneflux.api.IEnergyProvider", modid = "redstoneflux"), @Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux") })

public class TileEntityAdvancedBoundingBox extends TileEntityBoundingBox implements IEnergyReceiver, IEnergyProvider {
	
	public IAdvancedBoundingBlock getAdvTile() {
		
		IAdvancedBoundingBlock tile = (IAdvancedBoundingBlock) new Coord4D(corePos, world).getTileEntity(world);
		
		if(tile == null) {
			return null;
		}
		
		if(!(tile instanceof IAdvancedBoundingBlock)) {
			return null;
		}
		
		return (IAdvancedBoundingBlock) tile;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getAdvTile().getName());
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getEnergyStored(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int getMaxEnergyStored(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.getMaxEnergyStored(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public boolean canConnectEnergy(EnumFacing from) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return false;
		}
		
		return false;//advTile.canConnectEnergy(from);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null) {
			return 0;
		}
		
		return advTile.extractEnergy(from, maxExtract, simulate);
	}
	
	@Override
	@Method(modid = "redstoneflux")
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(advTile == null || !canConnectEnergy(from)) {
			return 0;
		}
		
		return advTile.receiveEnergy(from, maxReceive, simulate);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY) {
			return super.hasCapability(capability, facing);
		}
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing==EnumFacing.EAST)
			return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
		
		if(advTile == null) {
			return super.hasCapability(capability, facing);
		}
		
		return advTile.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if(capability == Capabilities.TILE_NETWORK_CAPABILITY) {
			return super.getCapability(capability, facing);
		}
		
		IAdvancedBoundingBlock advTile = getAdvTile();
		
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing==EnumFacing.EAST)
			return (T) advTile.getInsertionHandler();
		
		if(advTile == null) {
			return super.getCapability(capability, facing);
		}
		
		return advTile.getCapability(capability, facing);
	}

}
