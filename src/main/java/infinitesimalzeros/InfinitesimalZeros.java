package infinitesimalzeros;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import codechicken.lib.CodeChickenLib;
import cofh.CoFHCore;
import cofh.redstoneflux.RedstoneFluxProps;
import infinitesimalzeros.common.CommonProxy;
import infinitesimalzeros.common.config.ConfigGuiFactory;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = InfinitesimalZeros.MODID, name = InfinitesimalZeros.NAME, version = InfinitesimalZeros.VERSION, dependencies = RedstoneFluxProps.VERSION_GROUP + CoFHCore.VERSION_GROUP + CodeChickenLib.MOD_VERSION_DEP + "required-after:forge@[14.23.4.2814,)", guiFactory = "infinitesimalzeros.common.config.ConfigGuiFactory", acceptedMinecraftVersions = "[1.12.2]")

public class InfinitesimalZeros {
	
	public static final String MODID = "infinitesimalzeros";
	public static final String NAME = "Infinitesimal Zeros";
	public static final String NAME_GAPLESS = "InfinitesimalZeros";
	public static final String VERSION = "1.12.2-alpha-build23";
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
	@Instance(MODID)
	public static InfinitesimalZeros instance;
	
	public static Logger logger = (Logger) LogManager.getLogger(NAME_GAPLESS);
	
	@SidedProxy(clientSide = "infinitesimalzeros.client.ClientProxy", serverSide = "infinitesimalzeros.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.Init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit();
	}
	
	@EventHandler
	public void mapId(FMLModIdMappingEvent event) {
		
		proxy.mapId();
	}
	
}
