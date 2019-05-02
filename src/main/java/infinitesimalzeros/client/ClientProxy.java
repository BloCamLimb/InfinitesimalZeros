package infinitesimalzeros.client;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.render.item.ItemLayerWrapper;
import infinitesimalzeros.client.render.item.RenderMachineItem;
import infinitesimalzeros.client.render.machine.DryingBedTESR;
import infinitesimalzeros.client.render.machine.NanaSmelterTESR;
import infinitesimalzeros.common.CommonProxy;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineBlockStateMapper;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import infinitesimalzeros.common.registry.RegistryBlocks;
import infinitesimalzeros.common.tileentities.TileEntityDryingPool;
import infinitesimalzeros.common.tileentities.TileEntitySmelter;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
	
	private static final IStateMapper machineMapper = new MachineBlockStateMapper();
	
	@Override
	public void preInit() {
		
		super.preInit();
		OBJLoader.INSTANCE.addDomain(InfinitesimalZeros.MODID);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new NanaSmelterTESR());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDryingPool.class, new DryingBedTESR());
		ModelLoader.setCustomStateMapper(RegistryBlocks.TEBlock1, machineMapper);
		// ModelLoader.setCustomStateMapper(RegistryBlocks.TEBlock2, machineMapper);
		Item.getItemFromBlock(RegistryBlocks.TEBlock1).setTileEntityItemStackRenderer(new RenderMachineItem());
		
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		
	}
	
	@SubscribeEvent
	public void bakeModel(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
		
		machineModelBake(modelRegistry, "smelter", MachineTypes.Smelter);
	}
	
	private void machineModelBake(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry, String type, MachineTypes machineType) {
	    ModelResourceLocation modelResourceLocation = new ModelResourceLocation("infinitesimalzeros:" + type, "inventory");
	    ItemLayerWrapper itemLayerWrapper = new ItemLayerWrapper(modelRegistry.getObject(modelResourceLocation));
	    RenderMachineItem.modelMap.put(machineType, itemLayerWrapper);
	    modelRegistry.putObject(modelResourceLocation, itemLayerWrapper);
	}
	
}
