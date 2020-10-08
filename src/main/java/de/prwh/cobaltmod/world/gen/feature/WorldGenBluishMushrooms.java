package de.prwh.cobaltmod.world.gen.feature;

import java.util.Random;

import de.prwh.cobaltmod.core.blocks.BlockBluishMushroom;
import de.prwh.cobaltmod.core.blocks.BlockBluishMushroom.PlantVariations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBluishMushrooms extends WorldGenerator {
	private BlockBluishMushroom flower;
	private IBlockState state;

	public WorldGenBluishMushrooms(BlockBluishMushroom flowerIn) {
		this.state = flowerIn.getStateFromMeta((int) (Math.random() * PlantVariations.values().length));
		this.flower = (BlockBluishMushroom) flowerIn;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < 64; ++i) {
			
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			if (worldIn.isAirBlock(blockpos) && blockpos.getY() < worldIn.getHeight() - 1 && this.flower.canBlockStay(worldIn, blockpos, this.state)) {
				//System.out.println("Placing: " + blockpos + " " + this.state);
				worldIn.setBlockState(blockpos, this.state, 2);
			}
		}

		return true;
	}
}