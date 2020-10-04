package de.prwh.cobaltmod.core;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCobalt extends CreativeTabs {

	private int tab;

	public CreativeTabCobalt(int par1, String par2Str, int par3) {
		super(par1, par2Str);
		tab = par3;
	}

	@Override
	public ItemStack getTabIconItem() {
		if (tab == 1) {
			return new ItemStack(CMContent.COBALT_INGOT);
		} else {
			return new ItemStack((CMContent.COBALT_ORE));
		}
	}
}
