package de.prwh.cobaltmod.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCMFlowers extends WorldGenerator {
	private BlockBush flower;
	private IBlockState state;

	public WorldGenCMFlowers(Block flowerIn) {
		this.state = flowerIn.getDefaultState();
		this.flower = (BlockBush) flowerIn;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < 6; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));

			if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.hasNoSky() || blockpos.getY() < 255)
					&& this.flower.canBlockStay(worldIn, blockpos, this.state)) {
				worldIn.setBlockState(blockpos, this.state, 2);
			}
		}

		return true;
	}
}