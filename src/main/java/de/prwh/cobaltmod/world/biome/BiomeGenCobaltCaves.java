package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobaltCaves extends CMBiomeGenBase {

	public BiomeGenCobaltCaves(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Cobalt Caves");
		properties.setRainDisabled();
		this.topBlock = CMContent.CORRUPTED_STONE.getDefaultState();
		this.fillerBlock = CMContent.HARDENED_CORRUPTED_STONE.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}