package infinitesimalzeros.common.tileentities;

import javax.annotation.Nullable;

import cofh.core.util.helpers.FluidHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT2;
import infinitesimalzeros.common.util.FluidUtils;
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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityDryingPool extends TileEntityFunctionalMachineT2 {
	
	public TileEntityDryingPool() {
		
		super("DryingBed", 0);
		
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
		
		//PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		FluidUtils.addTankData(data, inputTank);
		
		return data;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			FluidUtils.readTankData(dataStream, inputTank);
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
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 9);
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
		
		return new IFluidTank[] {inputTank};
	}

	@Override
	public boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource) {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canDrainTankFrom(int iTank, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean canProcess() {
		
		if(!world.canSeeSky(pos.up()))
			return false;
		
		return world.isDaytime() && !world.isRaining();
	}
	
	@Override
	protected void process() {
		
		if(isKeyTime(20))
			operatingTicks++;
		
		if(operatingTicks < ticksRequired)
			return;
		
		doFinish();
	}
	
	@Override
	protected void doFinish() {
		
		super.doFinish();
		
		if(!canProcess())
			turnOff();
	}
	
	@Override
	protected void turnOn() {
		
		super.turnOn();
		
		ticksRequired = 25;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing from) {

		/*if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {

				@Override
				public IFluidTankProperties[] getTankProperties() {

					FluidTankInfo inputInfo = inputTank.getInfo();
					return null;//new IFluidTankProperties[] { new FluidTankProperties(inputInfo.fluid, inputInfo.capacity)};
				}

				@Override
				public int fill(FluidStack resource, boolean doFill) {


					return 0;//inputTank.fill(resource, doFill);
				}

				@Nullable
				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {

					return null;//inputTank.drain(resource, doDrain);
				}

				@Nullable
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {

					return null;//inputTank.drain(maxDrain, doDrain);
				}
			});
		}*/
		return super.getCapability(capability, from);
	}

	@Override
	public IItemHandler getInsertionHandler() {
		
		return null;
	}

	@Override
	public IItemHandler getExtractionHandler() {
		
		return extractionHandler;
	}

	
}
