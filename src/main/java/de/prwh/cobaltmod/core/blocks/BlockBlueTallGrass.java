package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlueTallGrass extends BlockBush {
	
	protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
	
	public BlockBlueTallGrass() {
		super(Material.PLANTS);
		this.setTranslationKey("blue_tall_grass");
		this.setRegistryName("blue_tall_grass");
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}
	
	public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XYZ;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return TALL_GRASS_AABB;
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
	
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		state = worldIn.getBlockState(pos.down());
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return this.canSustainBush(soil);
    }
	@Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS;
    }
}