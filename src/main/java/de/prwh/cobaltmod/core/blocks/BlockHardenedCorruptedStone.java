package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockHardenedCorruptedStone extends Block {

	public BlockHardenedCorruptedStone() {
		super(Material.ROCK);
		this.setUnlocalizedName("hardened_corrupted_stone");
		this.setRegistryName("hardened_corrupted_stone");
		this.setHardness(3.0F);
	}
}
