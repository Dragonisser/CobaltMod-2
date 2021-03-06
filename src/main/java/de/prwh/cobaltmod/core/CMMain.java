package de.prwh.cobaltmod.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.api.CMReplace;
import de.prwh.cobaltmod.core.blocks.CMBlocks;
import de.prwh.cobaltmod.core.items.CMItems;
import de.prwh.cobaltmod.handler.event.CMLivingUpdateEventHandler;
import de.prwh.cobaltmod.handler.event.CMRainEventHandler;
import de.prwh.cobaltmod.world.biome.CMBiomeGenBase;
import de.prwh.cobaltmod.world.dim.cobaldis.WorldProviderCobaldis;
import de.prwh.cobaltmod.world.dim.deep_caves.WorldProviderDeepCaves;
import de.prwh.cobaltmod.world.gen.WorldGeneratorDim;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

//Seed 4650873961059636830

@Mod(modid = CMMain.MODID, name = "Cobalt Mod", version = CMMain.VERSION, dependencies = "after:biomesoplenty")
public class CMMain {

	// CreativeTab
	public static CreativeTabs cobalttabitems = new CreativeTabCobalt(CreativeTabs.getNextID(), "cobalttabitems", 1);
	public static CreativeTabs cobalttabblocks = new CreativeTabCobalt(CreativeTabs.getNextID(), "cobalttabblocks", 2);

	// Entity
	static int startEntityId = 0;

	// Armormaterial
	// public static ArmorMaterial BackpackArmor;
	// public static ArmorMaterial CobaltBackpackArmor;

	// Dimension
	//public static final WorldType COBALT_WORLD_TYPE = new CMWorldType();
	public static DimensionType type_cobaldis = null;
	public static DimensionType type_deep_caves = null;
	public static int id_cobaldis;
	public static int id_deep_caves;
	public static double portaltemple;

	// WindAxe
	public static int FORWARD_SPEED;
	public static int UPWARD_SPEED;

	// SpeedBoots
	// public static double forwardspeedboots;

	// SkyRender
	// @SideOnly(Side.CLIENT)
	// public static IRenderHandler skyRenderer;

	// Dev
	// public static boolean devenabled;
	// public static boolean templeenabled;

	@SidedProxy(clientSide = "de.prwh.cobaltmod.core.ClientProxyCobalt", serverSide = "de.prwh.cobaltmod.core.ServerProxyCobalt")
	public static ServerProxyCobalt proxy;
	public static final String MODID = "mod_cobalt";
	public static final String VERSION = "2.0.4";

	private static final Logger log = LogManager.getLogger(MODID.toUpperCase());

	@Instance("mod_cobalt")
	public static CMMain instance;
	// public static DamageSource causeCobaltArrowDamage(EntityCobexArrow
	// par0EntityArrow, Entity par1Entity) {
	// return (new EntityDamageSourceIndirect("cobexarrow", par0EntityArrow,
	// par1Entity)).setProjectile();
	// }

	// public static DamageSource causeLifeStealDamage(EntityLifeStealBall
	// par0EntityArrow, Entity par1Entity) {
	// return (new EntityDamageSourceIndirect("lifesteal", par0EntityArrow,
	// par1Entity)).setProjectile();
	// }

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {

		// Config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();

		config.get("Dimension", "Cobalt", 20).setComment("Which ID the Dimension has. Change it if you have problems with other Mods.");
		id_cobaldis = config.get("Dimension", "Cobalt", 20).getInt();
		id_deep_caves = config.get("Dimension", "Deep Caves", 21).getInt();
		//
		// portaltemple = config.get("PortalTemple", "Spawnrate",
		// 25).getDouble();
		// config.get("PortalTemple", "Spawnrate", 25).comment = "The Spawnrate
		// of the CobaltTemple defined in percent. 100% means always when it
		// cans, 0% means never.";
		//
		FORWARD_SPEED = config.get("WindAxe", "ForWardSpeed", 1).getInt();
		config.get("WindAxe", "ForWardSpeed", 1).setComment("The forwardspeedthe windaxe provides if you rightclick.");

		UPWARD_SPEED = config.get("WindAxe", "UpWardSpeed", 1).getInt();
		config.get("WindAxe", "UpWardSpeed", 1).setComment("The upwardspeedthe windaxe provides if you rightclick.");
		//
		// forwardspeedboots = config.get("Speed_Cobaltboots", "ForWardSpeed",
		// 0.3).getDouble();
		// config.get("Speed_Cobaltboots", "ForWardSpeed", 0.3).comment = "The
		// forwardspeed the Cobaltboots of Speed provides. 0.3 will be 30%
		// faster.";
		//
		// devenabled = config.get("Development", "Dev Enabled",
		// false).getBoolean(false);
		// config.get("Development", "Dev Enabled", false).comment = "To enable
		// the Development Items/Blocks.";

		// templeenabled = config.get(GENERATION, "Temple Enabled",
		// true).getBoolean(true);
		// config.get(GENERATION, "Temple Enabled", true).comment = "To enable
		// the spawning of the Temple.";

		// config.get("BiomeId", "Blue Hills", biomehillsid)
		// config.get("BiomeId", "Blue Hills", biomehillsid)

		config.save();

		CMContent.DARKWATER_FLUID = new Fluid("darkwater_fluid", new ResourceLocation(CMMain.MODID + "blocks/darkwater_flow"), new ResourceLocation(CMMain.MODID + "blocks/darkwater_flow"));
		FluidRegistry.registerFluid(CMContent.DARKWATER_FLUID);

		// EnumMaterial
		CMContent.COBALT_ARMOR = EnumHelper.addArmorMaterial("COBALT_ARMOR", CMMain.MODID + ":cobalt_armor", 50, new int[] { 4, 9, 7, 4 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		// this.BackpackArmor = EnumHelper.addArmorMaterial("Backpack Armor",
		// -1, new int[] { 0, 1, 0, 0 }, 0);
		// this.CobaltBackpackArmor = EnumHelper.addArmorMaterial("Cobalt
		// Backpack Armor", -1, new int[] { 4, 9, 7, 4 }, 0);
		CMContent.COBALT_TOOL = EnumHelper.addToolMaterial("COBALT_TOOL", 4, 2000, 15.0F, 4.0F, 20);
		CMContent.COBEX_TOOL = EnumHelper.addToolMaterial("COBEX_TOOL", 1, 150, 6.0F, 1.0F, 10);
		// CMContent.MagicRodTool = EnumHelper.addToolMaterial("MagicRod Tool",
		// 1, 10, 0.0F, 0.0F, 0);

		// Blocks/Items
		CMBlocks.init();
		CMItems.init();
		// CMPotions.init();

		// CMReplace
		CMReplace.addBlocks(CMContent.COBALT_DIRT, CMContent.COBALT_GRASS);
		CMReplace.addBlocks(Blocks.DIRT, CMContent.COBALT_GRASS);
		CMReplace.addBlocks(Blocks.GRASS, CMContent.COBALT_GRASS);
		CMReplace.addBlocks(Blocks.LOG, CMContent.COBEX_LOG);
		CMReplace.addBlocks(Blocks.LOG2, CMContent.COBEX_LOG);
		CMReplace.addBlocks(Blocks.TALLGRASS, CMContent.BLUE_TALL_GRASS);

		CMReplace.addFlowers(CMContent.CLEMATIS_FLOWER);
		CMReplace.addFlowers(CMContent.BELL_FLOWER);
		CMReplace.addFlowers(CMContent.GLOW_FLOWER);

		// Handler
		// FMLCommonHandler.instance().bus().register(new CraftingHandler());
		// FMLCommonHandler.instance().bus().register(new PickupHandler());
		// FMLCommonHandler.instance().bus().register(new SmeltingHandler());
		// FMLCommonHandler.instance().bus().register(new SpeedBootsHandler());

		// MinecraftForge.EVENT_BUS.register(new
		// CobaltBlockBreakEventHandler());
		MinecraftForge.EVENT_BUS.register(new CMLivingUpdateEventHandler());
		MinecraftForge.EVENT_BUS.register(new CMRainEventHandler());
		// MinecraftForge.EVENT_BUS.register(new HurtBlocksHandler());

		// Fluid
		// BucketHandler.INSTANCE.buckets.put(CMContent.darkwater,
		// CMContent.bucket_darkwater);
		// MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		// CMContent.darkwater_fluid.setBlock(CMContent.darkwater);
		// FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("darkwater_fluid",
		// FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
		// CMContent.bucket_darkwater), new ItemStack(Items.bucket));

		// Achievement
		//AchievementHandler.init();

		CMBiomeGenBase.init();

		// Dimension
		type_cobaldis = DimensionType.register("cobaldis", "_cobalt", id_cobaldis, WorldProviderCobaldis.class, false);
		DimensionManager.registerDimension(id_cobaldis, type_cobaldis);
		
		type_deep_caves = DimensionType.register("deep_caves", "_cobalt", id_deep_caves, WorldProviderDeepCaves.class, false);
		DimensionManager.registerDimension(id_deep_caves, type_deep_caves);
		
		
		//
		//DimensionManager.registerProviderType(cobaltdimension1, WorldProviderCobaltCaves.class, true);
		//DimensionManager.registerDimension(cobaltdimension1,cobaltdimension1);

		// Recipe
		//RecipeHandler.init();

		// GuiHandler
		// NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		// CobaltPacketDispatcher.registerPackets();

		// Worldgenerator Registration
		GameRegistry.registerWorldGenerator(new WorldGeneratorDim(), 0);
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		CMBiomeGenBase.initBiomeType();
		CMBlocks.oredictregister();
		
		// CobaltZombie
		// EntityRegistry.registerModEntity(EntityCobaltZombie.class,
		// "CobaltZombie", 1, this, 80, 2, true);
		// EntityList.addMapping(EntityCobaltZombie.class, "CobaltZombie",
		// CMMain.getUniqueEntityId());
		// registerEntityEgg(EntityCobaltZombie.class, 0x006633, 0x0033CC);

		// CobaltGuardian
		// EntityRegistry.registerModEntity(EntityCobaltGuardian.class,
		// "CobaltGuardian", 2, this, 50, 2, true);
		// EntityList.addMapping(EntityCobaltGuardian.class, "CobaltGuardian",
		// CMMain.getUniqueEntityId());
		// registerEntityEgg(EntityCobaltGuardian.class, 0x999999, 0x0033CC);

		// EntityBlueBuddy
		// EntityRegistry.registerModEntity(EntityBlueBuddy.class, "BlueBuddy",
		// 3, this, 50, 2, true);
		// EntityList.addMapping(EntityBlueBuddy.class, "BlueBuddy",
		// CMMain.getUniqueEntityId());
		// registerEntityEgg(EntityBlueBuddy.class, 0x00CCFF, 0x0033CC);

		// EntityCobaltGuardianMinion
		// EntityRegistry.registerModEntity(EntityCobaltGuardianMinion.class,
		// "CobaltGuardianMinion", 4, this, 50, 2, true);
		// EntityList.addMapping(EntityCobaltGuardianMinion.class,
		// "CobaltGuardianMinion", CMMain.getUniqueEntityId());
		// registerEntityEgg(EntityCobaltGuardianMinion.class, 0x00CCFF,
		// 0xFF0000);

		// EntityBlueSlime
		// EntityRegistry.registerModEntity(EntityBlueSlime.class, "BlueSlime",
		// 5, this, 50, 2, true);
		// EntityList.addMapping(EntityBlueSlime.class, "BlueSlime",
		// CMMain.getUniqueEntityId());
		// registerEntityEgg(EntityBlueSlime.class, 0x99FFFF, 0x0033CC);

		// EntityCobaltTntPrimed
		// EntityRegistry.registerModEntity(EntityCobaltTntPrimed.class,
		// "CobaltTntPrimed", 6, this, 50, 2, true);
		// EntityList.addMapping(EntityCobaltTntPrimed.class, "CobaltTntPrimed",
		// CMMain.getUniqueEntityId());

		// CobaltArrow
		// EntityRegistry.registerModEntity(EntityCobexArrow.class,
		// "CobexArrow", 7, this, 128, 1, true);
		// EntityList.addMapping(EntityCobexArrow.class, "CobexArrow",
		// CMMain.getUniqueEntityId());

		// Altar
		// GameRegistry.registerTileEntity(TileEntityAltar.class,
		// "tileentityaltar");

		// RitualStone
		// GameRegistry.registerTileEntity(TileEntityRitualStone.class,
		// "tileentityritualstone");

		// CobexChest
		//GameRegistry.registerTileEntity(TileEntityCobexChest.class, CMMain.MODID + ":tileentity_cobex_chest");

		// Furnace
		// GameRegistry.registerTileEntity(TileEntityCobaltFurnace.class,
		// "tileentitycobaltfurnace");

		// Stone Furnace
		// GameRegistry.registerTileEntity(TileEntityCorruptedStoneFurnace.class,
		// "tileentitycorruptedstonefurnace");

		// CobaltChest
		// GameRegistry.registerTileEntity(TileEntityLockedCobaltChest.class,
		// "tileentitylockedcobaltchest");

		// Podium
		// GameRegistry.registerTileEntity(TileEntityPodium.class,
		// "tileentitypodium");

		// Neutralizer
		// GameRegistry.registerTileEntity(TileEntityNeutralizer.class,
		// "tileentityneutralizer");

		/**
		 * Use with caution Reason because it lags so intense. Not so good .-.
		 */
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			CMBlocks.registerBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerBlockModels(ModelRegistryEvent event) {
			CMBlocks.initTextures();
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			CMItems.registerItems(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItemModels(ModelRegistryEvent event) {
			CMItems.initTextures();
		}

		@SubscribeEvent
		public static void registerBiomes(RegistryEvent.Register<Biome> event) {
			CMBiomeGenBase.registerBiomes(event.getRegistry());
		}
	}

	public static Logger getLogger() {
		return log;
	}
}