package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCobaltBlock extends Block {
	public BlockCobaltBlock() {
		super(Material.ROCK);
		this.setUnlocalizedName("cobalt_block");
		this.setRegistryName("cobalt_block");
		this.setHardness(5F);
		this.setResistance(10F);
		this.setSoundType(SoundType.STONE);
	}
}
