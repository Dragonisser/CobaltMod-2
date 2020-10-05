package de.prwh.cobaltmod.world.dim.deep_caves;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderDeepCaves extends WorldProvider {

	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderDeepCaves(this.world);
		this.setAllowedSpawnTypes(false, false);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorDeepCaves(this.world, false, this.world.getSeed());
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public DimensionType getDimensionType() {
		return CMMain.type_deep_caves;
	}
}
