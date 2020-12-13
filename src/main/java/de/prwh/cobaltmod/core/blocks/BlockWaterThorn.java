package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWaterThorn extends BlockLilyPad {

	public BlockWaterThorn() {
		this.setUnlocalizedName("waterthorn");
		this.setRegistryName("waterthorn");
	}
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(CMContent.WATER_THORN_I);
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CMContent.WATER_THORN_I;
	}
	
	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        entityIn.attackEntityFrom(DamageSource.MAGIC, 1F);
    }
	
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.WATER || state.getMaterial() == Material.ICE;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());
            Material material = iblockstate.getMaterial();
            return material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0 || material == Material.ICE;
        }
        else
        {
            return false;
        }
    }
}
