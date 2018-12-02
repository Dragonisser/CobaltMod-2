package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCobaltDirt extends Block {
	public BlockCobaltDirt() {
		super(Material.GROUND);
		this.setSoundType(SoundType.GROUND);
		this.setRegistryName("cobalt_dirt");
		this.setTranslationKey("cobalt_dirt");
		this.setHardness(0.5F);
	}
}
