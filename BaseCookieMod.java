package CookieMod;

import java.util.EnumSet;

import net.minecraft.src.Block;
import net.minecraft.src.BlockTallGrass;
import net.minecraft.src.BlockTorch;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Render;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "CookieMod", name = "CookieMod", version = "0.1a")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class BaseCookieMod {

	public static final Item genericItem = new GenericItem(5001).setIconIndex(0);
	public static int renderId;
	// public static Model tvModel;

	// The instance of your mod that Forge uses.
	@Instance("BaseCookieMod")
	public static BaseCookieMod instance; // This is the object reference to your class that Forge uses.

	// -------------Blocks--------------
	public static final Block blockEntertainment = new BlockBaseGame(500, 5)
				.setHardness(0.5F)
				.setStepSound(Block.soundStoneFootstep)
				.setBlockName("Entertainment Block")
				.setCreativeTab(CreativeTabs.tabDecorations);
	
	//public static final ItemArmor testArmor = new ItemArmor(5002, EnumArmorMaterial.DIAMOND, 3, 3);

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "CookieMod.ClientProxy", serverSide = "CookieMod.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// This method is called before Init. This is where reading configuration files goes.
	}

	@Init
	public void load(FMLInitializationEvent event) {
		// This is where the majority of your Mod initialization will go.
		// Block and item registry, WorldGen registry, and crafting recipes are common things found here.
		// tvModel = Model.lo

		// ------------Blocks----------
		MinecraftForge.setBlockHarvestLevel(blockEntertainment, "shovel", 0);
		GameRegistry.registerBlock(blockEntertainment);
		
		LanguageRegistry.addName(blockEntertainment, "Nintendo!");

		MinecraftForge.EVENT_BUS.register(blockEntertainment);

		//RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer)(renderId, new renderFurniture());
		// ------------Items----------
		LanguageRegistry.addName(genericItem, "Tile Entity Item");

		// ------------Entitys----------
		//EntityRegistry.registerModEntity(.class, "seatEnt", 1,this,250,5,false);
		//EntityRegistry.registerGlobalEntityID(Seat.class, "searEnt", ModLoader.getUniqueEntityId());
				
		// ------------TileEntitys----------

		// ------------Crafting----------
		GameRegistry.addRecipe(new ItemStack(blockEntertainment, 64), "x", 'x', Block.dirt);

		proxy.registerRenderers();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// This is where code goes for working with other mods. For example, setting up custom Equivalent Exchange block and item values.
	}
}