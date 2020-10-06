package de.prwh.cobaltmod.core.blocks;

import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.lib.MetaBlockPlant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBluishMushroom extends MetaBlockPlant {
	
	public static int metas = PlantVariations.values().length;

	public BlockBluishMushroom(String name) {
		super(Material.PLANTS, name, metas);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		this.setDefaultState(this.getStateFromMeta(PlantVariations.SMALL.meta));
	}
	
	public static final PropertyEnum<PlantVariations> VARIATIONS = PropertyEnum.create("variations", PlantVariations.class);

	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		state = worldIn.getBlockState(pos.down());
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS || state.getBlock() == CMContent.CORRUPTED_STONE
				|| state.getBlock() == CMContent.HARDENED_CORRUPTED_STONE;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return this.canSustainBush(soil);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == CMContent.COBALT_DIRT || state.getBlock() == CMContent.COBALT_GRASS || state.getBlock() == CMContent.CORRUPTED_STONE
				|| state.getBlock() == CMContent.HARDENED_CORRUPTED_STONE;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((PlantVariations) state.getValue(VARIATIONS)).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIATIONS, PlantVariations.getTypeByMeta(meta));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return PlantVariations.getNameByMeta(stack.getItemDamage());
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIATIONS});
    }
	
	
	public enum PlantVariations implements IStringSerializable {
		
		SMALL(0, "small"),
		BIG(1, "big")
		;
		
		private int meta;
		private final String unlocalizedName;
		
		private PlantVariations(int meta, String unlocName) {
			this.meta = meta;
			this.unlocalizedName = unlocName;
		}
		
		int getMeta() {
			return meta;
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}

		public static PlantVariations getTypeByMeta(int meta) {
			if (meta < 0 || meta >= PlantVariations.values().length) {
				meta = 0;
			}
			return PlantVariations.values()[meta];
		}
		
		public static String getNameByMeta(int meta)
		{
			if (meta < 0 || meta >= PlantVariations.values().length) {
				meta = 0;
			}			
			return PlantVariations.values()[meta].getUnlocalizedName();
		}

		@Override
		public String getName() {
			return this.getUnlocalizedName();
		}

		@Override
		public String toString()
		{
	        return this.unlocalizedName;
	    }

		
	}
}