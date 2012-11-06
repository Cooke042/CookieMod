package cookieMod;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockEntity extends BlockContainer {

	public BlockEntity(int blockId) {
		super(blockId, Material.rock);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
		super.onBlockPlacedBy(world, x, y, z, entityLiving);
		// Entity seat = new SeatEntity(world);
		// seat.setPosition(x, y, z);
		// world.spawnEntityInWorld(seat);
		setBlockBounds(0, 0, 0, 1, .25f, 1);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float posx, float posy, float posz) {
		//System.out.println(par6 + ", " + par7 + ", " + par8 + ", " + par9);
		SeatTile seatTile = (SeatTile)world.getBlockTileEntity(x, y, z);
		seatTile.seat1.setPosition(x, y, z);
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SeatTile(world);
	}
}
