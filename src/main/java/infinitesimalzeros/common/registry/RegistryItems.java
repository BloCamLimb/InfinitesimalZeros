package infinitesimalzeros.common.registry;

import infinitesimalzeros.common.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import static infinitesimalzeros.common.lib.ItemNames.*;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.CommonProxy;

public class RegistryItems {
	
	public static final Item neutron = new Neutron();

	public static void registerItems(IForgeRegistry<Item> registry)
	{
		registry.register(init(neutron, "neutron"));

	}
	
	public static Item init(Item item, String name)
	{
		return item.setUnlocalizedName("infinitesimalzeros." + name).setRegistryName(name);
	}
    
}
