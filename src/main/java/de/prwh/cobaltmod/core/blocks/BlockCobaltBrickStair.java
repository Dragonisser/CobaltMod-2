package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockCobaltBrickStair extends BlockStairs {

	protected BlockCobaltBrickStair(IBlockState modelState) {
		super(modelState);
		this.setTranslationKey("cobalt_brick_stairs");
		this.setRegistryName("cobalt_brick_stairs");
		this.setHardness(5F);
		this.setResistance(2000F);
		this.setSoundType(SoundType.STONE);
		this.useNeighborBrightness = true;
	}
}
