package de.prwh.cobaltmod.world.dim;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class CMWorldType extends WorldType {

	public CMWorldType() {
		super("Cobaltis");
	}

	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new CMBiomeProvider(world);
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new CMChunkGenerator(world, generatorOptions);
	}
}
