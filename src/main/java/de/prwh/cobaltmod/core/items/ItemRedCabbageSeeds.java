package de.prwh.cobaltmod.core.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemRedCabbageSeeds extends Item implements IPlantable {
	private final Block crops;
	/** BlockID of the block the seeds can be planted on. */
	@SuppressWarnings("unused")
	private final Block soilBlockID;

	public ItemRedCabbageSeeds(Block crops, Block soil) {
		this.crops = crops;
		this.soilBlockID = soil;
		this.setUnlocalizedName("red_cabbage_seeds");
		this.setRegistryName("red_cabbage_seeds");
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
		if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack)
				&& state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this)
				&& worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), this.crops.getDefaultState());
			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return this.crops == Blocks.NETHER_WART ? EnumPlantType.Nether : EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.crops.getDefaultState();
	}
	
	
}