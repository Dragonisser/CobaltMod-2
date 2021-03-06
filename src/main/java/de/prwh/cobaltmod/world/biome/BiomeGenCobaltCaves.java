package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobaltCaves extends CMBiomeGenBase {

	public BiomeGenCobaltCaves(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Deep Caves");
		properties.setRainDisabled();
		this.topBlock = CMContent.CORRUPTED_STONE.getDefaultState();
		this.fillerBlock = CMContent.HARDENED_CORRUPTED_STONE.getDefaultState();
		this.decorator.flowersPerChunk = 4;
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}