package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPortalFrame extends Block {

	public BlockPortalFrame() {
		super(Material.ROCK);
		this.setUnlocalizedName("portal_frame");
		this.setRegistryName("portal_frame");
		this.setBlockUnbreakable();
	}
}
