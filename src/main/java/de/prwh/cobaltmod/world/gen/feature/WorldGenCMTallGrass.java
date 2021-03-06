package de.prwh.cobaltmod.world.gen.feature;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCMTallGrass extends WorldGenerator {
	private final IBlockState tallGrassState;

	public WorldGenCMTallGrass() {
		this.tallGrassState = CMContent.BLUE_TALL_GRASS.getDefaultState();
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (IBlockState iblockstate = worldIn
				.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position)
						|| iblockstate.getBlock().isLeaves(iblockstate, worldIn, position))
						&& position.getY() > 0; iblockstate = worldIn.getBlockState(position)) {
			position = position.down();
		}

		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));

			if (worldIn.isAirBlock(blockpos) && CMContent.BLUE_TALL_GRASS.canPlaceBlockAt(worldIn, blockpos)) {
				worldIn.setBlockState(blockpos, this.tallGrassState, 2);
			}
		}

		return true;
	}
}