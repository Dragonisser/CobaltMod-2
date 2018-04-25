package de.prwh.cobaltmod.core;

import de.prwh.cobaltmod.core.blocks.CMBlocks;
import de.prwh.cobaltmod.core.items.CMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxyCobalt extends CommonProxyCobalt {

	public void init() {
		CMBlocks.initTextures();
		CMItems.initTextures();
	}

	/**
	 * Note that if you simply return 'Minecraft.getMinecraft().thePlayer', your
	 * packets will not work as expected because you will be getting a client player
	 * even when you are on the server! Sounds absurd, but it's true.
	 *
	 * Solution is to double-check side before returning the player:
	 */
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {

		return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
	}
}