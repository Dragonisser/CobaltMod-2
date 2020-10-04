package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;

public class BlockCobexDoor extends BlockDoor {

	protected BlockCobexDoor(Material materialIn) {
		super(materialIn);
		this.setUnlocalizedName("cobex_door");
		this.setRegistryName("cobex_door");
	}
}
