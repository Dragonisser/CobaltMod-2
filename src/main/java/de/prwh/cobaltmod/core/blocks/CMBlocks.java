package de.prwh.cobaltmod.core.blocks;

import java.util.ArrayList;
import java.util.List;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.blocks.slabs.BlockCobaltDoubleSlab;
import de.prwh.cobaltmod.core.blocks.slabs.BlockCobaltHalfSlab;
import de.prwh.cobaltmod.core.lib.CMLib;
import de.prwh.cobaltmod.core.lib.ItemBlockMeta;
import de.prwh.cobaltmod.core.lib.MetaBlock;
import de.prwh.cobaltmod.core.lib.MetaBlockPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class CMBlocks {

	// private static int number = 0;

	public static void init() {

		CMContent.COBALT_BRICK = addBlock(new BlockCobaltBrick());
		CMContent.COBALT_BRICK_STAIR = addBlock(new BlockCobaltBrickStair(CMContent.COBALT_BRICK.getDefaultState()));
		CMContent.CORRUPTED_STONE = addBlock(new BlockCorruptedStone());
		CMContent.COBALT_DIRT = addBlock(new BlockCobaltDirt());
		CMContent.COBALT_GRASS = addBlock(new BlockCobaltGrass());
		CMContent.COBALT_FARMLAND = addBlock(new BlockCobaltFarmLand(), false);
		CMContent.COBALT_STONE = addBlock(new BlockCobaltStone());
		CMContent.COBALT_ORE = addBlock(new BlockCobaltOre());
		CMContent.COBALT_BLOCK = addBlock(new BlockCobaltBlock());
		CMContent.COBEX_LOG = addBlock(new BlockCobexLog());
		CMContent.COBEX_WOOD = addBlock(new BlockCobexWood());
		CMContent.COBEX_LEAVES = addBlock(new BlockCobexLeaves());
		CMContent.COBEX_SAPLING = addBlock(new BlockCobexSapling());
		CMContent.COBALT_HALF_SLAB = (BlockSlab) new BlockCobaltHalfSlab().setUnlocalizedName("half_slab_cobalt").setRegistryName("half_slab_cobalt");
		CMContent.COBALT_DOUBLE_SLAB = (BlockSlab) new BlockCobaltDoubleSlab().setUnlocalizedName("double_slab_cobalt").setRegistryName("double_slab_cobalt");
		CMContent.CLEMATIS_FLOWER = addBlock(new BlockFlowerClematis());
		CMContent.RED_CABBAGE_CROP = addBlock(new BlockRedCabbageCrop(), false);
		CMContent.BLUE_TALL_GRASS = addBlock(new BlockBlueTallGrass(), false);
		CMContent.BELL_FLOWER = addBlock(new BlockBellFlower());
		CMContent.GLOW_FLOWER = addBlock(new BlockGlowFlower());
		CMContent.COBEX_TORCH = addBlock(new BlockCobexTorch());

		CMContent.PORTAL_COBALT = addBlock(new BlockPortalCobalt(), false);
		CMContent.PORTAL_FRAME = addBlock(new BlockPortalFrame());
		CMContent.BLUE_FIRE = addBlock(new BlockBlueFire(), false);

		CMContent.BLUE_VINE = addBlock(new BlockBlueVine());
		CMContent.BIG_COBEX_LEAVES = addBlock(new BlockBigCobexLeaves());
		CMContent.BIG_COBEX_SAPLING = addBlock(new BlockBigCobexSapling());

		CMContent.BLUEBERRY_BUSH = addBlock(new BlockBlueBerryBush());

		register();
		setFireInfo();
		oredictregister();

	}

	public static void setFireInfo() {
		Blocks.FIRE.setFireInfo(CMContent.COBEX_LOG, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.COBEX_WOOD, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.BIG_COBEX_LEAVES, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.COBEX_LEAVES, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.COBEX_SAPLING, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.BIG_COBEX_SAPLING, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.BLUE_VINE, 60, 60);
		Blocks.FIRE.setFireInfo(CMContent.BLUE_TALL_GRASS, 60, 60);
	}

	public static void oredictregister() {
		OreDictionary.registerOre("oreCobalt", CMContent.COBALT_ORE);
		// OreDictionary.registerOre("oreCarthun", CMContent.carthunore);
		OreDictionary.registerOre("treeCobex", CMContent.COBEX_LOG);
		OreDictionary.registerOre("plankCobex", CMContent.COBEX_WOOD);
	}

	public static class BlockData {
		public Block block = null;
		public Item item;
		public int meta = 0;

		public BlockData(Item item, Block block, int meta) {
			this.block = block;
			this.meta = meta;
			this.item = item;
		}

		public BlockData(Item item) {
			this.item = item;
		}

		@Override
		public String toString() {
			return "block:" + this.block.getUnlocalizedName() + "; meta:" + this.meta;
		}
	}

	public static void register() {

		BlockSlab hslab = CMContent.COBALT_HALF_SLAB;
		BlockSlab dSlab = CMContent.COBALT_DOUBLE_SLAB;

		registerWithItemSlab(hslab, hslab, dSlab, hslab.getRegistryName(), true);
		registerWithItemSlab(dSlab, hslab, dSlab, dSlab.getRegistryName(), false);

		// leaves
		// NLib.register(NanpaContent.leaves_jungle);
		// Item item_leaves = new
		// ItemBlockLeaves(NanpaContent.leaves_jungle).setRegistryName(NanpaContent.leaves_jungle.getRegistryName());
		// NLib.register(item_leaves);
		// blocks.add(new BlockData(item_leaves, NanpaContent.leaves_jungle,
		// BlockPlanksJungle.EnumType.values().length));
		// //leavestwo
		// NLib.register(NanpaContent.leaves_jungle_two);
		// Item item_leaves_two = new
		// ItemBlockLeavesTwo(NanpaContent.leaves_jungle_two).setRegistryName(NanpaContent.leaves_jungle_two.getRegistryName());
		// NLib.register(item_leaves_two);
		// blocks.add(new BlockData(item_leaves_two,
		// NanpaContent.leaves_jungle_two,
		// BlockPlanksJungleTwo.EnumType.values().length));
		// //logs
		// NLib.register(NanpaContent.log_jungle);
		// Item item_log = new
		// ItemBlockLog(NanpaContent.log_jungle).setRegistryName(NanpaContent.log_jungle.getRegistryName());
		// NLib.register(item_log);
		// blocks.add(new BlockData(item_log, NanpaContent.log_jungle,
		// BlockPlanksJungle.EnumType.values().length));
		// //logstwo
		// NLib.register(NanpaContent.log_jungle_two);
		// Item item_log_two = new
		// ItemBlockLogTwo(NanpaContent.log_jungle_two).setRegistryName(NanpaContent.log_jungle_two.getRegistryName());
		// NLib.register(item_log_two);
		// blocks.add(new BlockData(item_log_two, NanpaContent.log_jungle_two,
		// BlockPlanksJungleTwo.EnumType.values().length));

		// NLib.register(NanpaContent.block_portal);
		// Item item_portal = new
		// ItemBlock(NanpaContent.block_portal).setRegistryName(NanpaContent.block_portal.getRegistryName());
		// NLib.register(item_portal);
		// blocks.add(new BlockData(item_portal, NanpaContent.block_portal,
		// BlockPlanksJungle.EnumType.values().length));
		// NanpaContent.block_portal.setCreativeTab(NanpaMain.nanpatabblocks);
	}

	private static <T extends Block> T addBlock(T block) {
		return addBlock(block, CMMain.cobalttabblocks, true);
	}

	private static <T extends Block> T addBlock(T block, boolean addToCreative) {
		return addBlock(block, CMMain.cobalttabblocks, addToCreative);
	}

	private static <T extends Block> T addBlock(T block, CreativeTabs tab, boolean addToCreative) {
		CMLib.registerWithItem(block);
		CMMain.getLogger().info(block + " " + block.getUnlocalizedName().substring(5));
		if (addToCreative)
			block.setCreativeTab(tab);
		return block;
	}

	@SuppressWarnings("unused")
	private static <T extends MetaBlock> T addMetaBlock(T block, int meta) {
		return addMetaBlock(block, new ItemBlockMeta(block), meta);
	}

	// block with blockstates
	private static <T extends MetaBlock> T addMetaBlock(T block, ItemBlock item, int meta) {
		return CMLib.registerWithItem(block, meta);
	}

	@SuppressWarnings("unused")
	private static <T extends MetaBlockPlant> T addMetaBlockPlant(T block, int meta) {
		return addMetaBlockPlant(block, new ItemBlockMeta(block), meta);
	}

	// block with blockstates
	private static <T extends MetaBlockPlant> T addMetaBlockPlant(T block, ItemBlock item, int meta) {
		return CMLib.registerWithItem(block, meta);
	}

	public static Block registerWithItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab, ResourceLocation name, boolean creativeTab) {
		GameRegistry.register(block);

		Item item = new ItemSlab(block, singleSlab, doubleSlab).setRegistryName(name);
		GameRegistry.register(item);
		// CMLib.registerInventoryItem(singleSlab);
		if (creativeTab)
			block.setCreativeTab(CMMain.cobalttabblocks);
		CMMain.getLogger().info(block + " " + block.getUnlocalizedName().substring(5));
		CMBlocks.blocks.add(new BlockData(item, block, 0));
		return block;
	}

	public static List<BlockData> blocks = new ArrayList<BlockData>();

	@SideOnly(Side.CLIENT)
	public static void initTextures() {
		// some magic to not want textures for those states
		// ModelLoader.setCustomStateMapper(NanpaContent.leaves_jungle, new
		// StateMap.Builder().ignore(BlockLeavesJungle.CHECK_DECAY).ignore(BlockLeavesJungle.DECAYABLE).build());
		// ModelLoader.setCustomStateMapper(NanpaContent.leaves_jungle_two, new
		// StateMap.Builder().ignore(BlockLeavesJungle.CHECK_DECAY).ignore(BlockLeavesJungle.DECAYABLE).build());

		blocks.forEach((BlockData block) -> {
			if (block.meta <= 1) {
				CMLib.addTextures(block.item);
			} else {
				CMLib.registerVariant(block.item, block.meta);
				CMLib.addTextures(block.item, block.block, block.meta);
			}
		});

		blocks.clear(); // not longer needed
	}
}
