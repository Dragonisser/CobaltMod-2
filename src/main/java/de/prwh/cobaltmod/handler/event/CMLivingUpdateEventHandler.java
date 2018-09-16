package de.prwh.cobaltmod.handler.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CMLivingUpdateEventHandler {

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {

		if (event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) event.getEntity();
			if (!thePlayer.getEntityData().hasKey("CM_UNTILPORTAL")) {
				thePlayer.getEntityData().setInteger("CM_UNTILPORTAL", 300);
			}
			if(!thePlayer.getEntityData().hasKey("CM_INPORTAL")) {
				thePlayer.getEntityData().setBoolean("CM_INPORTAL", false);
			}
			if(!thePlayer.getEntityData().hasKey("CM_PORTALCOUNTER")) {
				thePlayer.getEntityData().setInteger("CM_PORTALCOUNTER", 0);
			}
			
			
			if(!thePlayer.getEntityData().getBoolean("CM_INPORTAL")) {
				int portalcounter = thePlayer.getEntityData().getInteger("CM_PORTALCOUNTER");
				//System.out.println(portalcounter);
				if(portalcounter > 0) {
					thePlayer.getEntityData().setInteger("CM_PORTALCOUNTER", portalcounter - 4);
				} else if (portalcounter < 0) {
					thePlayer.getEntityData().setInteger("CM_PORTALCOUNTER", 0);
				}
			}
		}
//		if (event.entityLiving.isPotionActive(CMContent.potion_cobalt_resistance)) {
//			if (event.entityLiving.getActivePotionEffect(CMContent.potion_cobalt_resistance).getDuration() == 0) {
//				event.entityLiving.removePotionEffect(CMContent.potion_cobalt_resistance.id);
//				return;
//			}
//		}
//		if (event.entityLiving.isPotionActive(CMContent.potion_cobalt_confusion)) {
//			if (event.entityLiving.getActivePotionEffect(CMContent.potion_cobalt_confusion).getDuration() == 0) {
//				event.entityLiving.removePotionEffect(CMContent.potion_cobalt_confusion.id);
//				return;
//			}
//		}
	}
}