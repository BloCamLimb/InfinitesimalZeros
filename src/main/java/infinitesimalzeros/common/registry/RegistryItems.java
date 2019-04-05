package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.common.item.Neutron;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryItems {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
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
