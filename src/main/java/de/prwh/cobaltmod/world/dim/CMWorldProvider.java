package de.prwh.cobaltmod.world.dim;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class CMWorldProvider extends WorldProvider {

	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new CMBiomeProvider(this.world);
		this.setAllowedSpawnTypes(false, false);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new CMChunkGenerator(this.world, "");
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
		return CMMain.type_cobaltdimension;
	}
}
