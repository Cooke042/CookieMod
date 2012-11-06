package CookieMod;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBoat;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	// client stuff?
	@Override
	public void registerRenderers() {
		// Or 'this' if your proxy happens to be the one that implements the block render interface.
		// This variable renderId? this thing has got to be static somewhere. not necessarily public, but we need it available form everywhere. at the
		// minimum we need public accessors. I personally have this variable as a public int in my common @mod class.

		ClientRegistry.registerTileEntity(TileGameMachine.class, "TestingTile", new RenderGameTile());

		MinecraftForgeClient.preloadTexture(ITEMS_PNG);
		MinecraftForgeClient.preloadTexture(BLOCK_PNG);
		
	}
}
