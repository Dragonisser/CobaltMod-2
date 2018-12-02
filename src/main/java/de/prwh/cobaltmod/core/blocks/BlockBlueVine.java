package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.BlockVine;
import net.minecraft.block.SoundType;

public class BlockBlueVine extends BlockVine {
	public BlockBlueVine() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setTranslationKey("blue_vine");
		this.setRegistryName("blue_vine");
		this.setHardness(0.2F);
		this.setSoundType(SoundType.PLANT);
	}
}
