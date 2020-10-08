package de.prwh.cobaltmod.core.lib;

import java.util.Arrays;
import java.util.Random;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.blocks.CMBlocks;
import de.prwh.cobaltmod.core.blocks.CMBlocks.BlockData;
import de.prwh.cobaltmod.core.items.CMItems;
import de.prwh.cobaltmod.core.items.CMItems.ItemData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//library with a lot of useful methods like registering block and items, their itemrender (also with metas) and drop
// Stacks
public class CMLib {

	static String MODID = CMMain.MODID;

	/**
	 * Drops an itemstack
	 *
	 * @param itemstack
	 *            the itemstack to be dropped
	 * @param world
	 *            the world
	 * @param x
	 *            coordinate (west<east)
	 * @param y
	 *            coordinate (up>down)
	 * @param z
	 *            coordinate (north<south)
	 * @param delay
	 *            delay, before the dropped item can be picked up
	 */
	public static void dropItemStack(ItemStack itemstack, World world, BlockPos pos, int delay) {
		EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
		entityitem.posX = pos.getX();
		entityitem.posY = pos.getY();
		entityitem.posZ = pos.getZ();
		entityitem.setPickupDelay(delay);
		if (itemstack.hasTagCompound()) {
			entityitem.getItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
		}
		world.spawnEntity(entityitem);
	}

	/**
	 * Drops an Itemstack with default pickup delay Args: itemstack, world,
	 * coords
	 *
	 * @param itemstack
	 *            the itemstack to be dropped
	 * @param world
	 *            the world
	 * @param x
	 *            coordinate (west<east)
	 * @param y
	 *            coordinate (up>down)
	 * @param z
	 *            coordinate (north<south)
	 */
	public static void dropItemStack(ItemStack itemstack, World world, BlockPos pos) {
		dropItemStack(itemstack, world, pos, 10);
	}

	/**
	 * Drops Itemstack in x/z radius of the coords Args: itemstack, world,
	 * coords
	 *
	 * @param itemstack
	 *            the itemstack to be dropped
	 * @param world
	 *            the world
	 * @param x
	 *            coordinate (west<east)
	 * @param y
	 *            coordinate (up>down)
	 * @param z
	 *            coordinate (north<south)
	 * @param radius
	 *            the radius, where the stack could be dropped
	 */
	public static void dropItemStackAround(ItemStack itemstack, World world, BlockPos pos, int radius) {
		BlockPos newpos = pos.add(getRandomIntegerInRange(new Random(), 0, radius), 0, getRandomIntegerInRange(new Random(), 0, radius));
		dropItemStack(itemstack, world, newpos, 10);
	}

	/**
	 * Drops Itemstack in x/z radius of the coords Args: itemstack, world,
	 * coords
	 *
	 * @param itemstack
	 *            the itemstack to be dropped
	 * @param world
	 *            the world
	 * @param x
	 *            coordinate (west<east)
	 * @param y
	 *            coordinate (up>down)
	 * @param z
	 *            coordinate (north<south)
	 * @param radius
	 *            the radius, where the stack could be dropped
	 * @param delay
	 *            delay, before the dropped item can be picked up by player
	 */
	public static void dropItemStackAround(ItemStack itemstack, World world, BlockPos pos, int radius, int delay) {
		BlockPos newpos = pos.add(getRandomIntegerInRange(new Random(), 0, radius), 0, getRandomIntegerInRange(new Random(), 0, radius));
		dropItemStack(itemstack, world, newpos, delay);
	}

	// -------------------------------------------------------------------- the
	// following methods are used by naemBlocks/naemItems, please use their
	// methods to add and register a Block/Item
	/**
	 * Registers an item with unlocalized name
	 */
	public static void register(Item item) {
		//GameRegistry.register(item); // registers Item using new Parents
	}

	/**
	 * Registers a block with unlocalized name
	 */
	public static void register(Block block) {
		//GameRegistry.register(block); // registers Block using new Parents
	}

	/**
	 * Registers a block including ItemBlock with unlocalized name an the
	 * specified amount of metadata
	 */
	public static <T extends Block> T registerWithItem(T block) {
		return registerWithItem(block, 0);
	}

	/**
	 * Registers a block including ItemBlock with unlocalized name an the
	 * specified amount of metadata
	 */
	public static <T extends Block> T registerWithItem(T block, int meta) {
		//GameRegistry.register(block); // register block
		Item item = null;
		if (meta > 1) {
			item = new ItemBlockMeta(block);
		} else {
			item = new ItemBlock(block);
		}
		item.setRegistryName(block.getRegistryName());
		CMItems.addToItemList(new ItemData(item, meta)); // register blockitem in name of block
		CMBlocks.addToBlockList(new BlockData(item, block, meta)); // write to init,
																// so it could
																// be loaded
																// later
		return block;
	}

	/**
	 * maps the textures for the block
	 * 
	 * @param block
	 *            the block
	 * @param meta
	 *            the number of metadata to be registered (0,1 = no meta)
	 * @param metacount
	 */
	@SideOnly(Side.CLIENT)
	public static void addTextures(Item block) {
		CMLib.registerInventoryItem(block);
	}

	/**
	 * maps the textures for the block
	 * 
	 * @param block
	 *            the block
	 * @param meta
	 *            the number of metadata to be registered (0,1 = no meta)
	 * @param metacount
	 */
	@SideOnly(Side.CLIENT)
	public static void addTextures(Item item, Block block, int meta) {
		CMLib.registerInventoryMetaItem(block, meta);
	}

	// ------------------------------------------------------------
	/**
	 * registers the Variant and itemModel for the block preinit!!
	 *
	 * @param block
	 *            theblock that gets registered (as his item)
	 * @param meta
	 *            the amount of meta of the block
	 * @param names
	 */
	@SideOnly(Side.CLIENT)
	public static void registerVariant(Block block, int meta) {
		for (int i = 0; i < meta; i++) {
			ItemStack it = new ItemStack(block, 1, i);
			String name = it.getUnlocalizedName().substring(5);
			ModelBakery.registerItemVariants(Item.getItemFromBlock(block), new ResourceLocation(MODID + ":" + name));
		}
	}

	/**
	 * registers the Variant and itemModel for the item preinit!!
	 *
	 * @param item
	 *            the item that gets registered
	 * @param meta
	 *            the amount of meta of the item
	 */
	@SideOnly(Side.CLIENT)
	public static void registerVariant(Item item, int meta) {
		for (int i = 0; i < meta; i++) {
			ItemStack it = new ItemStack(item, 1, i);
			String name = it.getUnlocalizedName().substring(5);
			ModelBakery.registerItemVariants(item, new ResourceLocation(MODID + ":" + name));
		}
	}

	/**
	 * registering the inventory renderer for a block with metadata must be done
	 * in init
	 *
	 * @param block
	 *            the block with metadata
	 * @param meta
	 *            the amount of meta for this block
	 */
	public static void registerInventoryMetaItem(Block block, int meta) {
		for (int i = 0; i < meta; i++) {
			ItemStack it = new ItemStack(block, 1, i);
			String name = it.getUnlocalizedName().substring(5);
			registerInventoryItem(Item.getItemFromBlock(block), new ResourceLocation(MODID, name), i);
		}
	}

	/**
	 * registering the inventory renderer for a item with metadata must be done
	 * in init
	 *
	 * @param item
	 *            the item with metadata
	 * @param meta
	 *            the amount of meta for this block
	 */
	public static void registerInventoryMetaItem(MetaItem item, int meta) {
		for (int i = 0; i < meta; i++) {
			ItemStack it = new ItemStack(item, 1, i);
			String name = it.getUnlocalizedName().substring(5);
			registerInventoryItem(item, new ResourceLocation(MODID, name), i);
		}
	}

	// --------------------------------------
	/**
	 * registers the inventory model using the given block meta = 0; modelname =
	 * unlocalized name of block must be used in init
	 *
	 * @param block
	 *            the block using the model
	 */
	public static void registerInventoryItem(Block block) {
		registerInventoryItem(block, block.getRegistryName(), 0);
	}

	/**
	 * registers the inventory model using the given item meta = 0; modelname =
	 * unlocalized name of item must be used in init
	 *
	 * @param item
	 *            the item using the model
	 */
	public static void registerInventoryItem(Item item) {
		registerInventoryItem(item, item.getRegistryName(), 0);
	}

	/**
	 * registers the inventory model using the given block modelname =
	 * unlocalized name of item must be used in init
	 *
	 * @param block
	 *            the block using the model
	 * @param meta
	 *            the meta-number of this block
	 */
	public static void registerInventoryItem(Block block, int meta) {
		registerInventoryItem(block, block.getRegistryName(), meta);
	}

	/**
	 * registers the inventory model using the given item modelname =
	 * unlocalized name of item must be used in init
	 *
	 * @param item
	 *            the item using the model
	 * @param meta
	 *            the meta-number of this item
	 */
	public static void registerInventoryItem(Item item, int meta) {
		registerInventoryItem(item, item.getRegistryName(), meta);
	}

	/**
	 * registers the inventory model using the given block meta = 0 must be used
	 * in init
	 *
	 * @param block
	 *            the block using the model
	 * @param resourceLocation
	 *            the model file name (the json item-model)
	 */
	public static void registerInventoryItem(Block block, ResourceLocation resourceLocation) {
		registerInventoryItem(block, resourceLocation, 0);
	}

	/**
	 * registers the inventory model using the given item meta = 0 must be used
	 * in init
	 *
	 * @param item
	 *            the block using the model
	 * @param resourceLocation
	 *            the model file name (the json item-model)
	 */
	public static void registerInventoryItem(Item item, ResourceLocation resourceLocation) {
		registerInventoryItem(item, resourceLocation, 0);
	}

	/**
	 * registers the inventory model using the given block must be used in init
	 *
	 * @param block
	 *            the block using the model
	 * @param resourceLocation
	 *            the model file name (the json item-model)
	 * @param meta
	 *            the meta of the block
	 */
	public static void registerInventoryItem(Block block, ResourceLocation resourceLocation, int meta) {
		registerInventoryItem(Item.getItemFromBlock(block), resourceLocation, meta);
	}

	/**
	 * registers the inventory model using the given item must be used in init
	 *
	 * @param item
	 *            the item using the model
	 * @param modelname
	 *            the model file name (the json item-model)
	 * @param meta
	 *            the meta of the item
	 */
	@SideOnly(Side.CLIENT)
	public static void registerInventoryItem(Item item, ResourceLocation resourceLocation, int meta) {
		//CMMain.getLogger().info(item.getUnlocalizedName() + " " + resourceLocation + " " + meta);
		
		ModelResourceLocation model = new ModelResourceLocation(resourceLocation, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model);
	}

	// ---------------------------
	/**
	 * Checks if key is element of array
	 */
	public static <T> boolean contains(final T[] array, final T key) {
		return Arrays.asList(array).contains(key);
	}

	public static int getRandomIntegerInRange(Random rand, int a, int b) {
		return a >= b ? a : rand.nextInt(b - a + 1) + a;
	}
}