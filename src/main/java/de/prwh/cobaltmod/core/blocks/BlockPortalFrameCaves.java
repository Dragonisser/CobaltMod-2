package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPortalFrameCaves extends Block {

	public BlockPortalFrameCaves() {
		super(Material.ROCK);
		this.setUnlocalizedName("portal_frame_caves");
		this.setRegistryName("portal_frame_caves");
		this.setBlockUnbreakable();
	}
}
