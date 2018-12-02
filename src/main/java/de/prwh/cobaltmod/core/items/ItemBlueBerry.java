package de.prwh.cobaltmod.core.items;

import net.minecraft.item.ItemFood;

public class ItemBlueBerry extends ItemFood {

	public ItemBlueBerry(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setTranslationKey("blue_berry");
		this.setRegistryName("blue_berry");
	}
}
