package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IModelRender;
import infinitesimalzeros.common.items.MetaItem;
import infinitesimalzeros.common.items.MetaItems;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.registry.RegistryRecipes;
import infinitesimalzeros.common.registry.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class RegistryEventHandler {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		InfinitesimalZeros.logger.info("Items Initilizing...");
		RegistryItems.registerItems(event.getRegistry());
		RegistryBlocks.registerItemBlocks(event.getRegistry());
		InfinitesimalZeros.proxy.registerOreDictEntries();
		event.getRegistry().registerAll(RegistryItems.ITEMS.toArray(new Item[0]));
		IForgeRegistry<Item> registry = event.getRegistry();
		
		for(MetaItem<?> item : MetaItems.ITEMS) {
			registry.register(item);
			item.registerSubItems();
		}
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		
		InfinitesimalZeros.logger.info("Blocks Initilizing...");
		RegistryBlocks.registerBlocks(event.getRegistry());
		;
		RegistryBlocks.initRenderRegistry();
		TileEntityHandler.registerTileEntity();
		NetworkRegistry.INSTANCE.registerGuiHandler(InfinitesimalZeros.instance, new GUIHandler());
		event.getRegistry().registerAll(RegistryBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
		MetaItems.registerModels();
		
		for(Item item : RegistryItems.ITEMS) {
			if(item instanceof IModelRender) {
				((IModelRender) item).registerModels();
			}
		}
		
		for(Block block : RegistryBlocks.BLOCKS) {
			if(block instanceof IModelRender) {
				((IModelRender) block).registerModels();
			}
		}
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		
		RegistryRecipes.initMainRecipes();
		MetaItems.registerOreDict();
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		
		RegistrySounds.init();
	}
	
}
