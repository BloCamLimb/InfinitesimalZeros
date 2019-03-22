package infinitesimalzeros.client.handler;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.render.IModelRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = InfinitesimalZeros.MODID)
public class ModelHandler {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for(Item item : Item.REGISTRY) {
			if(item instanceof IModelRegistry)
				((IModelRegistry) item).registerModels();
		}
	}

}
