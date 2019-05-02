package infinitesimalzeros.common.util;

import cofh.core.fluid.FluidTankCore;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import io.netty.buffer.ByteBuf;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.GasTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidUtils {
	
	private static final NBTTagCompound EMPTY_TAG_COMPOUND = new NBTTagCompound();

    public static void addTankData(TileNetworkList data, GasTank tank) {
        if (tank.getGas() != null) {
            data.add(tank.getGas().write(new NBTTagCompound()));
        } else {
            data.add(EMPTY_TAG_COMPOUND);
        }
    }

    public static void addTankData(TileNetworkList data, FluidTankCore tank) {
        if (tank.getFluid() != null) {
            data.add(tank.getFluid().writeToNBT(new NBTTagCompound()));
        } else {
            data.add(EMPTY_TAG_COMPOUND);
        }
    }

    public static void addFluidStack(TileNetworkList data, FluidStack stack) {
        if (stack != null) {
            data.add(stack.writeToNBT(new NBTTagCompound()));
        } else {
            data.add(EMPTY_TAG_COMPOUND);
        }
    }

    public static void readTankData(ByteBuf dataStream, GasTank tank) {
        tank.setGas(GasStack.readFromNBT(PacketHandler.readNBT(dataStream)));
    }

    public static void readTankData(ByteBuf dataStream, FluidTankCore tank) {
        tank.setFluid(readFluidStack(dataStream));
    }
    
    public static FluidStack readFluidStack(ByteBuf dataStream) {
        return FluidStack.loadFluidStackFromNBT(PacketHandler.readNBT(dataStream));
    }
	
}
