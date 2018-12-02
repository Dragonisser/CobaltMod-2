package de.prwh.cobaltmod.core.lib;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

///dummy class, important for registration
public abstract class MetaBlock extends Block {

	int meta = 0;

	/**
	 * creates a block with metadata
	 *
	 * @param material
	 *            the block material
	 * @param metaCount
	 *            the amount of "metadata"
	 * @param unlocname
	 */
	protected MetaBlock(Material material, String unlocname, int metaCount) {
		super(material);
		setCreativeTab(CMMain.cobalttabblocks);
		setTranslationKey(unlocname);
		this.meta = metaCount;
	}

	/**
	 * use in child!!!
	 */

	// /**
	// * creates the blockstates
	// */
	// protected abstract BlockState createBlockState();

	/**
	 * returns meta(number) according to state
	 */
	public abstract int getMetaFromState(IBlockState state);

	/**
	 * returns state according to meta
	 */
	public abstract IBlockState getStateFromMeta(int meta);

	/**
	 * returns additional Unlocalized Name based on stack (used by items)
	 *
	 * @param stack
	 * @return
	 */
	public abstract String getUnlocalizedName(ItemStack stack);

	// some basic methods, used by every meta block and very similar for each

	/**
	 * returns the itemdamage for drop
	 */
	public int damageDropped(IBlockState i) {
		return this.getMetaFromState(i);
	}

	/**
	 * creates list of all subblocks for creative tabs
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < meta; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	// example implementation
	public enum MetaBlockType implements IStringSerializable {
		; // no types by default

		private final String unlocalizedName;
		private int meta;

		private MetaBlockType(int meta, String unlocname) {
			this.meta = meta;
			this.unlocalizedName = unlocname;
		}

		private MetaBlockType(int meta) {
			this.meta = meta;
			this.unlocalizedName = this.name().toLowerCase();
		}

		public static MetaBlockType getTypeByMeta(int meta) {
			if (meta < 0 || meta >= MetaBlockType.values().length) {
				meta = 0;
			}
			return MetaBlockType.values()[meta];
		}

		public static String getNameByMeta(int meta) {
			return MetaBlockType.getTypeByMeta(meta).getUnlocalizedName();
		}

		public int getMeta() {
			return meta;
		}

		public String getUnlocalizedName() {
			return unlocalizedName;
		}

		@Override
		public String getName() {
			return this.unlocalizedName;
		}

		@Override
		public String toString() {
			return this.unlocalizedName;
		}

	}

}