package de.prwh.cobaltmod.core.blocks;

import java.util.Random;

import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.api.CMReplace;
import de.prwh.cobaltmod.core.client.CobaltAuraParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCobaltGrass extends Block {

	public BlockCobaltGrass() {
		super(Material.GRASS);
		this.setTranslationKey("cobalt_grass");
		this.setRegistryName("cobalt_grass");
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		this.setHardness(0.6F);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CMContent.COBALT_DIRT.getItemDropped(CMContent.COBALT_DIRT.getDefaultState(), rand, fortune);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(10) == 0) {

			double d0 = (double) ((float) pos.getX() + rand.nextFloat());
			double d1 = (double) ((float) pos.getY() + +1.1F);
			double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;

			FMLClientHandler.instance().getClient().effectRenderer.addEffect(
					new CobaltAuraParticle(FMLClientHandler.instance().getWorldClient(), d0, d1, d2, d3, d4, d5));
		}
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (!worldIn.isRemote) {
			if (worldIn.getLightFromNeighbors(pos.up()) < 4
					&& worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
				worldIn.setBlockState(pos, CMContent.COBALT_DIRT.getDefaultState());
			} else {
				if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
					for (int i = 0; i < 5; ++i) {
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
							return;
						}

						IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
						// IBlockState block_down =
						// worldIn.getBlockState(blockpos.down());
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

						for (int j = 0; j < 3; j++) {
							BlockPos downwards = blockpos.add(0, -j, 0);
							if (worldIn.getBlockState(downwards).getBlock() == Blocks.DIRT) {
								worldIn.setBlockState(downwards, CMContent.COBALT_DIRT.getDefaultState());

							}
						}

						for (int v = 0; v < CMReplace.getSpread().size(); v++) {

							// System.out.println(iblockstate1.getBlock());
							// System.out.println(CMReplace.getMap().containsKey(iblockstate1.getBlock()));

							if (iblockstate1.getBlock() == Blocks.LOG || iblockstate1.getBlock() == Blocks.LOG2
									|| iblockstate1.getBlock() == Blocks.LEAVES
									|| iblockstate1.getBlock() == Blocks.LEAVES2) {

								if (CMReplace.getSpread().containsKey(iblockstate1.getBlock())) {
									worldIn.setBlockState(blockpos,
											CMReplace.getSpread().get(iblockstate1.getBlock()).getDefaultState());
								}

							} else if (iblockstate1.getBlock() instanceof BlockFlower) {

								Block flower = CMReplace.getFlowers().get(rand.nextInt(CMReplace.getFlowers().size()));

								worldIn.setBlockState(blockpos, flower.getDefaultState());
							} else if (CMReplace.getSpread().containsKey(iblockstate1.getBlock())
									&& worldIn.getLightFromNeighbors(blockpos.up()) >= 4
									&& iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {

								if (iblockstate.getBlock() instanceof BlockFlower
										|| iblockstate.getBlock() instanceof BlockDoublePlant) {
									Block flower = CMReplace.getFlowers()
											.get(rand.nextInt(CMReplace.getFlowers().size()));

									worldIn.setBlockState(blockpos.add(0, 1, 0), flower.getDefaultState());
								}

								worldIn.setBlockState(blockpos,
										CMReplace.getSpread().get(iblockstate1.getBlock()).getDefaultState());

							}
						}
					}
				}
			}
		}
	}

	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {

		super.onEntityWalk(worldIn, pos, entityIn);

		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			NonNullList<ItemStack> armor = player.inventory.armorInventory;
			if (armor.get(0) != null) {
				if (armor.get(0).getItem() == CMContent.COBALT_BOOTS) {
					return;
				}
			}
		}
		if (entityIn instanceof EntityLivingBase) {
			// EntityLivingBase elb = (EntityLivingBase) entityIn;

			// if (elb.isPotionActive(CMContent.potion_cobalt_resistance)) {
			// return;
			// }
		}
		// if (entityIn instanceof EntityCobaltZombie) {
		// return;
		// }
		// if (entityIn instanceof EntityCobaltGuardian) {
		// return;
		// }
		entityIn.attackEntityFrom(DamageSource.MAGIC, 4F);
	}
}
