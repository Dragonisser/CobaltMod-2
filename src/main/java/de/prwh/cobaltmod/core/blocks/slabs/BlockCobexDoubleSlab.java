package de.prwh.cobaltmod.core.blocks.slabs;

import java.util.Random;

import javax.annotation.Nullable;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCobexDoubleSlab extends CMSlab {

	public BlockCobexDoubleSlab() {
		super(Material.ROCK);
		this.setHardness(0.7F);
	}

	@Override
	public boolean isDouble() {
		return true;
	}

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}
	
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(CMContent.COBEX_HALF_SLAB);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(CMContent.COBEX_HALF_SLAB);
	}

}
