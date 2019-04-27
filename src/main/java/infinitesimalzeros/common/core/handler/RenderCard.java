package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.items.ItemCard;
import infinitesimalzeros.common.registry.RegistryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = InfinitesimalZeros.MODID)
public class RenderCard {
	
	/*private static final ResourceLocation texture = new ResourceLocation(InfinitesimalZeros.MODID + ":textures/items/card.png");
	
	@SubscribeEvent
	public static void renderItem(RenderSpecificHandEvent event) {
		
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.player.getHeldItem(event.getHand()).getItem() != RegistryItems.card)
			return;
		event.setCanceled(true);
		try {
			renderItemInFirstPerson(mc.player, event.getPartialTicks(), event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	private static void renderItemInFirstPerson(AbstractClientPlayer player, float partialTicks, float interpPitch, EnumHand hand, float swingProgress, ItemStack stack, float equipProgress) throws Throwable {
		// Cherry picked from ItemRenderer.renderItemInFirstPerson
		boolean flag = hand == EnumHand.MAIN_HAND;
		EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
		GlStateManager.pushMatrix();
		boolean flag1 = enumhandside == EnumHandSide.RIGHT;
		float f = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
		float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2F));
		float f2 = -0.2F * MathHelper.sin(swingProgress * (float)Math.PI);
		int i = flag1 ? 1 : -1;
		GlStateManager.translate(i * f, f1, f2);
		transformSideFirstPerson(enumhandside, equipProgress);
		transformFirstPerson(enumhandside, swingProgress);
		doRender(enumhandside, partialTicks, stack);
		GlStateManager.popMatrix();
	}
	
	private static void doRender(EnumHandSide side, float partialTicks, ItemStack stack) {
		
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		int ticks = 0;
		GlStateManager.translate(0.3F + 0.02F * ticks, 0.475F + 0.01F * ticks, -0.2F - (side == EnumHandSide.RIGHT ? 0.035F : 0.01F) * ticks);
		GlStateManager.rotate(87.5F + ticks * (side == EnumHandSide.RIGHT ? 8 : 12), 0F, 1F, 0F);
		GlStateManager.rotate(ticks * 2.85F, 0F, 0F, 1F);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		GlStateManager.translate(-0.30F, -0.24F, -0.07F);
		GlStateManager.scale(0.0070F, 0.0070F, -0.0070F);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		font.drawString(TextFormatting.ITALIC + "" + TextFormatting.BOLD + ItemCard.getName(stack), 0, 0, 0xA07100);
		GlStateManager.popMatrix();
	}
	
	// Copy - ItemRenderer.transformSideFirstPerson
		// Arg - Side, EquipProgress
		private static void transformSideFirstPerson(EnumHandSide p_187459_1_, float p_187459_2_)
		{
			int i = p_187459_1_ == EnumHandSide.RIGHT ? 1 : -1;
			GlStateManager.translate(i * 0.56F, -0.44F + p_187459_2_ * -0.8F, -0.72F);
		}

		// Copy with modification - ItemRenderer.transformFirstPerson
		// Arg - Side, SwingProgress
		private static void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_)
		{
			int i = p_187453_1_ == EnumHandSide.RIGHT ? 1 : -1;
			// Botania - added
			GlStateManager.translate(p_187453_1_ == EnumHandSide.RIGHT ? 0.2F : 0.52F, -0.125F, p_187453_1_ == EnumHandSide.RIGHT ? 0.6F : 0.25F);
			GlStateManager.rotate(p_187453_1_ == EnumHandSide.RIGHT ? 60F : 120F, 0F, 1F, 0F);
			GlStateManager.rotate(30F, 0F, 0F, -1F);
			// End add
			float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float)Math.PI);
			GlStateManager.rotate(i * (45.0F + f * -20.0F), 0.0F, 1.0F, 0.0F);
			float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float)Math.PI);
			GlStateManager.rotate(i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(i * -45.0F, 0.0F, 1.0F, 0.0F);
		}*/
	
}
