package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGlowFlower extends BlockBush {
	
	public BlockGlowFlower() {
		super(Material.PLANTS);
		this.setUnlocalizedName("glow_flower");
		this.setRegistryName("glow_flower");
		this.setLightLevel(0.7F);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}
	
	public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XYZ;
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