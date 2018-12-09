package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockCobexStair extends BlockStairs {

	protected BlockCobexStair(IBlockState modelState) {
		super(modelState);
		this.setTranslationKey("cobex_wood_stairs");
		this.setRegistryName("cobex_wood_stairs");
		this.setHardness(0.7F);
		this.setResistance(0.7F);
		this.setSoundType(SoundType.WOOD);
		this.useNeighborBrightness = true;
	}
}
