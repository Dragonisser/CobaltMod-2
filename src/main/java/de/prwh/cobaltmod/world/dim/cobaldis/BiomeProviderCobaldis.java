package de.prwh.cobaltmod.world.dim.cobaldis;

import de.prwh.cobaltmod.world.biome.CMBiomeGenBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class BiomeProviderCobaldis extends BiomeProvider {

	public BiomeProviderCobaldis(World world) {
		super(world.getWorldInfo());
		allowedBiomes.clear();
		allowedBiomes.add(CMBiomeGenBase.biomeforest);
		allowedBiomes.add(CMBiomeGenBase.biomehills);
		allowedBiomes.add(CMBiomeGenBase.biomemountains);
		allowedBiomes.add(CMBiomeGenBase.biomeswamp);
		allowedBiomes.add(CMBiomeGenBase.biometall);

		getBiomesToSpawnIn().clear();
		getBiomesToSpawnIn().add(CMBiomeGenBase.biomeforest);
	}

	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		GenLayer biomes = new GenLayerBiomesCobaldis(1);

		biomes = new GenLayerBiomesCobaldis(1000, biomes);
		biomes = new GenLayerZoom(1000, biomes);
		biomes = new GenLayerZoom(1001, biomes);
		biomes = new GenLayerZoom(1002, biomes);
		biomes = new GenLayerZoom(1003, biomes);
		biomes = new GenLayerZoom(1004, biomes);

		GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10L, biomes);
		biomeIndexLayer.initWorldGenSeed(seed);

		return new GenLayer[] { biomes, biomeIndexLayer };
	}
}