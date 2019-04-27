package infinitesimalzeros.common;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.render.ColorItemRenderer;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.items.MetaItems;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.registry.RegistryRecipes;
import infinitesimalzeros.common.unification.MaterialBasis;
import mekanism.api.MekanismAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void preInit() {
		
		MinecraftForge.EVENT_BUS.register(this);
		MekanismAPI.addBoxBlacklistMod(InfinitesimalZeros.MODID);
		MaterialBasis.runMaterialHandlers();
		MaterialBasis.freezeRegistry();
		Capabilities.register();
		/*
		 * InfinitesimalZeros.logger.info("Init Items"); MetaItems.init();
		 */
	}
	
	public void Init() {
		
		registerColors();
		
		InfinitesimalZeros.logger.info("Packet Initializing...");
		PacketHandler.Init();
		
	}
	
	public void postInit() {
		
		RegistryRecipes.initMainRecipes();
		
	}
	
	public void registerOreDictEntries() {
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerColors() {
		
		MetaItems.registerColors();
		ColorItemRenderer render = new ColorItemRenderer();
		render.renderItem();
	}
	
	public static CreativeTabs creativeTab = new CreativeTabs(InfinitesimalZeros.MODID) {

		@Override
		public ItemStack getTabIconItem() {
			
			return new ItemStack(RegistryItems.neutron);
		}
		
	};

	public void mapId() {
		
		RecipeCoreT1.refresh();
		
	}
	
}
