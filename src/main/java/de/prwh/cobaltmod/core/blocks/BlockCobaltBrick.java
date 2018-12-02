package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCobaltBrick extends Block {

	public BlockCobaltBrick() {
		super(Material.ROCK);
		this.setTranslationKey("cobalt_brick");
		this.setRegistryName("cobalt_brick");
		this.setHardness(5F);
		this.setResistance(2000F);
		this.setSoundType(SoundType.STONE);
		this.setTickRandomly(true);
	}

	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			//EntityPlayer entityplayer = (EntityPlayer) entityIn;
			// entityplayer.addStat(AchievementHandler.cobaltachiev9, 1);
		}
		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@SuppressWarnings("deprecation")
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			for (int l = 0; l < 40; ++l) {

				int x = pos.getX() + rand.nextInt(3) - 1;
				int y = pos.getY() + rand.nextInt(5) - 3;
				int z = pos.getZ() + rand.nextInt(3) - 1;

				BlockPos posB = new BlockPos(x, y, z);
				IBlockState block = worldIn.getBlockState(posB);

				if (block == Blocks.GRASS && block.getLightValue() >= 4 && block.getLightOpacity() <= 2) {
					// TODO FIXME!
					// worldIn.setBlockState(posB,
					// CMContent.COBALTGRASS.getDefaultState());
				}
			}
		}
	}
}