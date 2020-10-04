package de.prwh.cobaltmod.core.blocks.slabs;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class CMSlab extends BlockSlab {

	public static final PropertyEnum<CMSlab.Variant> VARIANT = PropertyEnum.<CMSlab.Variant>create(
			"variant", CMSlab.Variant.class);
	
	public CMSlab(Material material) {
		super(material);
		IBlockState iblockstate = this.blockState.getBaseState();
		this.useNeighborBrightness = true;
		
		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}

		this.setDefaultState(iblockstate.withProperty(VARIANT, CMSlab.Variant.DEFAULT));
	}

    public boolean isOpaqueCube(IBlockState state)
    {
        return this.isDouble();
    }
    


	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, CMSlab.Variant.DEFAULT);

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
//	public String getUnlocalizedName(int meta) {
//		return super.getTranslationKey();
//	}

	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	public Comparable<?> getTypeForItem(ItemStack stack) {
		return CMSlab.Variant.DEFAULT;
	}

	public static class Double extends CMSlab {
		public Double(Material material) {
			super(material);
		}

		public boolean isDouble() {
			return true;
		}

		@Override
		public String getUnlocalizedName(int meta) {
			return super.getUnlocalizedName();
		}
	}

	public static class Half extends CMSlab {
		public Half(Material material) {
			super(material);
		}

		public boolean isDouble() {
			return false;
		}

		@Override
		public String getUnlocalizedName(int meta) {
			return super.getUnlocalizedName();
		}
	}

	public static enum Variant implements IStringSerializable {
		DEFAULT;

		public String getName() {
			return "default";
		}
	}
}
