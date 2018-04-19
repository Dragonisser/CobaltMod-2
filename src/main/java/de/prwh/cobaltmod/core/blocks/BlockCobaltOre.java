package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCobaltOre extends Block {

	public BlockCobaltOre() {
		super(Material.ROCK);
		this.setUnlocalizedName("cobalt_ore");
		this.setRegistryName("cobalt_ore");
		this.setHardness(5.0F);
		this.setResistance(2000F);
		this.setSoundType(SoundType.STONE);
	}
}