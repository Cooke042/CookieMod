package cookieMod;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public static String ITEMS_PNG = "/generic/Items.png";
	public static String BLOCK_PNG = "/generic/Block.png";
	public static String OBJECTS_PNG = "/generic/Block.png";
	
	//client stuff?
	public void registerRenderers() {
		GameRegistry.registerTileEntity(GameMachineTile.class, "TestTile");
	}
}
