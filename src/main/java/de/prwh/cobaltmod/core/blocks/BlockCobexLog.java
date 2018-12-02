package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCobexLog extends BlockLog {

	public BlockCobexLog() {
		super();
		this.setTranslationKey("cobex_log");
		this.setRegistryName("cobex_log");
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		this.setTickRandomly(true);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (!worldIn.isRemote) {
			for (int i = 0; i < 20; ++i) {
				BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

				if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
					return;
				}

				IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

				if (iblockstate1.getBlock() == Blocks.LEAVES || iblockstate1.getBlock() == Blocks.LEAVES2) {
					worldIn.setBlockState(blockpos, CMContent.COBEX_LEAVES.getDefaultState());
				} else if (iblockstate1.getBlock() == Blocks.LOG || iblockstate1.getBlock() == Blocks.LOG2) {
					worldIn.setBlockState(blockpos, CMContent.COBEX_LOG.getDefaultState());
				} else if (iblockstate1.getBlock() == Blocks.GRASS) {
					worldIn.setBlockState(blockpos, CMContent.COBALT_GRASS.getDefaultState());
				}
			}

		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();

		switch (meta & 12) {
		case 0:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			break;
		case 4:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			break;
		case 8:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			break;
		default:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@SuppressWarnings("incomplete-switch")
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		switch ((BlockLog.EnumAxis) state.getValue(LOG_AXIS)) {
		case X:
			i |= 4;
			break;
		case Z:
			i |= 8;
			break;
		case NONE:
			i |= 12;
		}

		return i;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LOG_AXIS });
	}
}