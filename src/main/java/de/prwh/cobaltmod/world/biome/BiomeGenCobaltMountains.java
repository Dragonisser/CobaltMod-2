package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobaltMountains extends CMBiomeGenBase {

	public BiomeGenCobaltMountains(BiomeProperties properties) {
		super(properties);
		this.topBlock = CMContent.COBALT_GRASS.getDefaultState();
		this.fillerBlock = CMContent.COBALT_DIRT.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}
