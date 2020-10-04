package de.prwh.cobaltmod.world.dim.deep_caves;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.world.dim.cobaldis.BiomeProviderCobaldis;
import de.prwh.cobaltmod.world.dim.cobaldis.ChunkGeneratorCobaldis;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderDeepCaves extends WorldProvider {

	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderCobaldis(this.world);
		this.setAllowedSpawnTypes(false, false);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorCobaldis(this.world, "");
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
