package cookieMod;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockOre;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

public class GenericOre extends BlockOre{

	public GenericOre(int id, int texture) {
		super(id, texture);
		
		setHardness(4f);
		setStepSound(Block.soundStoneFootstep);
		setBlockName("Generic Ore");
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	
	@Override
	public String getTextureFile() {
		return ClientProxy.BLOCK_PNG;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return ModBaseGamer.genericItem.shiftedIndex;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ModBaseGamer.renderId;
	}
}
