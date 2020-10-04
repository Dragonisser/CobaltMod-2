package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.entity.tileentity.TileEntityCobexChest;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCobexChest extends BlockChest {

	protected BlockCobexChest(Type chestTypeIn) {
		super(chestTypeIn);
		this.setUnlocalizedName("cobex_chest");
		this.setRegistryName("cobex_chest");
	}
	
	
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCobexChest();
    }
}
