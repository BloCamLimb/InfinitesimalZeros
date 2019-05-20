package infinitesimalzeros.common.core.handler;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.capabilities.CapabilityBankProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	
	public static final ResourceLocation BANK_SYSTEM = new ResourceLocation(InfinitesimalZeros.MODID, "bank");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		
		if(event.getObject() instanceof EntityPlayer) {
			event.addCapability(BANK_SYSTEM, new CapabilityBankProvider());
		}
	}
}
