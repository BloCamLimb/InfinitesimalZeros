package infinitesimalzeros;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import infinitesimalzeros.common.CommonProxy;
import infinitesimalzeros.common.network.PacketSimpleGui;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = InfinitesimalZeros.MODID, name = InfinitesimalZeros.NAME, version = InfinitesimalZeros.VERSION, dependencies = "", acceptedMinecraftVersions = "[1.12.2]")

public class InfinitesimalZeros {
	
	public static final String MODID = "infinitesimalzeros";
	public static final String NAME = "Infinitesimal Zeros";
	public static final String VERSION = "1.12.2-b1";
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
	@Instance(MODID)
	public static InfinitesimalZeros instance;
	
	public static Logger logger = (Logger) LogManager.getLogger("InfinitesimalZeros");
	
	@SidedProxy(clientSide = "infinitesimalzeros.client.ClientProxy", serverSide = "infinitesimalzeros.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.Init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit();
	}
	
}
