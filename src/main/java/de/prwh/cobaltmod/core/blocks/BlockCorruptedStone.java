package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCorruptedStone extends Block {
	public BlockCorruptedStone() {
		super(Material.ROCK);
		this.setUnlocalizedName("corrupted_stone");
		this.setRegistryName("corrupted_stone");
		this.setHardness(1.5F);
	}
}