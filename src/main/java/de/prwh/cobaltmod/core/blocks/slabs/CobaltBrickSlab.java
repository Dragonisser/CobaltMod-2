package de.prwh.cobaltmod.core.blocks.slabs;

import java.util.Random;

import javax.annotation.Nullable;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CobaltBrickSlab extends BlockSlab {

	public static final PropertyEnum<CobaltBrickSlab.Variant> VARIANT = PropertyEnum.<CobaltBrickSlab.Variant>create(
			"variant", CobaltBrickSlab.Variant.class);

	public CobaltBrickSlab() {
		super(Material.ROCK);
		this.setHardness(1.5F);
		IBlockState iblockstate = this.blockState.getBaseState();

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}

		this.setDefaultState(iblockstate.withProperty(VARIANT, CobaltBrickSlab.Variant.DEFAULT));
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(CMContent.COBALT_HALF_SLAB);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(CMContent.COBALT_HALF_SLAB);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, CobaltBrickSlab.Variant.DEFAULT);

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF,
					(meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] { VARIANT })
				: new BlockStateContainer(this, new IProperty[] { HALF, VARIANT });
	}

	/**
	 * Returns the slab block name with the type associated with it
	 */
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}

	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	public Comparable<?> getTypeForItem(ItemStack stack) {
		return CobaltBrickSlab.Variant.DEFAULT;
	}

	public static class Double extends CobaltBrickSlab {
		public boolean isDouble() {
			return true;
		}
	}

	public static class Half extends CobaltBrickSlab {
		public boolean isDouble() {
			return false;
		}
	}

	public static enum Variant implements IStringSerializable {
		DEFAULT;

		public String getName() {
			return "default";
		}
	}
}
