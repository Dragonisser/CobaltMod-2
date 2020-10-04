package de.prwh.cobaltmod.world.dim;

import de.prwh.cobaltmod.world.dim.cobaldis.BiomeProviderCobaldis;
import de.prwh.cobaltmod.world.dim.cobaldis.ChunkGeneratorCobaldis;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class CMWorldType extends WorldType {

	public CMWorldType() {
		super("cobaldis");
	}

	@Override
	public BiomeProvider getBiomeProvider(World world) {
		return new BiomeProviderCobaldis(world);
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorCobaldis(world, generatorOptions);
	}
}
