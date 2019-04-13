package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.List;

import infinitesimalzeros.common.blocks.BlockBoundingBox;
import infinitesimalzeros.common.blocks.BlockTileEntityCore;
import infinitesimalzeros.common.blocks.NanaBlock;
import infinitesimalzeros.common.blocks.NanaFurnace;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineSets;
import infinitesimalzeros.common.items.ItemBlockMachine;
import infinitesimalzeros.common.items.ItemTestMachine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block NanaBlock = new NanaBlock("nana_block");
	public static final Block NanaFurnace = new NanaFurnace();
	public static final Block TEBlock1 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_A);
	public static final Block TEBlock2 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_B);
	public static final Block BBBlock = (BlockBoundingBox)new BlockBoundingBox();
	
	public static Block init(Block block, String name) {
		
		return block.setUnlocalizedName(name).setRegistryName("infinitesimalzeros:" + name);
	}
	
	public static void initRenderRegistry() {
		
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		
		registry.register(init(TEBlock1, "Machine1"));
		registry.register(init(TEBlock2, "Machine2"));
		registry.register(init(BBBlock, "BoundingBox"));
		registry.register(init(NanaFurnace, "nana_furnace"));
		RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock1).setRegistryName(TEBlock1.getRegistryName()));
		RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock2).setRegistryName(TEBlock2.getRegistryName()));
		RegistryItems.ITEMS.add(new ItemBlock(BBBlock).setRegistryName(BBBlock.getRegistryName()));
		RegistryItems.ITEMS.add(new ItemTestMachine(NanaFurnace).setRegistryName(NanaFurnace.getRegistryName()));
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry) {}
}
