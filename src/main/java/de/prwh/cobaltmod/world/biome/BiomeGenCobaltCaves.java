package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobaltCaves extends CMBiomeGenBase {

	public BiomeGenCobaltCaves(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Cobalt Caves");
		properties.setRainDisabled();
		this.topBlock = CMContent.COBALT_GRASS.getDefaultState();
		this.fillerBlock = CMContent.COBALT_DIRT.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}