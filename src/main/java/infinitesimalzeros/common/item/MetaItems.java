package infinitesimalzeros.common.item;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public final class MetaItems {
	
	private MetaItems() {}
	
	public static List<MetaItem<?>> ITEMS = MetaItem.getMetaItems();

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        for (MetaItem<?> item : ITEMS) {
            item.registerColor();
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (MetaItem<?> item : ITEMS) {
            item.registerModels();
        }
    }
    
    public static void init() {
        MetaItem1 first = new MetaItem1();
        first.setRegistryName("materials");
    }
	
    public static void registerOreDict() {
        for (MetaItem<?> item : ITEMS) {
            if (item instanceof MaterialMetaItem) {
                ((MaterialMetaItem) item).registerOreDict();
            }
        }
    }
}
