package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.common.items.ItemCard;
import infinitesimalzeros.common.items.ItemMaterial;
import infinitesimalzeros.common.items.ItemWrench;
import infinitesimalzeros.common.items.Neutron;
import infinitesimalzeros.common.unification.MaterialSetIcon;
import infinitesimalzeros.common.unification.material.IngotMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryItems {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//public static final Item neutron = new Neutron();
	
	public static final Item wrench = new ItemWrench("wrench");
	public static final Item card = new ItemCard("card");
	public static final Item material = new ItemMaterial();
	//public static final IngotMaterial Lithium = new IngotMaterial(2, "lithium", 0x80C8F0, MaterialSetIcon.DULL);
	
	public static void registerItems(IForgeRegistry<Item> registry) {
		
		//registry.register(init(neutron, "neutron"));
		
		//RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock2).setRegistryName(TEBlock2.getRegistryName()));
		//ITEMS.add(new ItemBlock(RegistryBlocks.BBBlock).setRegistryName(RegistryBlocks.BBBlock.getRegistryName()));
		//RegistryItems.ITEMS.add(new ItemTestMachine(NanaFurnace).setRegistryName(NanaFurnace.getRegistryName()));
	}
	
	public static Item init(Item item, String name) {
		
		return item.setUnlocalizedName("infinitesimalzeros." + name).setRegistryName(name);
	}
	
}
