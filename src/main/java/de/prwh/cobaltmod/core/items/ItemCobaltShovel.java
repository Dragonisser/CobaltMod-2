package de.prwh.cobaltmod.core.items;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemCobaltShovel extends ItemSpade {

	public ItemCobaltShovel(ToolMaterial material) {
		super(material);
		this.setUnlocalizedName("cobalt_shovel");
		this.setRegistryName("cobalt_shovel");
	}
	
	public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        return material != Material.GROUND && material != Material.GRASS ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}
