package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.world.biome.BiomeGenCobaltSwamp;
import de.prwh.cobaltmod.world.gen.feature.WorldGenCobexTrees;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockCobexSapling extends BlockBush implements IGrowable {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D,
			0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public BlockCobexSapling() {
		super(Material.PLANTS);
		this.setUnlocalizedName("cobex_sapling");
		this.setRegistryName("cobex_sapling");
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return this.canSustainBush(soil);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getBlock() == this) // Forge: This function is called during
										// world gen and placement, before this
										// block is set, so if we are not 'here'
										// then assume it's the pre-check.
		{
			IBlockState soil = worldIn.getBlockState(pos.down());
			return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
		}
		return this.canSustainBush(worldIn.getBlockState(pos.down()));
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (((Integer) state.getValue(STAGE)).intValue() == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, pos, state, rand);
		}
	}

	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
			return;

		WorldGenerator worldgenerator = (WorldGenerator) new WorldGenCobexTrees(true, 4 + rand.nextInt(3));
		if (worldIn.getBiome(pos) instanceof BiomeGenCobaltSwamp) {
			worldgenerator = (WorldGenerator) new WorldGenCobexTrees(true, 4 + rand.nextInt(3), true);
		}
		
		
		
		int i = 0;
		int j = 0;
		boolean flag = false;

		IBlockState iblockstate2 = Blocks.AIR.getDefaultState();

		if (flag) {
			worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
			worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
		} else {
			worldIn.setBlockState(pos, iblockstate2, 4);
		}

		if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
			if (flag) {
				worldIn.setBlockState(pos.add(i, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
				worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
				worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
			} else {
				worldIn.setBlockState(pos, state, 4);
			}
		}
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called
	 * when the block gets destroyed. It returns the metadata of the dropped
	 * item based on the old metadata of the block.
	 */
	public int damageDropped(IBlockState state) {
		return 0;
	}

	/**
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((Integer) state.getValue(STAGE)).intValue() << 3;
		return i;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}
}