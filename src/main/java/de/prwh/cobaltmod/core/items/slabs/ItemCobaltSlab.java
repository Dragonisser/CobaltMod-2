package de.prwh.cobaltmod.core.items.slabs;

import de.prwh.cobaltmod.core.blocks.slabs.BlockCobaltDoubleSlab;
import de.prwh.cobaltmod.core.blocks.slabs.BlockCobaltHalfSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public class ItemCobaltSlab extends ItemSlab {
	/**
	 * Initializes a new instance of the ItemBlockStainedBrickSlab class.
	 * 
	 * @param block
	 *            the block behind the item.
	 * @param slab
	 *            the half height slab.
	 * @param doubleSlab
	 *            the full height slab.
	 * @param stacked
	 *            whether or not the block is the stacked version.
	 */
	public ItemCobaltSlab(final Block block, final BlockCobaltHalfSlab slab, final BlockCobaltDoubleSlab doubleSlab,
			final Boolean stacked) {
		super(block, slab, doubleSlab);
	}
}
