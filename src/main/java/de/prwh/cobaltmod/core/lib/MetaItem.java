package de.prwh.cobaltmod.core.lib;

import java.util.List;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//dummy class 
public abstract class MetaItem extends Item {

	public MetaItem() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CMMain.cobalttabblocks);
	}

	public abstract String getUnlocalizedName(ItemStack stack);

	@SuppressWarnings({ "rawtypes" })
	@SideOnly(Side.CLIENT)
	public abstract void getSubItems(Item itemIn, CreativeTabs tab, List subItems);

}
