package CookieMod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

import org.lwjgl.opengl.GL11;


public class CJB_BlockMesh extends Block
{

    protected CJB_BlockMesh(int i, Material material)
    {
        super(i, material);
        blockIndexInTexture = Block.blockSteel.blockIndexInTexture;
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.8F - f / 2.0F, 0.0F, 1.0F, 0.8F + f / 2.0F, 1.0F);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
    	return 0;
        //return mod_cjb_items.MeshModelID;
    }
    
	@Override
	public void addCollidingBlockToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entitiy) {

        setBlockBounds(0.3F, 0.8F, 0.2F, 0.7F, 0.9F, 0.3F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entitiy);
		
		setBlockBounds(0.3F, 0.8F, 0.7F, 0.7F, 0.9F, 0.8F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entitiy);
		
        setBlockBounds(0.2F, 0.8F, 0.3F, 0.3F, 0.9F, 0.7F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entitiy);
		
        setBlockBounds(0.7F, 0.8F, 0.3F, 0.8F, 0.9F, 0.7F);
		super.addCollidingBlockToList(world, x, y, z, aabb, list, entitiy);
       
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.8F, 0.0F, 1F, 0.9F, 1F);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }

    
    public void RenderInInv(RenderBlocks renderblocks, Block block, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        for(int j = 0; j < 4; j++)
        {
            if(j == 0)
            {
                block.setBlockBounds(0.0F, 0.8F, 0.2F, 1F, 0.9F, 0.3F);
            }
            if(j == 1)
            {
                block.setBlockBounds(0.0F, 0.8F, 0.7F, 1F, 0.9F, 0.8F);
            } 
            if(j == 2)
            {
                block.setBlockBounds(0.2F, 0.8F, 0.0F, 0.3F, 0.9F, 1F);
            }
            if(j == 3)
            {
                block.setBlockBounds(0.7F, 0.8F, 0.0F, 0.8F, 0.9F, 1F);
            }
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            GL11.glScalef(1f, 1f, 1f);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
        block.setBlockBounds(0.0F, 0.8F, 0.0F, 1F, 0.9F, 1F);
    }
    
    public boolean RenderInWorld(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
    {
       
        block.setBlockBounds(0.0F, 0.8F, 0.2F, 1F, 0.9F, 0.3F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        block.setBlockBounds(0.0F, 0.8F, 0.7F, 1F, 0.9F, 0.8F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        block.setBlockBounds(0.2F, 0.8F, 0.0F, 0.3F, 0.9F, 1F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        block.setBlockBounds(0.7F, 0.8F, 0.0F, 0.8F, 0.9F, 1F);
        renderblocks.renderStandardBlock(block, i, j, k);
        return false;
    }
}