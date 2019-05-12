package infinitesimalzeros.common.tileentities;

import cofh.core.network.ITileInfoPacketHandler;
import cofh.core.network.PacketBase;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.core.FluidHandlerZero;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT2;
import infinitesimalzeros.common.util.FluidUtils;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntitySaltTank extends TileEntityFunctionalMachineT2 {
	
	private FluidStack prevFluid = new FluidStack(FluidRegistry.WATER, 0);
	
	public TileEntitySaltTank() {
		
		super("SaltTank", 0);
		doAutoSync = false;
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
		
		if(!world.isRemote) {
			prevFluid = updateRenderFluid(inputTank, prevFluid);
		}

	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		
		return Math.pow(128, 2);
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
		
		return resource.getFluid() == FluidRegistry.WATER;
	}

	@Override
	public boolean canDrainTankFrom(int iTank, EnumFacing side) {
		
		return true;
	}
	
	@Override
	protected boolean canProcess() {
		
		if(!world.canSeeSky(pos.up()))
			return false;
		
		return world.isDaytime() && !world.isRaining() && inventory.get(0).getCount() < 64;
	}
	
	@Override
	protected void process() {
		
		if(!isKeyTime(20))
			return;
		
		if(world.isRaining())
			inputTank.fill(new FluidStack(FluidRegistry.WATER, 10), true);
		
		if(!canProcess())
			return;
		
		if(inputTank.getFluidAmount() < energyPerTick)
			return;
		
		inputTank.drain((int) energyPerTick, true);
		
		operatingTicks++;
		
		if(operatingTicks < ticksRequired)
			return;
		
		doFinish();
	}
	
	@Override
	protected void doFinish() {
		
		super.doFinish();
		
		if (inventory.get(0).isEmpty()) {
			inventory.set(0, new ItemStack(Items.DIAMOND));
		} else {
			inventory.get(0).grow(1);
		}
		
		if(!canProcess())
			turnOff();
	}
	
	@Override
	protected void turnOn() {
		
		super.turnOn();
		
		// Actually in seconds 24 15 10 6
		ticksRequired = 24;
		
		// Actually mb/s
		energyPerTick = 10;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return super.hasCapability(capability, facing) || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing facing) {

		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == null) {
			
			return (T) new FluidHandlerZero(this, facing);
		}
		return super.getCapability(capability, facing);
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
