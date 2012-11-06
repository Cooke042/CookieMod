package cookieMod;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public static String ITEMS_PNG = "/cookieMod/Items.png";
	public static String BLOCK_PNG = "/cookieMod/Block.png";
	public static String OBJECTS_PNG = "/cookieMod/Block.png";
	
	//client stuff?
	public void registerRenderers() {
		GameRegistry.registerTileEntity(GameMachineTile.class, "TestTile");
		GameRegistry.registerTileEntity(SeatTile.class, "TestTile");
	}
}
