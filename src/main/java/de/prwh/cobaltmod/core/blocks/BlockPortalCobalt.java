package de.prwh.cobaltmod.core.blocks;

import java.lang.reflect.Field;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.cache.LoadingCache;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.world.dim.CMTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPortalCobalt extends BlockPortal {

	public static String name;
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z });
	protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	protected int portalCounter;

	public BlockPortalCobalt() {
		super();

		name = "portal_cobalt";
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
		this.setTickRandomly(true);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setBlockUnbreakable();
		this.setLightLevel(0.75F);

	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing.Axis) state.getValue(AXIS)) {
		case X:
			return X_AABB;
		case Y:
		default:
			return Y_AABB;
		case Z:
			return Z_AABB;
		}
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	public static int getMetaForAxis(EnumFacing.Axis axis) {
		return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean trySpawnPortal(World worldIn, BlockPos pos) {
		BlockPortalCobalt.Size blockportal$size = new BlockPortalCobalt.Size(worldIn, pos, EnumFacing.Axis.X);

		if (blockportal$size.isValid() && blockportal$size.portalBlockCount == 0) {
			blockportal$size.placePortalBlocks();
			return true;
		} else {
			BlockPortalCobalt.Size blockportal$size1 = new BlockPortalCobalt.Size(worldIn, pos, EnumFacing.Axis.Z);

			if (blockportal$size1.isValid() && blockportal$size1.portalBlockCount == 0) {
				blockportal$size1.placePortalBlocks();
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis) state.getValue(AXIS);

		if (enumfacing$axis == EnumFacing.Axis.X) {
			BlockPortalCobalt.Size blockportal$size = new BlockPortalCobalt.Size(worldIn, pos, EnumFacing.Axis.X);

			if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		} else if (enumfacing$axis == EnumFacing.Axis.Z) {
			BlockPortalCobalt.Size blockportal$size1 = new BlockPortalCobalt.Size(worldIn, pos, EnumFacing.Axis.Z);

			if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		pos = pos.offset(side);
		EnumFacing.Axis enumfacing$axis = null;

		if (blockState.getBlock() == this) {
			enumfacing$axis = (EnumFacing.Axis) blockState.getValue(AXIS);

			if (enumfacing$axis == null) {
				return false;
			}

			if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST) {
				return false;
			}

			if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH) {
				return false;
			}
		}

		boolean flag = blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2)).getBlock() != this;
		boolean flag1 = blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2)).getBlock() != this;
		boolean flag2 = blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2)).getBlock() != this;
		boolean flag3 = blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2)).getBlock() != this;
		boolean flag4 = flag || flag1 || enumfacing$axis == EnumFacing.Axis.X;
		boolean flag5 = flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z;
		return flag4 && side == EnumFacing.WEST ? true : (flag4 && side == EnumFacing.EAST ? true : (flag5 && side == EnumFacing.NORTH ? true : flag5 && side == EnumFacing.SOUTH));
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random) {
		return 0;
	}

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {

		if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && (entityIn instanceof EntityPlayerMP)) {
			MinecraftServer mcServer = entityIn.getServer();
			EntityPlayerMP player = (EntityPlayerMP) entityIn;

			if (!pos.equals(getLastPortalPos(player))) {

				if (player.timeUntilPortal == 0) {
					if (player.getEntityData().getInteger("CM_UNTILPORTAL") > 0) {
						player.getEntityData().setInteger("CM_UNTILPORTAL", player.getEntityData().getInteger("CM_UNTILPORTAL") - 1);
					} else {
						player.getEntityData().setInteger("CM_UNTILPORTAL", 300);
					}
				}

				if (player.timeUntilPortal > 0) {
					player.timeUntilPortal = 10;
				} else if (player.capabilities.isCreativeMode || player.getEntityData().getInteger("CM_UNTILPORTAL") <= 0) {
					if (setPortalInformation(player, pos)) {
						if (player.dimension != CMMain.cobaltdimension) {
							mcServer.getPlayerList().transferPlayerToDimension(player, CMMain.cobaltdimension, new CMTeleporter(DimensionManager.getWorld(CMMain.cobaltdimension)));
							player.getEntityData().setInteger("CM_UNTILPORTAL", 300);
							player.timeUntilPortal = 15;
						} else {
							mcServer.getPlayerList().transferPlayerToDimension(player, 0, new CMTeleporter(DimensionManager.getWorld(0)));
							player.getEntityData().setInteger("CM_UNTILPORTAL", 300);
							player.timeUntilPortal = 15;
						}
					}

				}
			}

		}
	}

	/**
	 * Uses reflection to get the 'lastPortalPos' of the entity
	 * 
	 * @param entity
	 * @return
	 */
	private BlockPos getLastPortalPos(Entity entity) {
		Field lastPortalPos;

		try {
			lastPortalPos = getClassField(Entity.class, "lastPortalPos", "field_181016_an");

			return (BlockPos) lastPortalPos.get(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Uses reflection to set 'lastPortalPos', 'lastPortalVec' and
	 * 'teleportDirection' of the entity.
	 * 
	 * @param entity
	 * @param pos
	 * @return true/false
	 */
	private boolean setPortalInformation(Entity entity, BlockPos pos) {

		Field lastPortalPos;
		Field lastPortalVec;
		Field teleportDirection;

		try {

			lastPortalPos = getClassField(Entity.class, "lastPortalPos", "field_181016_an");
			lastPortalVec = getClassField(Entity.class, "lastPortalVec", "field_181017_ao");
			teleportDirection = getClassField(Entity.class, "teleportDirection", "field_181018_ap");

			lastPortalPos.set(entity, pos);
			BlockPattern.PatternHelper blockpattern$patternhelper = this.createPatternHelper(entity.world, pos);
			double d0 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double) blockpattern$patternhelper.getFrontTopLeft().getZ()
					: (double) blockpattern$patternhelper.getFrontTopLeft().getX();
			double d1 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? entity.posZ : entity.posX;
			d1 = Math.abs(MathHelper.pct(d1 - (double) (blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), d0,
					d0 - (double) blockpattern$patternhelper.getWidth()));
			double d2 = MathHelper.pct(entity.posY - 1.0D, (double) blockpattern$patternhelper.getFrontTopLeft().getY(),
					(double) (blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));

			lastPortalVec.set(entity, new Vec3d(d1, d2, 0.0D));
			teleportDirection.set(entity, blockpattern$patternhelper.getForwards());

			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * Uses the given Class, object and the two field names to try reflect a field
	 * in the given class
	 * 
	 * Just a test ¯\_(^_^)_/¯
	 * 
	 * System.out.println(getClassField(Entity.class, entityIn, "lastPortalPos1",
	 * "field_181016_an1"));
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param altFieldName
	 * @return
	 */
	private Field getClassField(Class<?> clazz, String fieldName, String altFieldName) {
		try {
			Field reflection;
			boolean developmentEnvironment = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
			
			if (developmentEnvironment) {
				reflection = clazz.getDeclaredField(fieldName);
				reflection.setAccessible(true);
				return reflection;
			} else {
				reflection = clazz.getDeclaredField(altFieldName);
				reflection.setAccessible(true);
				return reflection;
			}
		} catch (Exception ex) {
			CMMain.getLogger().error("ERROR - Reflection couldnt find Field - " + fieldName + " or " + altFieldName + ". Report to ModCreator");
			CMMain.getLogger().catching(ex);
		}
		return null;
	}

	/**
	 * Uses reflection to get 'lastPortalPos', 'lastPortalVec' and
	 * 'teleportDirection' of the entity and print it to console.
	 * 
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void printPortalInformation(Entity entity) {
		Field lastPortalPos;
		Field lastPortalVec;
		Field teleportDirection;

		try {
			lastPortalPos = getClassField(Entity.class, "lastPortalPos", "field_181016_an");
			lastPortalVec = getClassField(Entity.class, "lastPortalVec", "field_181017_ao");
			teleportDirection = getClassField(Entity.class, "teleportDirection", "field_181018_ap");

			System.out.println(lastPortalPos.get(entity));
			System.out.println(lastPortalVec.get(entity));
			System.out.println(teleportDirection.get(entity));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Nullable
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F,
					rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d0 = (double) ((float) pos.getX() + rand.nextFloat());
			double d1 = (double) ((float) pos.getY() + rand.nextFloat());
			double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
			double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				d0 = (double) pos.getX() + 0.5D + 0.25D * (double) j;
				d3 = (double) (rand.nextFloat() * 2.0F * (float) j);
			} else {
				d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) j;
				d5 = (double) (rand.nextFloat() * 2.0F * (float) j);
			}

			worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]); // TODO CHANGE THAT
		}
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return getMetaForAxis((EnumFacing.Axis) state.getValue(AXIS));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:

			switch ((EnumFacing.Axis) state.getValue(AXIS)) {
			case X:
				return state.withProperty(AXIS, EnumFacing.Axis.Z);
			case Z:
				return state.withProperty(AXIS, EnumFacing.Axis.X);
			default:
				return state;
			}

		default:
			return state;
		}
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AXIS });
	}

	public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_) {
		EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
		BlockPortalCobalt.Size blockportal$size = new BlockPortalCobalt.Size(worldIn, p_181089_2_, EnumFacing.Axis.X);
		LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

		if (!blockportal$size.isValid()) {
			enumfacing$axis = EnumFacing.Axis.X;
			blockportal$size = new BlockPortalCobalt.Size(worldIn, p_181089_2_, EnumFacing.Axis.Z);
		}

		if (!blockportal$size.isValid()) {
			return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
		} else {
			int[] aint = new int[EnumFacing.AxisDirection.values().length];
			EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
			BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

			for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values()) {
				BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(
						enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1),
						EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

				for (int i = 0; i < blockportal$size.getWidth(); ++i) {
					for (int j = 0; j < blockportal$size.getHeight(); ++j) {
						BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

						if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR) {
							++aint[enumfacing$axisdirection.ordinal()];
						}
					}
				}
			}

			EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

			for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values()) {
				if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()]) {
					enumfacing$axisdirection1 = enumfacing$axisdirection2;
				}
			}

			return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1),
					EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
		}
	}

	public static class Size {
		private final World world;
		private final EnumFacing.Axis axis;
		private final EnumFacing rightDir;
		private final EnumFacing leftDir;
		private int portalBlockCount;
		private BlockPos bottomLeft;
		private int height;
		private int width;

		public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
			this.world = worldIn;
			this.axis = p_i45694_3_;

			if (p_i45694_3_ == EnumFacing.Axis.X) {
				this.leftDir = EnumFacing.EAST;
				this.rightDir = EnumFacing.WEST;
			} else {
				this.leftDir = EnumFacing.NORTH;
				this.rightDir = EnumFacing.SOUTH;
			}

			for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0
					&& this.isEmptyBlock(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down()) {
				;
			}

			int i = this.getDistanceUntilEdge(p_i45694_2_, this.leftDir) - 1;

			if (i >= 0) {
				this.bottomLeft = p_i45694_2_.offset(this.leftDir, i);
				this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

				if (this.width < 2 || this.width > 21) {
					this.bottomLeft = null;
					this.width = 0;
				}
			}

			if (this.bottomLeft != null) {
				this.height = this.calculatePortalHeight();
			}
		}

		protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
			int i;

			for (i = 0; i < 22; ++i) {
				BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

				if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != CMContent.PORTAL_FRAME) {
					break;
				}
			}

			Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
			return block == CMContent.PORTAL_FRAME ? i : 0;
		}

		public int getHeight() {
			return this.height;
		}

		public int getWidth() {
			return this.width;
		}

		protected int calculatePortalHeight() {
			label24:

			for (this.height = 0; this.height < 21; ++this.height) {
				for (int i = 0; i < this.width; ++i) {

					BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
					Block block = this.world.getBlockState(blockpos).getBlock();

					if (!this.isEmptyBlock(block)) {
						break label24;
					}

					if (block == CMContent.PORTAL_COBALT) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

						if (block != CMContent.PORTAL_FRAME) {
							break label24;
						}
					} else if (i == this.width - 1) {
						block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

						if (block != CMContent.PORTAL_FRAME) {
							break label24;
						}
					}
				}
			}

			for (int j = 0; j < this.width; ++j) {
				if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != CMContent.PORTAL_FRAME) {
					this.height = 0;
					break;
				}
			}

			if (this.height <= 21 && this.height >= 3) {
				return this.height;
			} else {
				this.bottomLeft = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}

		@SuppressWarnings("deprecation")
		protected boolean isEmptyBlock(Block blockIn) {
			return blockIn.getMaterial(null) == Material.AIR || blockIn == CMContent.BLUE_FIRE || blockIn == CMContent.PORTAL_COBALT;
		}

		public boolean isValid() {
			return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}

		public void placePortalBlocks() {
			for (int i = 0; i < this.width; ++i) {
				BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

				for (int j = 0; j < this.height; ++j) {
					this.world.setBlockState(blockpos.up(j), CMContent.PORTAL_COBALT.getDefaultState().withProperty(BlockPortalCobalt.AXIS, this.axis), 2);
				}
			}
		}
	}
}
