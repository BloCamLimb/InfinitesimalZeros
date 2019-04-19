package infinitesimalzeros.common;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.render.ColorItemRenderer;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.core.handler.GUIHandler;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.items.MetaItems;
import infinitesimalzeros.common.network.PacketSimpleGui;
import infinitesimalzeros.common.unification.MaterialBasis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy {
	
	public void preInit() {
		
		MinecraftForge.EVENT_BUS.register(this);
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
		
	}
	
	public void registerOreDictEntries() {
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerColors() {
		
		MetaItems.registerColors();
		ColorItemRenderer render = new ColorItemRenderer();
		render.renderItem();
	}
	
}
