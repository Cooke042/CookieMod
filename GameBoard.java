package cookieMod;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import net.minecraft.src.ModelRenderer;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;

public class GameBoard extends Model{

	float x, y, z;
	int[][] grid;
	float sw, sh;

	public GameBoard(int sizex, int sizey, int pixelsize) {
		sw = 10.974f/16f/(float)sizex;
		sh = 8.768f/16f/(float)sizey;
		grid = new int[sizex][sizey];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = 1;
			}
		}
	}

	public boolean setGridValue(int n, int x, int y) {
		grid[x][y] = n;
		return true;
	}
	
	public void drawArray(Tessellator tessellator) {
		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, -1);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
			int val = grid[i][j];
			//addBoxWithTextureId(tessellator, i, j, grid[i][j]);
			addBoxWithTextureId(tessellator, i, j, j);
//			tessellator.addVertexWithUV(-i * sw, j * sh, 0, i*ts, (i+1)*ts);
//			tessellator.addVertexWithUV(-i * sw - sw, j * sh, 0, (i+1)*ts, (i+1)*ts);
//			tessellator.addVertexWithUV(-i * sw - sw, j * sh + sh, 0, (i+1)*ts, i*ts);
//			tessellator.addVertexWithUV(-i * sw , j * sh + sh, 0, 0, i*ts);
			}
		}
		tessellator.draw();
	}
	
	float ts = 16/256f;
	public void addBoxWithTextureId(Tessellator tess, int x, int y, int id){
		float tex = ts*(id%16);
		float tey = ts*(id/16);
				
		tess.addVertexWithUV(-x * sw, y * sh, 0, tex, tey+ts);
		tess.addVertexWithUV(-x * sw - sw, y * sh, 0, tex+ts, tey+ts);
		tess.addVertexWithUV(-x * sw - sw, y * sh + sh, 0, tex+ts, tey);
		tess.addVertexWithUV(-x * sw , y * sh + sh, 0, tex, tey);
	}
}
