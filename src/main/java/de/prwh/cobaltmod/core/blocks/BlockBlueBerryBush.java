package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlueBerryBush extends BlockCrops {

	public BlockBlueBerryBush() {
		this.setUnlocalizedName("blueberry_bush");
		this.setRegistryName("blueberry_bush");
		this.setHardness(0.2F);
	}

	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

	protected Item getSeed() {
		return null;
	}

	protected Item getCrop() {
		return null;
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!worldIn.isRemote && !player.isCreative()) {
			if (this.isMaxAge(state)) {
				spawnAsEntity(worldIn, pos, new ItemStack(CMContent.BLUE_BERRY, 1 + worldIn.rand.nextInt(2)));
				spawnAsEntity(worldIn, pos, new ItemStack(CMContent.BLUEBERRY_BUSH, 1));
			} else {
				spawnAsEntity(worldIn, pos, new ItemStack(CMContent.BLUEBERRY_BUSH, 1));
			}
		}
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return canSustainBush(worldIn.getBlockState(pos.down()));
	}

	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this.isMaxAge(state) && !worldIn.isRemote) {

			spawnAsEntity(worldIn, pos, new ItemStack(CMContent.BLUE_BERRY, 1 + worldIn.rand.nextInt(2)));
			int randState = worldIn.rand.nextInt(3);
			worldIn.setBlockState(pos, this.withAge(this.getMaxAge() - 1 - randState), 2);
			return true;
		}
		return false;
	}
}
