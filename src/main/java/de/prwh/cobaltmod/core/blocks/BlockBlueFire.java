package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.BlockFire;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlueFire extends BlockFire {
	protected BlockBlueFire() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false))
				.withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(UPPER, Boolean.valueOf(false)));
		this.setTickRandomly(true);
		this.setUnlocalizedName("blue_fire");
		this.setRegistryName("blue_fire");
		this.setHardness(0.0F);
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.CLOTH);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!(CMContent.PORTAL_COBALT).trySpawnPortal(worldIn, pos) && !(CMContent.PORTAL_CAVES).trySpawnPortal(worldIn, pos)) {
			if (!worldIn.getBlockState(pos.down()).isFullCube() && !this.canNeighborCatchFire(worldIn, pos)) {
				worldIn.setBlockToAir(pos);
			} else {
				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
			}
		}
	}

	private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
				return true;
			}
		}

		return false;
	}

	public boolean isBurning(IBlockAccess world, BlockPos pos) {
		return true;
	}

	public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
	}
}