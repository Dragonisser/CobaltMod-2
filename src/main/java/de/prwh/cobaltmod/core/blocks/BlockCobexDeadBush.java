package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCobexDeadBush extends BlockBush {

	public BlockCobexDeadBush() {
		super(Material.PLANTS);
		this.setUnlocalizedName("cobex_deadbush");
		this.setRegistryName("cobex_deadbush");
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}

	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		state = worldIn.getBlockState(pos.down());
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS || state.getBlock() == CMContent.CORRUPTED_STONE
				|| state.getBlock() == CMContent.HARDENED_CORRUPTED_STONE;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return this.canSustainBush(soil);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS || state.getBlock() == CMContent.CORRUPTED_STONE
				|| state.getBlock() == CMContent.HARDENED_CORRUPTED_STONE;
	}
}