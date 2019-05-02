package infinitesimalzeros.common.tileentities;

import javax.annotation.Nullable;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT2;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityDryingPool extends TileEntityFunctionalMachineT2 {
	
	public TileEntityDryingPool() {
		
		super("AdvS", 500000, 60, 200);
		
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public boolean isStackValid(int slot, ItemStack stack) {
		
		return true;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		data.add(inputTank.getFluid().writeToNBT(new NBTTagCompound()));
		
		return super.getNetworkedData(data);
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		inputTank.setFluid(FluidStack.loadFluidStackFromNBT(PacketHandler.readNBT(dataStream)));
	}

	@Override
	public void onPlace(EnumFacing side) {
		
		for(int x = -2; x <= 2; x++) {
			
			for(int y = 0; y <= 0; y++) {
				
				for(int z = -2; z <= 2; z++) {
					
					if(x == 0 && y == 0 && z == 0)
						continue;

					int xx = side == EnumFacing.SOUTH ? x : side == EnumFacing.EAST ? -z : side == EnumFacing.NORTH ? -x : z;
					int zz = side == EnumFacing.SOUTH ? z : side == EnumFacing.EAST ? -x : side == EnumFacing.NORTH ? -z : x;
					
					BlockPos pos = getPos().add(xx, y, zz);
					
					if(x == -2 && y == 0 && z == 0)
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 1);
					else if (x == 2 && y == 0 && z == 0)
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 2);
					else
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 0);
					
					world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
				}
			}
		}
		
	}

	@Override
	public void onBreak() {
		
		for(int x = -2; x <= 2; x++) {
			
			for(int y = 0; y <= 0; y++) {
				
				for(int z = -2; z <= 2; z++) {
					
					world.setBlockToAir(getPos().add(x,y,z));
				}
			}
		}
		
	}

	@Override
	public IFluidTank[] getAccessibleFluidTanks(EnumFacing side) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrainTankFrom(int iTank, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing from) {

		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {

				@Override
				public IFluidTankProperties[] getTankProperties() {

					FluidTankInfo inputInfo = inputTank.getInfo();
					return new IFluidTankProperties[] { new FluidTankProperties(inputInfo.fluid, inputInfo.capacity)};
				}

				@Override
				public int fill(FluidStack resource, boolean doFill) {


					return inputTank.fill(resource, doFill);
				}

				@Nullable
				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {

					return inputTank.drain(resource, doDrain);
				}

				@Nullable
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {

					return inputTank.drain(maxDrain, doDrain);
				}
			});
		}
		return super.getCapability(capability, from);
	}

	
}
