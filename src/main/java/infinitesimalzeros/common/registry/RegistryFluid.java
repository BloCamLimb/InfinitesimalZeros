package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.fluid.FluidCore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryFluid {
	
	public static final List<Fluid> FLUIDS = new ArrayList<Fluid>();
	
	public static final Fluid salt_water2 = new FluidCore("saltywater", 0x6079BB);
	public static final Fluid salt_water1 = new FluidCore("saltywater2", 0xFF0000);
	
}
