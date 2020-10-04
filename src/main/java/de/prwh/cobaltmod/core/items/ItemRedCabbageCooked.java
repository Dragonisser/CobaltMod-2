package de.prwh.cobaltmod.core.items;

import net.minecraft.item.ItemFood;

public class ItemRedCabbageCooked extends ItemFood {

	public ItemRedCabbageCooked(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName("red_cabbage_cooked");
		this.setRegistryName("red_cabbage_cooked");
	}
}
