package de.prwh.cobaltmod.world.dim;

import de.prwh.cobaltmod.core.CMMain;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CMWorldProvider extends WorldProvider {
	public void registerWorldChunkManager() {
		// this.biomeProvider = new BiomeProviderSingle(CMBiomeGenBase.biomeforest);
		this.setDimension(CMMain.cobaltdimension);
		this.setAllowedSpawnTypes(false, false);
		this.hasSkyLight = false;
	}

//	@Override
//	public IChunkGenerator createChunkGenerator() {
//		return new CMChunkGenerator(this.world, "");
//	}
//
//	public BiomeProvider getBiomeProvider() {
//		return new CMBiomeProvider(this.world);
//		// return this.biomeProvider = new
//		// BiomeProviderSingle(BiomeGenBaseCobalt.biomeplains);
//	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return CMMain.cobaltdimension;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering cobaltis";
	}

	@Override
	public DimensionType getDimensionType() {
		return CMMain.type_cobaltdimension;
	}

	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColor(net.minecraft.entity.Entity cameraEntity, float partialTicks) {

		World world = cameraEntity.world;
		float f = world.getCelestialAngle(partialTicks);
		float f1 = MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
		f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
		int i = MathHelper.floor(cameraEntity.posX);
		int j = MathHelper.floor(cameraEntity.posY);
		int k = MathHelper.floor(cameraEntity.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		int l = net.minecraftforge.client.ForgeHooksClient.getSkyBlendColour(world, blockpos);
		float f3 = (float) (l >> 16 & 255) / 255.0F;
		float f4 = (float) (l >> 8 & 255) / 255.0F;
		float f5 = (float) (l & 255) / 255.0F;
		f3 = f3 * f1;
		f4 = f4 * f1;
		f5 = f5 * f1;
		float f6 = world.getRainStrength(partialTicks);

		if (f6 > 0.0F) {
			float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
			float f8 = 1.0F - f6 * 0.75F;
			f3 = f3 * f8 + f7 * (1.0F - f8);
			f4 = f4 * f8 + f7 * (1.0F - f8);
			f5 = f5 * f8 + f7 * (1.0F - f8);
		}

		float f10 = world.getThunderStrength(partialTicks);

		if (f10 > 0.0F) {
			float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
			float f9 = 1.0F - f10 * 0.75F;
			f3 = f3 * f9 + f11 * (1.0F - f9);
			f4 = f4 * f9 + f11 * (1.0F - f9);
			f5 = f5 * f9 + f11 * (1.0F - f9);
		}

		if (world.getLastLightningBolt() > 0) {
			float f12 = (float) world.getLastLightningBolt() - partialTicks;

			if (f12 > 1.0F) {
				f12 = 1.0F;
			}

			f12 = f12 * 0.45F;
			f3 = f3 * (1.0F - f12) + 0.8F * f12;
			f4 = f4 * (1.0F - f12) + 0.8F * f12;
			f5 = f5 * (1.0F - f12) + 1.0F * f12;
		}

		return new Vec3d((double) f3, (double) f4, (double) f5);
	}

	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return true;
	}
}
