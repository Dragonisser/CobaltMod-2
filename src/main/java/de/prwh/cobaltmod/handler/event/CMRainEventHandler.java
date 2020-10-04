package de.prwh.cobaltmod.handler.event;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CMRainEventHandler {

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {

		World world = event.getEntity().world;
		BlockPos pos = event.getEntity().getPosition();
		BlockPos bloPos = world.getTopSolidOrLiquidBlock(pos);

		if (event.getEntity().world.isRaining() && world.isRainingAt(bloPos) && (world.provider.getDimension() == CMMain.id_cobaldis || world.provider.getDimension() == CMMain.id_deep_caves)) {
			if (event.getEntity() instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
				NonNullList<ItemStack> armor = player.inventory.armorInventory;
				if (armor.get(3).getItem() != Item.getItemFromBlock(Blocks.AIR)) {
					return;
				}
				player.attackEntityFrom(DamageSource.MAGIC, 4F);
			}
		}
	}
}
