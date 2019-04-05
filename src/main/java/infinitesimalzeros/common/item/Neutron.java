package infinitesimalzeros.common.item;

import infinitesimalzeros.InfinitesimalZeros;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class Neutron extends Item {

	private static final String name = "neutron";
	public HashMap<String, Integer> colorMap = new HashMap<>();
	
	
	public Neutron() {
		super();
        this.setCreativeTab(CreativeTabs.MATERIALS);
	}
	

	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String ss = "any";
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) {
			nbt = new NBTTagCompound();
		}
		if(nbt.getString("meta") != "") {
			ss = nbt.getString("meta").toLowerCase();
		}
		String sss = I18n.format("material."+ss, ss);
		return super.getItemStackDisplayName(stack)+" ("+sss+")";
	}
	
	
	
}
