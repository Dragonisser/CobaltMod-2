package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobaltSwamp extends CMBiomeGenBase {

	public BiomeGenCobaltSwamp(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Deep Swamp");
		properties.setBaseHeight(-0.2F);
		properties.setHeightVariation(0.1F);
		properties.setRainDisabled();
		this.topBlock = CMContent.COBALT_GRASS.getDefaultState();
		this.fillerBlock = CMContent.COBALT_DIRT.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}