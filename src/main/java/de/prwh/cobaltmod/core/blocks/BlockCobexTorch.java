package de.prwh.cobaltmod.core.blocks;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;

public class BlockCobexTorch extends BlockTorch {
	public BlockCobexTorch () {
		this.setTranslationKey("cobex_torch");
		this.setRegistryName("cobex_torch");
		this.setHardness(0.0F);
		this.setLightLevel(0.9375F);
		this.setSoundType(SoundType.WOOD);
	}
}
