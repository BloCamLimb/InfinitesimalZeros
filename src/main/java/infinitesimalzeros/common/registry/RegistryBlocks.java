package infinitesimalzeros.common.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.blocks.BlockBoundingBox;
import infinitesimalzeros.common.blocks.BlockTileEntityCore;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineSets;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import infinitesimalzeros.common.blocks.NanaBlock;
import infinitesimalzeros.common.blocks.NanaFurnace;
import infinitesimalzeros.common.items.ItemBlockMachine;
import infinitesimalzeros.common.items.ItemTestMachine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static Map<String, ModelResourceLocation> machineResources = new HashMap<>();
	
	//public static final Block NanaBlock = new NanaBlock("nana_block");
	//public static final Block NanaFurnace = new NanaFurnace();
	public static final Block TEBlock1 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_A);
	//public static final Block TEBlock2 = BlockTileEntityCore.getBlockMachine(MachineSets.Machine_Set_B);
	public static final Block BBBlock = new BlockBoundingBox();
	
	public static Block init(Block block, String name) {
		
		return block.setUnlocalizedName(InfinitesimalZeros.NAME_GAPLESS + "." + name).setRegistryName("infinitesimalzeros:" + name);
	}
	
	public static void initRenderRegistry() {
		
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		
		registry.register(init(TEBlock1, "Machine1"));
		//registry.register(init(TEBlock2, "Machine2"));
		registry.register(init(BBBlock, "BoundingBox"));
		//registry.register(init(NanaFurnace, "nana_furnace"));
		
		RegistryItems.ITEMS.add(new ItemBlockMachine(TEBlock1).setRegistryName(TEBlock1.getRegistryName()));
		
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry) {}
	
	public static void registerBlockRender() {
		
		for(MachineTypes type : MachineTypes.values()) {
			
			List<ModelResourceLocation> modelsToAdd = new ArrayList<>();
			String resource = "infinitesimalzeros:" + type.getName();
			
			while(true) {
				
				if(machineResources.get(resource) == null) {
					
					List<String> entries = new ArrayList<>();
					
					if(type.hasActiveTexture()) {
						entries.add("active=false");
					}
					
					if(type.hasRotations()) {
						entries.add("facing=north");
					}
					
					String properties = getProperties(entries);
					
					ModelResourceLocation model = new ModelResourceLocation(resource, properties);
					
					machineResources.put(resource, model);
					modelsToAdd.add(model);
					
				}
				
				break;
			}
			
			ModelLoader.registerItemVariants(Item.getItemFromBlock(type.block.getBlock()), modelsToAdd.toArray(new ModelResourceLocation[] {}));
		}
		
		ItemMeshDefinition machineMesher = stack -> {
			MachineTypes type = MachineTypes.get(stack);
			
			if(type != null) {
				String resource = "infinitesimalzeros:" + type.getName();
				
				return machineResources.get(resource);
			}
			
			return null;
		};
		InfinitesimalZeros.logger.info("This is"+machineResources);
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(RegistryBlocks.TEBlock1), machineMesher);
	}

	private static String getProperties(List<String> entries) {
		
		StringBuilder properties = new StringBuilder();
		
		for(int i = 0; i < entries.size(); i++) {
			properties.append(entries.get(i));
			if(i < entries.size() - 1) {
				properties.append(",");
			}
		}
		return properties.toString();
	}
}
