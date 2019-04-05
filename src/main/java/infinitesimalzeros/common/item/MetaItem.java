package infinitesimalzeros.common.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.unification.stack.ItemMaterialInfo;
import infinitesimalzeros.common.util.RenderUtil;
import infinitesimalzeros.common.util.interfaces.IItemColorProvider;
import infinitesimalzeros.common.util.interfaces.IItemModelIndexProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class MetaItem<T extends MetaItem<?>.MetaValueItem> extends Item {
	
	private static final List<MetaItem<?>> META_ITEMS = new ArrayList<>();
	
	protected TShortObjectMap<T> metaItems = new TShortObjectHashMap<>();
	private Map<String, T> names = new HashMap<>();
	protected TShortObjectHashMap<ModelResourceLocation[]> specialItemsModels = new TShortObjectHashMap<>();
	protected TShortObjectMap<ModelResourceLocation> metaItemsModels = new TShortObjectHashMap<>();
	
	private static final ModelResourceLocation MISSING_LOCATION = new ModelResourceLocation("builtin/missing", "inventory");
	
    public static List<MetaItem<?>> getMetaItems() {
        return Collections.unmodifiableList(META_ITEMS);
    }
    
    protected final short metaItemOffset;

    public MetaItem(short metaItemOffset) {
        setUnlocalizedName("meta_item");
        setHasSubtypes(true);
        this.metaItemOffset = metaItemOffset;
        META_ITEMS.add(this);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerColor() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(this::getColorForItemStack, this);
    }
    
    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        T metaValueItem = getItem(stack);
        if(metaValueItem != null && metaValueItem.getColorProvider() != null) {
            return metaValueItem.getColorProvider().getItemStackColor(stack, tintIndex);
        }
        IFluidHandlerItem fluidContainerItem = ItemHandlerHelper.copyStackWithSize(stack, 1)
            .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(tintIndex == 0 && fluidContainerItem != null) {
            FluidStack fluidStack = fluidContainerItem.drain(Integer.MAX_VALUE, false);
            return fluidStack == null ? 0x666666 : RenderUtil.getFluidColor(fluidStack);
        }
        return 0xFFFFFF;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for(short itemMetaKey : metaItems.keys()) {
            T metaValueItem = metaItems.get(itemMetaKey);
            int numberOfModels = metaValueItem.getModelAmount();
            if (numberOfModels > 1) {
                ModelResourceLocation[] resourceLocations = new ModelResourceLocation[numberOfModels];
                for (int i = 0; i < resourceLocations.length; i++) {
                    ResourceLocation resourceLocation = new ResourceLocation(InfinitesimalZeros.MODID, formatModelPath(metaValueItem) + "/" + (i + 1));
                    ModelBakery.registerItemVariants(this, resourceLocation);
                    resourceLocations[i] = new ModelResourceLocation(resourceLocation, "inventory");
                }
                specialItemsModels.put((short) (metaItemOffset + itemMetaKey), resourceLocations);
                continue;
            }
            ResourceLocation resourceLocation = new ResourceLocation(InfinitesimalZeros.MODID, formatModelPath(metaValueItem));
            ModelBakery.registerItemVariants(this, resourceLocation);
            metaItemsModels.put((short) (metaItemOffset + itemMetaKey), new ModelResourceLocation(resourceLocation, "inventory"));
        }

        ModelLoader.setCustomMeshDefinition(this, itemStack -> {
            short itemDamage = formatRawItemDamage((short) itemStack.getItemDamage());
            if(specialItemsModels.containsKey(itemDamage)) {
                int modelIndex = getModelIndex(itemStack);
                return specialItemsModels.get(itemDamage)[modelIndex];
            }
            if(metaItemsModels.containsKey(itemDamage)) {
                return metaItemsModels.get(itemDamage);
            }
            return MISSING_LOCATION;
        });
    }
    
    protected int getModelIndex(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem != null && metaValueItem.getModelIndexProvider() != null) {
            return metaValueItem.getModelIndexProvider().getModelIndex(itemStack);
        }
        /*IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem != null) {
            return (int) Math.min(((electricItem.getCharge() / (electricItem.getMaxCharge() * 1.0)) * 7), 7);
        }*/
        return 0;
    }
    
    protected String formatModelPath(T metaValueItem) {
        return "metaitems/" + metaValueItem.unlocalizedName;
    }
    
    public void registerSubItems() {}
    
    public final T getItem(short metaValue) {
        return metaItems.get(formatRawItemDamage(metaValue));
    }
    
    public final T getItem(ItemStack itemStack) {
        return getItem((short) (itemStack.getItemDamage() - metaItemOffset));
    }
    
    protected short formatRawItemDamage(short metaValue) {
        return metaValue;
    }
    
    protected abstract T constructMetaValueItem(short metaValue, String unlocalizedName);

    public final T addItem(int metaValue, String unlocalizedName) {
        Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, metaValue + metaItemOffset, "MetaItem ID should be in range from 0 to Short.MAX_VALUE-1");
        T metaValueItem = constructMetaValueItem((short) metaValue, unlocalizedName);
        if(metaItems.containsKey((short) metaValue)) {
            T registeredItem = metaItems.get((short) metaValue);
            throw new IllegalArgumentException(String.format("MetaId %d is already occupied by item %s (requested by item %s)", metaValue, registeredItem.unlocalizedName, unlocalizedName));
        }
        metaItems.put((short) metaValue, metaValueItem);
        names.put(unlocalizedName, metaValueItem);
        return metaValueItem;
    }
    
    public class MetaValueItem {
    	
        public MetaItem<T> getMetaItem() {
            return MetaItem.this;
        }
        
        private int modelAmount = 1;
        
        public final int metaValue;
        private IItemModelIndexProvider modelIndexProvider;

        public final String unlocalizedName;
        
        public int getModelAmount() {
            return modelAmount;
        }
        
        @Nullable
        public IItemModelIndexProvider getModelIndexProvider() {
            return modelIndexProvider;
        }
        
        
        protected MetaValueItem(int metaValue, String unlocalizedName) {
            this.metaValue = metaValue;
            this.unlocalizedName = unlocalizedName;
        }
        
        public MetaValueItem setMaterialInfo(ItemMaterialInfo materialInfo) {
            if (materialInfo == null) {
                throw new IllegalArgumentException("Cannot add null ItemMaterialInfo.");
            }
            //OreDictUnifier.registerOre(getStackForm(), materialInfo);
            return this;
        }
    	   
        private IItemColorProvider colorProvider;
        
        @Nullable
        public IItemColorProvider getColorProvider() {
            return colorProvider;
        }
    	
    }

}
