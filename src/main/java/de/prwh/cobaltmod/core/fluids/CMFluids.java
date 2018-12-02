package de.prwh.cobaltmod.core.fluids;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import de.prwh.cobaltmod.core.CMMain;
import de.prwh.cobaltmod.core.api.CMContent;
import de.prwh.cobaltmod.core.lib.CMLib;
import de.prwh.cobaltmod.core.lib.MeshDefinitionFix;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CMFluids {
	public static Fluid DARKWATER;
	/**
	 * The fluids registered by this mod. Includes fluids that were already
	 * registered by another mod.
	 */
	public static final Set<Fluid> FLUIDS = new HashSet<>();
	/**
	 * The fluid blocks from this mod only. Doesn't include blocks for fluids
	 * that were already registered by another mod.
	 */
	public static final ArrayList<IFluidBlock> MOD_FLUID_BLOCKS = new ArrayList<>();

	public static void registerFluids() {
		DARKWATER = createFluid("dark_water", true, fluid -> fluid.setLuminosity(1).setDensity(1600).setViscosity(100),
				fluid -> new BlockFluidClassic(fluid, Material.WATER));
		// Fluids-> NanpaContent, apply to Content, should be same oder, or
		// there are funny bugs ^^
		CMContent.DARK_WATER = (Block) CMFluids.MOD_FLUID_BLOCKS.get(0);
	}

	public static void registerFluidContainers() {
		// registerTank(FluidRegistry.WATER);
		// registerTank(FluidRegistry.LAVA);
		FLUIDS.forEach((Fluid fluid) -> registerBucket(fluid));
		for (final Fluid fluid : FLUIDS) {
			registerBucket(fluid);
			// registerTank(fluid);
		}
	}

	/**
	 * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing
	 * ones if a fluid has already been registered with the same name.
	 *
	 * @param name
	 *            The name of the fluid
	 * @param hasFlowIcon
	 *            Does the fluid have a flow icon?
	 * @param fluidPropertyApplier
	 *            A function that sets the properties of the {@link Fluid}
	 * @param blockFactory
	 *            A function that creates the {@link IFluidBlock}
	 * @return The fluid and block
	 */
	private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon,
			Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {

		final String texturePrefix = CMMain.MODID + ":blocks/fluid_";
		final ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
		final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;
		Fluid fluid = new Fluid(name, still, flowing);
		final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
		if (useOwnFluid) {
			fluidPropertyApplier.accept(fluid);
			registerFluidBlock(blockFactory.apply(fluid));
		} else {
			fluid = FluidRegistry.getFluid(name);
		}
		FLUIDS.add(fluid);
		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		block.setRegistryName("fluid." + block.getFluid().getName());
		block.setTranslationKey(block.getFluid().getName());
		// block.setCreativeTab(NanpaMain.nanpatabfluids);
		CMLib.registerWithItem(block);
		MOD_FLUID_BLOCKS.add(block);
		return block;
	}

	private static void registerBucket(Fluid fluid) {
		FluidRegistry.addBucketForFluid(fluid);
	}
	/*
	 * private static void registerTank(Fluid fluid) { final FluidStack
	 * fluidStack = new FluidStack(fluid, TileEntityFluidTank.CAPACITY);
	 * ((ItemFluidTank)
	 * Item.getItemFromBlock(ModBlocks.FLUID_TANK)).addFluid(fluidStack); }
	 */

	@SideOnly(Side.CLIENT)
	public static void registerFluidModels() {
		for (IFluidBlock block : MOD_FLUID_BLOCKS) {
			registerFluidModel(block);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerFluidModel(IFluidBlock fluidBlock) {
		final Item item = Item.getItemFromBlock((Block) fluidBlock);
		assert item != null;
		ModelBakery.registerItemVariants(item);
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(CMMain.MODID + ":" + "fluid",
				fluidBlock.getFluid().getName());
		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));
		CMLib.registerInventoryItem(item);
		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
		// itemsRegistered.add(item);
	}
}