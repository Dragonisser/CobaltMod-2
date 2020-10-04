package de.prwh.cobaltmod.core.items;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWindAxe extends ItemTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF,
			Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK,
			Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });

	private boolean isinair = false;

	protected ItemWindAxe(ToolMaterial material) {
		super(material, EFFECTIVE_ON);
		this.efficiency = material.getEfficiency();
		this.attackSpeed = -3.0F;

		this.setUnlocalizedName("wind_axe");
		this.setRegistryName("wind_axe");

		this.setMaxDamage(200);
		this.isDamageable();
	}

	protected ItemWindAxe(Item.ToolMaterial material, float damage, float speed) {
		super(material, EFFECTIVE_ON);
		this.attackDamage = damage;
		this.attackSpeed = speed;
	}

	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();
		return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
				? super.getDestroySpeed(stack, state) : this.efficiency;
	}

	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.EPIC;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par1ItemStack.getItemDamage() < par1ItemStack.getMaxDamage()) {
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
			// System.out.println(par1ItemStack.getItemDamage());
		}
		
		

		if (par5 && (par3Entity instanceof EntityPlayer)) {
			EntityPlayer entityplayer = (EntityPlayer) par3Entity;
			if(entityplayer.capabilities.isFlying) {
				this.isinair = false;
			}
			
			if (!par2World.isRemote && !entityplayer.capabilities.isCreativeMode) {
				if (entityplayer.getHeldItemMainhand().getItem() == CMContent.WIND_AXE) {
					if (par1ItemStack.getItemDamage() > 0) {
						entityplayer.fallDistance = 0.0F;
					}
				}
			}
		}

		if (par1ItemStack.getItemDamage() < par1ItemStack.getMaxDamage()) {
			if (par3Entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) par3Entity;
				if (this.isinair) {
					for (int cp = 0; cp <= 10; cp++) {
						double d0 = itemRand.nextGaussian() * 0.02D;
						double d1 = itemRand.nextGaussian() * 0.02D;
						double d2 = itemRand.nextGaussian() * 0.02D;

						double dx = entityplayer.posX;
						double dy = entityplayer.posY - 1.8;
						double dz = entityplayer.posZ;

						// par2World.spawnParticle("cloud", dx, dy, dz, d0, d1,
						// d2);
						par2World.spawnParticle(EnumParticleTypes.CLOUD, dx, dy, dz, d0, d1, d2, 0);
					}
				}

			}
		}

		if (par3Entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) par3Entity;

			int x = (int) entityplayer.posX;
			int y = (int) (entityplayer.posY - entityplayer.getYOffset());
			int z = (int) entityplayer.posZ;

			if (par1ItemStack.getItemDamage() <= 190) {
				if (par2World.getBlockState(new BlockPos(x, y - 1, z)).getBlock() != Blocks.AIR) {
					this.isinair = false;
				}
			}
		}
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		// if (par2EntityLivingBase instanceof EntityCobaltGuardian) {
		// return false;
		// } else {
		float forwardspeed = 0.5F;
		float upwardspeed = 0.5F;

		double x = -1 * Math.sin(par3EntityLivingBase.rotationYaw * Math.PI / 180) * forwardspeed;
		double z = Math.cos(par3EntityLivingBase.rotationYaw * Math.PI / 180) * forwardspeed;
		double y = upwardspeed;

		par2EntityLivingBase.addVelocity(x, y, z);
		// }
		return false;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItemDamage() == 0) {
			double x = -1 * Math.sin(playerIn.rotationYaw * Math.PI / 180) * (float) CMMain.FORWARD_SPEED;
			double z = Math.cos(playerIn.rotationYaw * Math.PI / 180) * (float) CMMain.FORWARD_SPEED;
			double y = (float) CMMain.UPWARD_SPEED;

			playerIn.addVelocity(x, y, z);

			playerIn.getHeldItem(EnumHand.MAIN_HAND).damageItem(199, playerIn);

			this.isinair = true;

		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list,
			boolean i) {
		// list.add(StatCollector.translateToLocal("tooltip.windaxe.first_line"));
		// list.add("");
		// list.add(StatCollector.translateToLocal("tooltip.windaxe.second_line"));
		// list.add("");
		// list.add(StatCollector.translateToLocal("tooltip.windaxe.third_line"));
	}

}
