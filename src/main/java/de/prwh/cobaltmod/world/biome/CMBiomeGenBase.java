package de.prwh.cobaltmod.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;

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
		this.decorator.flowersPerChunk = 4;
		CMBiomeGenBase.biomeDec = this.decorator;
	}
	
	public static List<BiomeData> biomedata = new ArrayList<BiomeData>();
	
	public static List<BiomeData> getBiomeList() {
		return biomedata;
	}
	
	public static void addToBiomeList(BiomeData biomedata) {
		getBiomeList().add(biomedata);
	}
	
	public static class BiomeData {
		public Biome biome = null;

		public BiomeData(Biome biome) {
			this.biome = biome;
		}

		@Override
		public String toString() {
			return "biome: " + this.biome.getBiomeName();
		}
	}
	
	public static BiomeDecorator getBiomeDecorator() {
		return biomeDec;
	}
	
	public static void init() {
		biomemountains = new BiomeGenCobaltMountains(new Biome.BiomeProperties("Higherlands").setBaseHeight(3.2F).setHeightVariation(0.25F))
				.setRegistryName("higherlands");
		biomeforest = new BiomeGenCobexForest(new Biome.BiomeProperties("Cobex Forest").setBaseHeight(0.2F).setHeightVariation(0.2F))
				.setRegistryName("cobex_forest");
		biomeswamp = new BiomeGenCobaltSwamp(new Biome.BiomeProperties("Deep Swamp").setBaseHeight(-0.2F).setHeightVariation(0.1F)).setRegistryName("deep_swamp");
		biometall = new BiomeGenCobaltTall(new Biome.BiomeProperties("Tall Forest").setBaseHeight(0.7F).setHeightVariation(0.2F)).setRegistryName("tall_forest");
		biomehills = new BiomeGenCobaltHills(new Biome.BiomeProperties("Blue Hills").setBaseHeight(1.4F).setHeightVariation(0.3F)).setRegistryName("blue_hills");
		biomecaves = new BiomeGenCobaltCaves(new Biome.BiomeProperties("Deep Caves")).setRegistryName("deep_caves");

		addToBiomeList(new BiomeData(biomemountains));
		addToBiomeList(new BiomeData(biomeforest));
		addToBiomeList(new BiomeData(biomeswamp));
		addToBiomeList(new BiomeData(biometall));
		addToBiomeList(new BiomeData(biomehills));
		addToBiomeList(new BiomeData(biomecaves));
	}

	public static void registerBiomes(IForgeRegistry<Biome> registry) {
		getBiomeList().forEach((BiomeData biome) -> {
			registry.registerAll(biome.biome);
			//CMMain.getLogger().info(biome.biome);
		});
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
							if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
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

	public static void initBiomeType() {
		BiomeDictionary.addTypes(biomemountains, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(biomeforest, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(biomeswamp, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET);
		BiomeDictionary.addTypes(biometall, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(biomehills, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.HILLS);
		BiomeDictionary.addTypes(biomecaves, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.WET);
	}
}