package de.prwh.cobaltmod.world.gen;

import java.util.Random;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.world.biome.BiomeGenCobaltHills;
import de.prwh.cobaltmod.world.biome.BiomeGenCobaltMountains;
import de.prwh.cobaltmod.world.biome.BiomeGenCobaltSwamp;
import de.prwh.cobaltmod.world.biome.BiomeGenCobaltTall;
import de.prwh.cobaltmod.world.biome.BiomeGenCobexForest;
import de.prwh.cobaltmod.world.biome.CMBiomeGenBase;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCMFlowers;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCMTallGrass;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCobaltMineable;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCobexBigTrees;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCobexTrees;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorDim implements IWorldGenerator {

	public BlockPos chunkPos;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		if (world.provider.getDimension() == 0 && !world.getWorldType().getName().equals(CMMain.COBALT_WORLD_TYPE.getName())) {
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		} else if (world.provider.getDimension() == CMMain.cobaltdimension || world.getWorldType().getName().equals(CMMain.COBALT_WORLD_TYPE.getName())) {
			generateCobaltis(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateCobaltis(World worldIn, Random rand, int x, int z) {
		int RandPosX = x + rand.nextInt(16) + 8;
		int RandPosZ = z + rand.nextInt(16) + 8;
		int height = worldIn.getHeight(RandPosX, RandPosZ);
		BlockPos pos = new BlockPos(RandPosX, height, RandPosZ);
		this.chunkPos = new BlockPos(RandPosX, 0, RandPosZ);
		Biome biome = worldIn.getBiome(pos);

		for (int k = 0; k < 3; k++) // How often it tries to spawn in a chunk
		{
			int oreXCoord = x + rand.nextInt(16) + 8;
			int oreYCoord = rand.nextInt(20) + 20; // Ebene 0 - 25
			int oreZCoord = z + rand.nextInt(16) + 8;

			new WorldGenCobaltMineable(CMContent.COBALT_ORE.getDefaultState(), 4).generate(worldIn, rand, new BlockPos(oreXCoord, oreYCoord, oreZCoord));
		}
		
		for (int k = 0; k < 5; k++) // How often it tries to spawn in a chunk
		{
			int oreXCoord = x + rand.nextInt(16) + 8;
			int oreYCoord = rand.nextInt(70); // Ebene 0 - 25
			int oreZCoord = z + rand.nextInt(16) + 8;

			new WorldGenCobaltMineable(CMContent.COBALT_STONE.getDefaultState(), 4).generate(worldIn, rand, new BlockPos(oreXCoord, oreYCoord, oreZCoord));
		}

		for (int l2 = 0; l2 < CMBiomeGenBase.getBiomeDecorator().flowersPerChunk; ++l2) {
			if (height > 0) {
				int rand_height = rand.nextInt(height);
				BlockPos blockpos1 = this.chunkPos.add(x, rand_height, z);
				BlockFlower.EnumFlowerType blockflower$enumflowertype = biome.pickRandomFlower(rand, blockpos1);
				BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();

				if (blockflower.getDefaultState().getMaterial() != Material.AIR) {
					double d1 = Math.random();
					if (d1 < 0.33) {
						new WorldGenCMFlowers(CMContent.BELL_FLOWER).generate(worldIn, rand, pos);
					} else if (d1 > 0.33 && d1 < 0.66) {
						new WorldGenCMFlowers(CMContent.CLEMATIS_FLOWER).generate(worldIn, rand, pos);
					} else {
						new WorldGenCMFlowers(CMContent.GLOW_FLOWER).generate(worldIn, rand, pos);
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) // How often it tries to spawn in a chunk
		{
			Block block = worldIn.getBlockState(pos).getBlock();
			boolean tree_gen = false;
			if (block != CMContent.COBEX_LOG) {
				for (int a = -5; a < 5; a++) {
					for (int b = -5; b < 5; b++) {
						block = worldIn.getBlockState(pos.add(a, 1, b)).getBlock();
						if (block != CMContent.COBEX_LOG) {
							tree_gen = true;
						} else {
							tree_gen = false;
						}

					}
				}

			}
			if (tree_gen) {
				if (worldIn.getBiome(pos) instanceof BiomeGenCobaltMountains || (worldIn.getBiome(pos) instanceof BiomeGenCobexForest || (worldIn.getBiome(pos) instanceof BiomeGenCobaltHills))) {
					new WorldGenCobexTrees(true, 4 + rand.nextInt(3)).generate(worldIn, rand, pos); // Tree
				}
				if (worldIn.getBiome(pos) instanceof BiomeGenCobaltSwamp) {
					new WorldGenCobexTrees(true, 4 + rand.nextInt(3), true).generate(worldIn, rand, pos); // Tree with vines
				}
				if (worldIn.getBiome(pos) instanceof BiomeGenCobaltTall) {
					new WorldGenCobexBigTrees(true, 11 + rand.nextInt(3), false).generate(worldIn, rand, pos); // Big Tree
				}
			}
			double d = Math.random();

			if (d < 0.33) {
				new WorldGenCMTallGrass().generate(worldIn, rand, pos);
			}
		}
	}

	@SuppressWarnings("unused")
	public void generateSurface(World worldIn, Random rand, int x, int z) {

		int RandPosX = x + rand.nextInt(16) + 8;
		int RandPosZ = z + rand.nextInt(16) + 8;
		int height = worldIn.getHeight(RandPosX, RandPosZ);
	}
}