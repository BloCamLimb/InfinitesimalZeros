package infinitesimalzeros.common.registry;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IModelRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class BlockRegister extends Block implements IModelRender {
	
	public BlockRegister(String name, Material material) {
		
		super(material);
		setUnlocalizedName(InfinitesimalZeros.NAME_GAPLESS + "." + name);
		setRegistryName(name);
		RegistryBlocks.BLOCKS.add(this);
		RegistryItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		
		InfinitesimalZeros.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
