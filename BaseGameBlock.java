package cookieMod;

import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapColor;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.CanUpdate;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;

public class BaseGameBlock extends BlockContainer {
	public static Class tileEnt;

	protected BaseGameBlock(int par1, int par2) {
		super(par1, 7, Material.rock);
		tileEnt = GameMachineTile.class;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCK_PNG;
	}

	@Override
	public void addCollidingBlockToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entitiy) {
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entitiy);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new GameMachineTile();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) {

		int meta = (MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D)) % 4;
		GameMachineTile tt = (GameMachineTile) world.getBlockTileEntity(x, y, z);
		tt.facingDir = meta;
		tt.modelid = 0;
		setBlockBounds(0f, 0f, .2f, 1f, .7f, .8f);
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		return;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

		GameMachineTile tt = (GameMachineTile) world.getBlockTileEntity(x, y, z);
		WorldInfo wi = world.getWorldInfo();
		Model ttmodel = Model.models.get(tt.modelid);

		if (player.isSneaking()) {
			tt.drawWire = !tt.drawWire;
		} else {

			wi.setRaining(false);
			world.setWorldTime(1000);
		}
		return true;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3) {
		return blockID;
	}

	@ForgeSubscribe
	public void drawingHighlight(DrawBlockHighlightEvent event) {

		MovingObjectPosition mop = event.target;
		World world = event.context.theWorld;
		if (world.getBlockId(mop.blockX, mop.blockY, mop.blockZ) == this.blockID) {

			TileEntity tile = world.getBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
			if (tile instanceof GameMachineTile) {
				((GameMachineTile) tile).drawWire = true;
			}
			event.setCanceled(true);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two adjacent blocks and also
	 * whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

}