package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.common.block.BlockBoundingBox;
import infinitesimalzeros.common.block.BlockTileEntityCore;
import infinitesimalzeros.common.block.BlockTileEntityCore.MachineSets;
import infinitesimalzeros.common.block.NanaBlock;
import infinitesimalzeros.common.block.NanaFurnace;
import infinitesimalzeros.common.item.ItemBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block NanaBlock = new NanaBlock("nana_block");
	public static final Block NanaFurnace = new NanaFurnace("nana_furnace");
	public static final Block TEBlock1 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_A);
	public static final Block TEBlock2 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_B);
	public static final Block BBBlock = new BlockBoundingBox();
	
	public static Block init(Block block, String name) {
		
		return block.setUnlocalizedName(name).setRegistryName("infinitesimalzeros:" + name);
	}
	
	public static void initRenderRegistry() {
		
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		
		registry.register(init(TEBlock1, "Machine1"));
		registry.register(init(TEBlock2, "Machine2"));
		registry.register(init(BBBlock, "BoundingBox"));
		RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock1).setRegistryName(TEBlock1.getRegistryName()));
		RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock2).setRegistryName(TEBlock2.getRegistryName()));
		RegistryItems.ITEMS.add(new ItemBlock(BBBlock).setRegistryName(BBBlock.getRegistryName()));
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry) {}
}
