package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCobaltStone extends Block {

	public BlockCobaltStone() {
		super(Material.ROCK);
		this.setUnlocalizedName("cobalt_stone");
		this.setRegistryName("cobalt_stone");
		this.setHardness(2.0F);
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
