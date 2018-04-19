package de.prwh.cobaltmod.core.items;

import net.minecraft.item.ItemFood;

public class ItemRedCabbage extends ItemFood {

	public ItemRedCabbage(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName("red_cabbage");
		this.setRegistryName("red_cabbage");
	}
}
