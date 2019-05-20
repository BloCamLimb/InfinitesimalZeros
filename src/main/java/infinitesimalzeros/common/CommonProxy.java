package infinitesimalzeros.common;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.render.ColorItemRenderer;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.config.Config;
import infinitesimalzeros.common.core.handler.CapabilityHandler;
import infinitesimalzeros.common.core.handler.KeybindHandler;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.integration.TOPInfoProvider;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.registry.RegistryFluid;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.registry.RegistryRecipes;
import mekanism.api.MekanismAPI;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(this);
		
		MekanismAPI.addBoxBlacklistMod(InfinitesimalZeros.MODID);
		
		Capabilities.register();
		
		Config.init(event.getModConfigurationDirectory());

	}
	
	public void Init() {
		
		registerColors();
		
		PacketHandler.Init();
		
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", TOPInfoProvider.class.getName());
		
	}
	
	public void postInit() {
		
		RegistryRecipes.initMainRecipes();
		
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		
	}
	
	public void registerOreDictEntries() {
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerColors() {
		
		ColorItemRenderer render = new ColorItemRenderer();
		render.renderItem();
	}
	
	public static CreativeTabs creativeTab = new CreativeTabs(InfinitesimalZeros.MODID) {

		@Override
		public ItemStack getTabIconItem() {
			
			return new ItemStack(RegistryItems.wrench);
		}
		
	};

	public void mapId() {
		
		RecipeCoreT1.refresh();
		
	}
	
}
