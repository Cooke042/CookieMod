package cookieMod;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class GenericItem extends Item {
	
	public GenericItem(int id) {
		super(id);
		setMaxStackSize(32);
		setCreativeTab(CreativeTabs.tabMisc);
		setIconIndex(0);
		setItemName("Generic Item");		
	}
	
	
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}

}
