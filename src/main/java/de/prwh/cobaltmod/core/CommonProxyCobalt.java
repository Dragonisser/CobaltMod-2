package de.prwh.cobaltmod.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxyCobalt {
	public int addArmor(String string) {
		return 0;
	}

	public void init() {
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
}