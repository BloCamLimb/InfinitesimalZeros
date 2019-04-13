package infinitesimalzeros.common.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import gnu.trove.map.hash.TShortObjectHashMap;
import infinitesimalzeros.common.unification.MaterialBasis;
import infinitesimalzeros.common.unification.MaterialSetIcon;
import infinitesimalzeros.common.unification.OrePrefix;
import infinitesimalzeros.common.unification.material.DustMaterial;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialMetaItem extends StandardMetaItem {
	
	protected OrePrefix[] orePrefixes;
	private ArrayList<Short> generatedItems = new ArrayList<>();
	
	public MaterialMetaItem(OrePrefix... orePrefixes) {
		
		super((short) (1000 * orePrefixes.length));
		Preconditions.checkArgument(orePrefixes.length <= 32, "Max allowed OrePrefix count on MaterialMetaItem is 32.");
		this.orePrefixes = orePrefixes;
		for(MaterialBasis material : MaterialBasis.MATERIAL_REGISTRY) {
			int i = MaterialBasis.MATERIAL_REGISTRY.getIDForObject(material);
			for(int j = 0; j < orePrefixes.length; j++) {
				OrePrefix orePrefix = orePrefixes[j];
				if(orePrefix != null) {
					short metadata = (short) (j * 1000 + i);
					generatedItems.add(metadata);
				}
			}
		}
	}
	
	public void registerOreDict() {
		
		for(short metaItem : generatedItems) {
			OrePrefix prefix = this.orePrefixes[metaItem / 1000];
			MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObjectById(metaItem % 1000);
			// OreDictUnifier.registerOre(new ItemStack(this, 1, metaItem), prefix, material);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		
		super.registerModels();
		TShortObjectHashMap<ModelResourceLocation> alreadyRegistered = new TShortObjectHashMap<>();
		for(short metaItem : generatedItems) {
			OrePrefix prefix = this.orePrefixes[metaItem / 1000];
			MaterialSetIcon materialSetIcon = MaterialBasis.MATERIAL_REGISTRY.getObjectById(metaItem % 1000).materialIconSet;
			short registrationKey = (short) (metaItem / 1000 * 1000 + materialSetIcon.ordinal());
			if(!alreadyRegistered.containsKey(registrationKey)) {
				ResourceLocation resourceLocation = prefix.materialIconType.getItemModelPath(materialSetIcon);
				ModelBakery.registerItemVariants(this, resourceLocation);
				alreadyRegistered.put(registrationKey, new ModelResourceLocation(resourceLocation, "inventory"));
			}
			ModelResourceLocation resourceLocation = alreadyRegistered.get(registrationKey);
			metaItemsModels.put(metaItem, resourceLocation);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected int getColorForItemStack(ItemStack stack, int tintIndex) {
		
		if(tintIndex == 0 && stack.getMetadata() < metaItemOffset) {
			MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObjectById(stack.getMetadata() % 1000);
			if(material == null)
				return 0xFFFFFF;
			return material.materialRGB;
		}
		return super.getColorForItemStack(stack, tintIndex);
	}
	
	protected boolean canGenerate(OrePrefix orePrefix, MaterialBasis material) {
		
		return orePrefix.doGenerateItem(material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack itemStack) {
		
		if(itemStack.getItemDamage() < metaItemOffset) {
			MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000);
			OrePrefix prefix = orePrefixes[itemStack.getItemDamage() / 1000];
			if(material == null || prefix == null)
				return "";
			return prefix.getLocalNameForItem(material);
		}
		return super.getItemStackDisplayName(itemStack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		
		if(stack.getItemDamage() < metaItemOffset) {
			OrePrefix prefix = orePrefixes[stack.getItemDamage() / 1000];
			return 64;
		}
		return super.getItemStackLimit(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		
		super.getSubItems(tab, subItems);
		if(tab == CreativeTabs.SEARCH) {
			for(short metadata : generatedItems) {
				subItems.add(new ItemStack(this, 1, metadata));
			}
		}
	}
	
	/*
	 * @Override public void onUpdate(ItemStack itemStack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	 * if(itemStack.getItemDamage() < metaItemOffset && generatedItems.contains((short) itemStack.getItemDamage()) && entityIn instanceof
	 * EntityLivingBase) { EntityLivingBase entity = (EntityLivingBase) entityIn; MaterialBasis material =
	 * MaterialBasis.MATERIAL_REGISTRY.getObjectById(itemStack.getItemDamage() % 1000); OrePrefix prefix = orePrefixes[itemStack.getItemDamage() / 1000];
	 * if(worldIn.getTotalWorldTime() % 20 == 0) { if(prefix.heatDamage != 0.0) { if(prefix.heatDamage > 0.0 &&
	 * !GTUtility.isWearingFullHeatHazmat(entity)) { entity.attackEntityFrom(DamageSources.getHeatDamage(), prefix.heatDamage); } else
	 * if(prefix.heatDamage < 0.0 && !GTUtility.isWearingFullFrostHazmat(entity)) { entity.attackEntityFrom(DamageSources.getFrostDamage(),
	 * -prefix.heatDamage); } } if(material != null && material.isRadioactive() && GTUtility.isWearingFullRadioHazmat(entity)) {
	 * GTUtility.applyRadioactivity(entity, 1, itemStack.getCount()); } } } }
	 */
	
	@Override
	public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
		
		super.addInformation(itemStack, worldIn, lines, tooltipFlag);
		int damage = itemStack.getItemDamage();
		if(damage < this.metaItemOffset) {
			MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			OrePrefix prefix = this.orePrefixes[(damage / 1000)];
			if(material == null)
				return;
			addMaterialBasisTooltip(itemStack, prefix, material, lines, tooltipFlag);
		}
	}
	
	public MaterialBasis getMaterialBasis(ItemStack itemStack) {
		
		int damage = itemStack.getItemDamage();
		if(damage < this.metaItemOffset) {
			return MaterialBasis.MATERIAL_REGISTRY.getObjectById(damage % 1000);
		}
		return null;
	}
	
	public OrePrefix getOrePrefix(ItemStack itemStack) {
		
		int damage = itemStack.getItemDamage();
		if(damage < this.metaItemOffset) {
			return this.orePrefixes[(damage / 1000)];
		}
		return null;
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		
		int damage = itemStack.getItemDamage();
		if(damage < this.metaItemOffset) {
			MaterialBasis material = MaterialBasis.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			OrePrefix prefix = this.orePrefixes[(damage / 1000)];
			if(material instanceof DustMaterial) {
				DustMaterial dustMaterialBasis = (DustMaterial) material;
				return (int) (dustMaterialBasis.burnTime * prefix.materialAmount / 3268800);
			}
		}
		return super.getItemBurnTime(itemStack);
		
	}
	
	protected void addMaterialBasisTooltip(ItemStack itemStack, OrePrefix prefix, MaterialBasis material, List<String> lines, ITooltipFlag tooltipFlag) {}
	
}
