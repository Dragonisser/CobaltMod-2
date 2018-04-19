package de.prwh.cobaltmod.core.lib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemMultiTexture {

	public ItemBlockMeta(final Block b) {
		super(b, b, new ItemMultiTexture.Mapper() {
			@Override
			public String apply(ItemStack input) {
				if (b instanceof MetaBlock) {
					return ((MetaBlock) b).getUnlocalizedName(input);
				} else {
					return ((MetaBlockPlant) b).getUnlocalizedName(input);
				}

			}
		});
		this.setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName();
		Object addname = this.nameFunction.apply(stack);
		if (addname != null) {
			name += "_" + (String) addname;
		}
		return name;
	}
}
