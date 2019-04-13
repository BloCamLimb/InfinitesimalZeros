package infinitesimalzeros.common.registry;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IModelRender;
import net.minecraft.item.Item;

public class ItemRegister extends Item implements IModelRender {
	
	public ItemRegister(String name) {
		
		setUnlocalizedName("infinitesimalzeros." + name);
		setRegistryName(name);
		RegistryItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		
		InfinitesimalZeros.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
