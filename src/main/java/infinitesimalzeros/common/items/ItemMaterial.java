package infinitesimalzeros.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.registry.ItemRegister;
import infinitesimalzeros.common.registry.RegistryItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMaterial extends ItemRegister {
	
	private List<Short> generateItems = new ArrayList<Short>();
	public static Map<String, ModelResourceLocation> resources = new HashMap<>();

	public ItemMaterial() {
		
		super("material");
		setHasSubtypes(true);
		for(MaterialTypes m : MaterialTypes.values()) {
			generateItems.add(m.meta);
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		
		super.getSubItems(tab, items);
		
		for(MaterialTypes m : MaterialTypes.values()) {
			items.add(new ItemStack(this, 1, m.meta));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if(MaterialTypes.get(stack) != null)
			return getUnlocalizedName() + "." + MaterialTypes.get(stack).getName();
		
		return "null";
	}
	
	@Override
	public void registerModels() {
		
		super.registerModels();
		
		for(short i : generateItems) {
			//String resource = InfinitesimalZeros.MODID + ":ingot";
			//ModelResourceLocation model = new ModelResourceLocation(resource, "inventory");
			//resources.put(resource, model);
			//ModelLoader.registerItemVariants(this, model);
			//ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(resource, "inventory"));
			//ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("ingot", "inventory"));
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("infinitesimalzeros:ingot", "inventory"));
			//ModelLoader.setCustomModelResourceLocation(this, (int) i, new ModelResourceLocation(new ResourceLocation(InfinitesimalZeros.MODID, "ingot"), "inventory"));
		}
		
		/*ModelLoader.setCustomMeshDefinition(this, stack -> {
			MaterialTypes m = MaterialTypes.get(stack);
			
			if(stack != null) {
				return resources.get(InfinitesimalZeros.MODID + ":ingot");
			}
			
			return null;
		});*/
	}
	
	public static enum MaterialTypes implements IStringSerializable {
		LITHIUM(0),
		ALUMINIUM(1);
		
		public short meta;
		
		MaterialTypes(int meta) {
			
			this.meta = (short) meta;
		}
		
		public static MaterialTypes get(ItemStack stack) {
			
			for(MaterialTypes m : values()) {
				if(m.meta == stack.getItemDamage())
					return m;
			}
			
			return null;
		}

		@Override
		public String getName() {
			
			return name().toLowerCase(Locale.ROOT);
		}
		
	}
	
	
	
}
