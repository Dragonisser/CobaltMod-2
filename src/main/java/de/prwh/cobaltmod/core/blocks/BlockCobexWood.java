package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCobexWood extends Block {
	public BlockCobexWood() {
		super(Material.WOOD);
		this.setUnlocalizedName("cobex_wood");
		this.setRegistryName("cobex_wood");
		this.setHardness(2.0F);
		this.setSoundType(SoundType.WOOD);
	}
}
