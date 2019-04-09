package infinitesimalzeros.common.render;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.BossInfo.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import infinitesimalzeros.common.item.Neutron;
import infinitesimalzeros.common.item.NeutronColor;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.util.interfaces.IModelRender;
import net.minecraft.client.Minecraft;

public class ColorItemRenderer {
	
	@SideOnly(Side.CLIENT)
	protected int getColorForItemStack(ItemStack stack, int tintIndex) {
		
		int s = 0xFFFFFF;
		NBTTagCompound nbt = stack.getTagCompound();
		nbt = new NBTTagCompound();
		String name = nbt.getString("meta");
		try {
			s = NeutronColor.valueOf(name).getColor();
		} catch (Exception e) {}
		return s;
	}
	
	@SideOnly(Side.CLIENT)
	public void renderItem() {
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(this::getColorForItemStack, RegistryItems.neutron);
	}
	
}
