package de.prwh.cobaltmod.world.biome;

import de.prwh.cobaltmod.core.api.CMContent;

public class BiomeGenCobexForest extends CMBiomeGenBase {

	public BiomeGenCobexForest(BiomeProperties properties) {
		super(properties);
		properties.setBaseBiome("Tall Forest");
		properties.setBaseHeight(0.1F);
		properties.setHeightVariation(0.2F);
		properties.setRainDisabled();
		this.topBlock = CMContent.COBALT_GRASS.getDefaultState();
		this.fillerBlock = CMContent.COBALT_DIRT.getDefaultState();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}
