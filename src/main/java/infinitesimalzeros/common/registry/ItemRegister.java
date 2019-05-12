package infinitesimalzeros.common.registry;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IModelRender;
import net.minecraft.item.Item;

public class ItemRegister extends Item implements IModelRender {
	
	public ItemRegister(String name) {
		
		setUnlocalizedName(InfinitesimalZeros.NAME_GAPLESS + "." + name);
		setRegistryName(name);
		setCreativeTab(InfinitesimalZeros.proxy.creativeTab);
		RegistryItems.ITEMS.add(this);
		InfinitesimalZeros.logger.info("Now Register Item: "+this.getRegistryName());
	}
	
	@Override
	public void registerModels() {
		
		InfinitesimalZeros.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
