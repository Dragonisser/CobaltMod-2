package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCobexLeaves extends BlockLeaves {

	public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");

	public BlockCobexLeaves() {
		super();
		this.setUnlocalizedName("cobex_leaves");
		this.setRegistryName("cobex_leaves");
		this.setHardness(0.2F);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.setTickRandomly(true);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(CMContent.COBEX_SAPLING);
	}

	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote) {
			for (int i = 0; i < 5; ++i) {
				BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

				if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
					return;
				}

				IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

				if (iblockstate1.getBlock() == Blocks.LEAVES || iblockstate1.getBlock() == Blocks.LEAVES2) {
					worldIn.setBlockState(blockpos, CMContent.COBEX_LEAVES.getDefaultState());
				} else if (iblockstate1.getBlock() == Blocks.LOG || iblockstate1.getBlock() == Blocks.LOG2) {
					worldIn.setBlockState(blockpos, CMContent.COBEX_LOG.getDefaultState());
				}
			}

		}
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CHECK_DECAY, DECAYABLE });
	}

	@Override
	public NonNullList<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
		if (!(Boolean) state.getValue(CHECK_DECAY)) {
			world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
		}
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (!((Boolean) state.getValue(DECAYABLE)).booleanValue()) {
			i |= 4;
		}

		if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue()) {
			i |= 8;
		}

		return i;
	}
}
