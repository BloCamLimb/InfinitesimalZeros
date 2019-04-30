package infinitesimalzeros.common.core.handler;

import org.lwjgl.input.Keyboard;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.client.gui.GuiModeSelect;
import infinitesimalzeros.common.registry.RegistryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = InfinitesimalZeros.MODID)
public class ClientTickHandler {
	
	@SubscribeEvent
	public static void clientTick(ClientTickEvent event) {
		
		if(event.phase != Phase.END)
			return;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		GuiScreen gui = mc.currentScreen;
		
		if(gui == null || !gui.doesGuiPauseGame())
			if(gui == null && Keyboard.isKeyDown(Keyboard.KEY_F)) {
				
				ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
				if(!stack.isEmpty() && stack.getItem() == RegistryItems.wrench) {
					InfinitesimalZeros.logger.info("KEY PRESSED");
					mc.displayGuiScreen(new GuiModeSelect());
				}
			}
	}
	
}
