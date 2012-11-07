package CookieMod;

import java.awt.image.TileObserver;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.omg.CORBA.OMGVMCID;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

import static org.lwjgl.opengl.GL11.*;

import cpw.mods.fml.common.Mod.Block;

public class RenderGameTile extends TileEntitySpecialRenderer {

	private static Model testTv;
	private static Tessellator tessellator = Tessellator.instance;

	public RenderGameTile() {

		// NMTMODELRENDERER
		// modelRenderer = new NMTModelRenderer(new ModelChest(), "testing");
		// ModelTileRenderer.File f = new File("mods/generic/boxTest.obj");
		// modelRenderer.addModelOBJ(f.toURI().toString());

		testTv = Model.LoadModelFromFile("boxTv.obj", CommonProxy.BLOCK_PNG);
		// gamescreen = new GameBoard(7.085f/16f, 11.762f/16f, -4.031f/16f, 20, 16, 16);

	}

	public void setTileEntityRenderer(TileEntityRenderer tileEntityRenderer) {
		super.setTileEntityRenderer(tileEntityRenderer);
	}

	public void renderTile(TileGameMachine tile, double x, double y, double z, float time) {

		tile.renderUpdate(time);
		Screen gb = tile.getGamescreen();
		RenderBlocks br = new RenderBlocks(tile.worldObj);

		glPushMatrix();

		br.renderBlockAllFaces(net.minecraft.src.Block.dirt, (int) x, (int) y + 1, (int) z);

		glTranslatef((float) x + .5f, (float) y, (float) z + .5f);

		glRotatef(tile.facingDir * -90f, 0, 1, 0); // set facing dir
		
		bindTextureByName(CommonProxy.BLOCK_PNG);

		// ---draw Model---
		glPolygonMode(GL_FRONT, GL_FILL);
		Model.drawModelById(tile.getModelid(), tessellator);

		// ---Draw Screen---
		glPushMatrix();
		glTranslatef(testTv.screenPos.x, testTv.screenPos.y, testTv.screenPos.z);
		gb.drawArray(tessellator, time);
		glPopMatrix();		

		// ---Draw Wire-frame---
		// used for the highlight effect
		if (tile.drawWire) {
			glColor3f(0, 0, 0);
			glPolygonMode(GL_FRONT, GL_LINE);
			Model.drawModelById(tile.getModelid(), tessellator);
			glPolygonMode(GL_FRONT, GL_FILL);
			tile.drawWire = false;
		}

		glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity tileEntity, double par2, double par4, double par6, float par8) {
		renderTile((TileGameMachine)tileEntity, par2, par4, par6, par8);
	}
}
