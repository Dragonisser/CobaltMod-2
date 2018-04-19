package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.items.ItemRedCabbageSeeds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCobaltFarmLand extends Block {
	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
	protected static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	protected BlockCobaltFarmLand() {
		super(Material.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, Integer.valueOf(0)));
		this.setUnlocalizedName("cobalt_farmland");
		this.setRegistryName("cobalt_farmland");
		this.setSoundType(SoundType.GROUND);
		this.setTickRandomly(true);
		this.setLightOpacity(255);
		this.setHardness(0.6F);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FARMLAND_AABB;
	}

	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
			net.minecraftforge.common.IPlantable plantable) {
		super.canSustainPlant(state, world, pos, direction, plantable);
		if (plantable instanceof ItemRedCabbageSeeds || plantable instanceof BlockRedCabbageCrop) {
			return true;
		}

		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		int i = ((Integer) state.getValue(MOISTURE)).intValue();

		if (!this.hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
			if (i > 0) {
				worldIn.setBlockState(pos, state.withProperty(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!this.hasCrops(worldIn, pos)) {
				this.turnToDirt(worldIn, pos);
			}
		} else if (i < 7) {
			worldIn.setBlockState(pos, state.withProperty(MOISTURE, Integer.valueOf(7)), 2);
		}
	}

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (!worldIn.isRemote && entityIn.canTrample(worldIn, this, pos, fallDistance)) // Forge:
																						// Move
																						// logic
																						// to
																						// Entity#canTrample
		{
			this.turnToDirt(worldIn, pos);
		}

		super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
	}

	private void turnToDirt(World p_190970_1_, BlockPos p_190970_2_) {
		IBlockState iblockstate = CMContent.COBALT_DIRT.getDefaultState();
		p_190970_1_.setBlockState(p_190970_2_, iblockstate);
		AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(p_190970_1_, p_190970_2_).offset(p_190970_2_);

		for (Entity entity : p_190970_1_.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb)) {
			entity.setPosition(entity.posX, axisalignedbb.maxY, entity.posZ);
		}
	}

	private boolean hasCrops(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof net.minecraftforge.common.IPlantable && canSustainPlant(worldIn.getBlockState(pos),
				worldIn, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) block);
	}

	private boolean hasWater(World worldIn, BlockPos pos) {
		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4),
				pos.add(4, 1, 4))) {
			if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			this.turnToDirt(worldIn, pos);
		}
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			this.turnToDirt(worldIn, pos);
		}
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		switch (side) {
		case UP:
			return true;
		case NORTH:
		case SOUTH:
		case WEST:
		case EAST:
			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
			Block block = iblockstate.getBlock();
			return !iblockstate.isOpaqueCube() && block != CMContent.COBALT_FARMLAND;
		default:
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CMContent.COBALT_DIRT.getItemDropped(CMContent.COBALT_DIRT.getDefaultState(), rand, fortune);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(MOISTURE, Integer.valueOf(meta & 7));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(MOISTURE)).intValue();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { MOISTURE });
	}
}