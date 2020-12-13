package de.prwh.cobaltmod.core.items;

import java.util.ArrayList;
import java.util.List;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.fluids.CMFluids;
import de.prwh.cobaltmod.core.lib.CMLib;
import de.prwh.cobaltmod.core.lib.MetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.registries.IForgeRegistry;

public class CMItems {
	public static void init() {


		CMContent.COBALT_INGOT = addItem(new ItemCobaltIngot());
		CMContent.COBALT_SWORD = addItem(new ItemCobaltSword(CMContent.COBALT_TOOL));
		CMContent.COBALT_PICKAXE = addItem(new ItemCobaltPickAxe(CMContent.COBALT_TOOL));
		CMContent.COBALT_SHOVEL = addItem(new ItemCobaltShovel(CMContent.COBALT_TOOL));
		CMContent.COBALT_AXE = addItem(new ItemCobaltAxe(CMContent.COBALT_TOOL));
		CMContent.COBALT_HOE = addItem(new ItemCobaltHoe(CMContent.COBALT_TOOL));
		CMContent.COBEX_STICK = addItem(new ItemCobexStick());
		CMContent.COBALT_HELMET = addItem(new ItemArmor(CMContent.COBALT_ARMOR, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("cobalt_helmet").setRegistryName("cobalt_helmet"));
		CMContent.COBALT_CHESTPLATE = addItem(new ItemArmor(CMContent.COBALT_ARMOR, 1, EntityEquipmentSlot.CHEST).setUnlocalizedName("cobalt_chestplate").setRegistryName("cobalt_chestplate"));
		CMContent.COBALT_LEGGINGS = addItem(new ItemArmor(CMContent.COBALT_ARMOR, 2, EntityEquipmentSlot.LEGS).setUnlocalizedName("cobalt_leggings").setRegistryName("cobalt_leggings"));
		CMContent.COBALT_BOOTS = addItem(new ItemArmor(CMContent.COBALT_ARMOR, 3, EntityEquipmentSlot.FEET).setUnlocalizedName("cobalt_boots").setRegistryName("cobalt_boots"));
		CMContent.COBEX_BOW = addItem(new ItemCobexBow());
//		CMContent.COBEX_ARROW = 
		CMContent.RED_CABBAGE_SEEDS = addItem(
				new ItemRedCabbageSeeds(CMContent.RED_CABBAGE_CROP, CMContent.COBALT_FARMLAND));
		CMContent.RED_CABBAGE = addItem(new ItemRedCabbage(2, 2, false));
		CMContent.RED_CABBAGE_COOKED = addItem(new ItemRedCabbageCooked(3, 4, false));
		CMContent.COBEX_SWORD = addItem(new ItemCobexSword(CMContent.COBEX_TOOL));
		CMContent.COBEX_PICKAXE = addItem(new ItemCobexPickAxe(CMContent.COBEX_TOOL));
		CMContent.COBEX_SHOVEL = addItem(new ItemCobexShovel(CMContent.COBEX_TOOL));
		CMContent.COBEX_AXE = addItem(new ItemCobexAxe(CMContent.COBEX_TOOL));
		CMContent.COBEX_HOE = addItem(new ItemCobexHoe(CMContent.COBEX_TOOL));
		
		CMContent.BLUE_BERRY = addItem(new ItemBlueBerry(2, 2, false));
		CMContent.FIRE_SHARD = addItem(new ItemFireShard());
		CMContent.COBALT_NUGGET = addItem(new ItemCobaltNugget());
		
		//CMContent.COBALT_DOOR_I = addItem(new ItemDoor(CMContent.COBALT_DOOR).setUnlocalizedName("cobalt_door").setRegistryName("cobalt_door_i"));
		//CMContent.COBEX_DOOR_I = addItem(new ItemDoor(CMContent.COBEX_DOOR).setUnlocalizedName("cobex_door").setRegistryName("cobex_door_i"));
		
		CMContent.WATER_THORN_I = addItem(new ItemWaterThorn());
		
		//Stuff
		
		CMContent.WIND_AXE = addItem(new ItemWindAxe(CMContent.COBALT_TOOL));
		
	}

	private static <T extends Item> T addItem(T item) {

		item.setCreativeTab(CMMain.cobalttabitems);

		CMLib.register(item);
		addToItemList(new ItemData(item, 0));
		return item;
	}

	@SuppressWarnings("unused")
	private static <T extends Item> T addItem(T item, CreativeTabs tab) {
		item.setCreativeTab(tab);
		CMLib.register(item);
		addToItemList(new ItemData(item, 0));
		return item;
	}

	@SuppressWarnings("unused")
	private static <T extends MetaItem> T addMetaItem(T item, int meta) {
		item.setCreativeTab(CMMain.cobalttabitems);
		CMLib.register(item);
		CMLib.registerVariant(item, meta);
		addToItemList(new ItemData(item, meta));
		return item;
	}

	@SuppressWarnings("unused")
	private static Item addBucket(Item item) {
		item.setCreativeTab(CMMain.cobalttabitems);
		final int meta = CMFluids.FLUIDS.size();
		CMLib.register(item);

		// NLib.registerVariant(item, meta);

		addToItemList(new ItemData(item, meta));
		return item;
	}

	public static Item addBucketModel(Item item) {
		final int meta = CMFluids.FLUIDS.size();
		CMLib.registerVariant(item, meta);
		return item;
	}

	private static List<ItemData> items = new ArrayList<ItemData>();
	
	public static List<ItemData> getItemList() {
		return items;
	}
	
	public static void addToItemList(ItemData itemdata) {
		getItemList().add(itemdata);
	}



	public static class ItemData {
		public Item item = null;
		public int meta = 0;

		public ItemData(Item item, int meta) {
			this.meta = meta;
			this.item = item;
		}

		public ItemData(Item item) {
			this.item = item;
		}

		@Override
		public String toString() {
			return "item: " + this.item.getUnlocalizedName() + "; meta: " + this.meta;
		}
	}

	public static boolean contains(Item item, String word) {
		return item.getUnlocalizedName().contains(word);
	}
	
	public static void initTextures() {
		getItemList().forEach((ItemData item) -> {
			//CMMain.getLogger().info(item.toString());
			if (item.item instanceof MetaItem) {
				CMLib.registerInventoryMetaItem((MetaItem) item.item, item.meta);
			} else {
				CMLib.registerInventoryItem(item.item);
			}
		});
	}
	
	public static void registerItems(IForgeRegistry<Item> registry) {
		getItemList().forEach((ItemData item) -> {
			registry.registerAll(item.item);
		});
	}
}
