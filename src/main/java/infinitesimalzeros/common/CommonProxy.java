package infinitesimalzeros.common;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.registry.ItemRender;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.registry.RegistryRecipes;
import infinitesimalzeros.common.registry.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {
	
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		new ItemRender();
	}
	
	public void Init() {
		
	}
	
	public void postInit() {
		
	}
	
	public void registerOreDictEntries() {
		
	}
    
	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        RegistryItems.registerItems(event.getRegistry());
        InfinitesimalZeros.proxy.registerOreDictEntries();
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        RegistryBlocks.init();
        RegistryBlocks.initRenderRegistry();
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        RegistryRecipes.initMainRecipes();
    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        RegistrySounds.init();
    }

}
