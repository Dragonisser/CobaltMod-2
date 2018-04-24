package de.prwh.cobaltmod.world.biome;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CMBiomeGenBase extends Biome {

	public static Biome biomeforest;
	public static Biome biomemountains;
	public static Biome biomeswamp;
	public static Biome biometall;
	public static Biome biomehills;
	public static Biome biomecaves;
	public static BiomeDecorator biomeDec;
	
	
	public CMBiomeGenBase(BiomeProperties properties) {
		super(properties);
		properties.setRainDisabled();
		properties.setRainfall(0.0F);
		this.theBiomeDecorator.flowersPerChunk = 4;
		CMBiomeGenBase.biomeDec = this.theBiomeDecorator;
	}
	
	public static BiomeDecorator getBiomeDecorator() {
		return biomeDec;
	}
	

	public static void init() {
		biomemountains = new BiomeGenCobaltMountains(new Biome.BiomeProperties("Higherlands").setBaseHeight(2.0F).setHeightVariation(0.25F))
				.setRegistryName("higherlands");
		biomeforest = new BiomeGenCobexForest(new Biome.BiomeProperties("Cobex Forest").setBaseHeight(0.1F).setHeightVariation(0.2F))
				.setRegistryName("cobex_forest");
		biomeswamp = new BiomeGenCobaltSwamp(new Biome.BiomeProperties("Deep Swamp").setBaseHeight(-0.2F).setHeightVariation(0.1F)).setRegistryName("deep_swamp");
		biometall = new BiomeGenCobaltTall(new Biome.BiomeProperties("Tall Forest").setBaseHeight(0.3F).setHeightVariation(0.2F)).setRegistryName("tall_forest");
		biomehills = new BiomeGenCobaltHills(new Biome.BiomeProperties("Blue Hills").setBaseHeight(0.8F).setHeightVariation(0.3F)).setRegistryName("blue_hills");
		biomecaves = new BiomeGenCobaltCaves(new Biome.BiomeProperties("Cobalt Caves")).setRegistryName("cobalt_caves");

		// biomeswamp = new BiomeGenCobaltSwamp(CMMain.biomeswampid,
		// false).setBiomeName("Deep Swamp");
		// biometall = new BiomeGenCobaltTall(CMMain.biometallid,
		// false).setBiomeName("Tall Forest");
		// biomehills = new BiomeGenCobaltHills(CMMain.biomehillsid,
		// false).setBiomeName("Blue Mountains");
		// biomecaves = new BiomeGenCobaltCaves(CMMain.biomecavesid,
		// false).setBiomeName("Cobalt Caves");

		GameRegistry.register(biomemountains);
		GameRegistry.register(biomeforest);
		GameRegistry.register(biomeswamp);
		GameRegistry.register(biometall);
		GameRegistry.register(biomehills);
		GameRegistry.register(biomecaves);
	}

	public static void registerWithBiomeDictionary() {

	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		int i = worldIn.getSeaLevel();
		IBlockState iblockstate = this.topBlock;
		IBlockState iblockstate1 = this.fillerBlock;
		int j = -1;
		int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int j1 = 255; j1 >= 0; --j1) {
			if (j1 <= rand.nextInt(5)) {
				chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
			} else {
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

				if (iblockstate2.getMaterial() == Material.AIR) {
					j = -1;
				} else if (iblockstate2.getBlock() == CMContent.CORRUPTED_STONE) {
					if (j == -1) {
						if (k <= 0) {
							iblockstate = AIR;
							iblockstate1 = CMContent.CORRUPTED_STONE.getDefaultState();
						} else if (j1 >= i - 4 && j1 <= i + 1) {
							iblockstate = this.topBlock;
							iblockstate1 = this.fillerBlock;
						}

						if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
							if (this.getFloatTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
								// iblockstate = ICE;
							} else {
								iblockstate = WATER;
							}
						}

						j = k;

						if (j1 >= i - 1) {
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						} else if (j1 < i - 7 - k) {
							iblockstate = AIR;
							iblockstate1 = CMContent.CORRUPTED_STONE.getDefaultState();
							// chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
						} else {
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
						}
					} else if (j > 0) {
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
				}
			}
		}
	}
}