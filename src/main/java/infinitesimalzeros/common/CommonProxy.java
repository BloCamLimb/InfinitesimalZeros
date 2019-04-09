package infinitesimalzeros.common;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.capability.Capabilities;
import infinitesimalzeros.common.item.MetaItems;
import infinitesimalzeros.common.render.ColorItemRenderer;
import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.util.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		MaterialBasis.runMaterialHandlers();
		MaterialBasis.freezeRegistry();
		Capabilities.register();
		/*InfinitesimalZeros.logger.info("Init Items");
		MetaItems.init();*/
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
	
	
	public void registerItemRenderer(Item item, int meta, String id){}
	
	public void registerColors() {
		MetaItems.registerColors();
		ColorItemRenderer render = new ColorItemRenderer();
		render.renderItem();
	}

}
