package de.prwh.cobaltmod.core.items;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemCobexAxe extends ItemTool {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF,
			Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK,
			Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });

	protected ItemCobexAxe(ToolMaterial material) {
		super(material, EFFECTIVE_ON);
		this.efficiency = material.getEfficiency();
		this.attackSpeed = -3.0F;
		
		this.setUnlocalizedName("cobex_axe");
		this.setRegistryName("cobex_axe");
	}

	protected ItemCobexAxe(Item.ToolMaterial material, float damage, float speed) {
		super(material, EFFECTIVE_ON);
		this.attackDamage = damage;
		this.attackSpeed = speed;
	}

	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();
		return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
				? super.getDestroySpeed(stack, state) : this.efficiency;
	}
}