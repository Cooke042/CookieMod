package CookieMod;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;


import net.minecraft.src.ModelRenderer;
import net.minecraft.src.NextTickListEntry;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;

public class Screen extends Model {

	float x, y, z; // screen Position vector relative to origin
	private Pixel[][] grid; // Pixel array
	public int sWidth, sHeight;
	private float pixelWidth, pixelHeight; // Pixel size width, height
	public static int black = 24;
	Pixel borderPixel;

	public Screen(int sizex, int sizey) {
		grid = new Pixel[sizex][sizey];
		setPixelAspect(sizex*.0625f, sizey*.0625f);//defailt 1:1 at pixel scale
		clearScreen();
		borderPixel = new Pixel(10+16);
		
		
		for (int i = 0; i < 4; i++) {

			setRegion(borderPixel, 5, 5, grid.length-5, grid[0].length-5);
			
//			drawVLine(borderPixel, i*5, 5, 20);
//			drawHLine(borderPixel, i*5, 5, 20);
		}
	}

	public Pixel getPixel(int x, int y) {
		return grid[x][y];
	}

	public void drawArray(Tessellator tessellator, float time) {
	
		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, -1);
	
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[1].length; j++) {
				if (grid[i][j] != null) {
					int val = grid[i][j].textureid;
					Vector color = grid[i][j].color;
					tessellator.setColorOpaque_F(color.x, color.y, color.z);
					addBoxWithTextureId(tessellator, i, j, val);
				}
			}
		}
		
		tessellator.draw();
	}

	public void clearScreen() {
		for (int i = 0; i < grid.length; i++) { // fill array with ones
			for (int j = 0; j < grid[i].length; j++) {
				//grid[i][j] = new Pixel(24);
				grid[i][j] = null;
			}
		}
	}
	
	public void setPixelAspect(float screenwidth, float screenheight){
		pixelWidth = screenwidth /(float) grid.length;
		pixelHeight = screenheight /(float) grid[0].length;
	}

	public void setRegion(Pixel pixel, int stx, int sty, int enx, int eny) {

		if (stx < 0 || stx > enx)
			stx = (stx < 0) ? 0 : enx;

		if (sty < 0 || sty > eny)
			sty = (sty < 0) ? 0 : eny;

		if (enx < stx || enx > grid.length)
			enx = (enx < stx) ? stx : grid.length;

		if (eny < sty || eny > grid[0].length)
			eny = (eny < sty) ? sty : grid[0].length;

		for (int i = stx; i < enx; i++) {
			for (int j = sty; j < eny; j++) {
				grid[i][j] = pixel;
			}
		}
	}

	public void setHLine(Pixel pixel, int ypos, int start, int end) {
		if (grid[ypos].length < end)
			end = grid[ypos].length;
		if (start < 0)
			start = 0;
		for (int i = start; i < end; i++) {
			grid[i][ypos] = pixel;
		}
	}

	public void setVLine(Pixel pixel, int xpos, int start, int end) {
		if (grid.length < end)
			end = grid.length;
		if (start < 0)
			start = 0;
		for (int i = start; i < end; i++) {
			grid[xpos][i] = pixel;
		}
	}

	public boolean setGridTexture(int texId, int x, int y) {
		grid[x][y].textureid = texId;
		return true;
	}

	float ts = .0625f;

	public void addBoxWithTextureId(Tessellator tess, int x, int y, int id) {
		float tex = ts * (id % 16);
		float tey = ts * (id / 16);

		tess.addVertexWithUV(-x * pixelWidth, -y * pixelHeight, 0, tex, tey + ts);
		tess.addVertexWithUV(-x * pixelWidth, -y * pixelHeight - pixelHeight, 0, tex, tey);
		tess.addVertexWithUV(-x * pixelWidth - pixelWidth, -y * pixelHeight - pixelHeight, 0, tex + ts, tey);
		tess.addVertexWithUV(-x * pixelWidth - pixelWidth, -y * pixelHeight, 0, tex + ts, tey + ts);
	}

	class Pixel {
		Vector localOffset;
		float fallspeed;
		int textureid;
		Vector color;
		boolean falling;
 
		
		public Pixel(Vector localOffset, int textureid, Vector color) {
			this.localOffset = localOffset;
			this.textureid = textureid;
			this.color = color;
			this.falling = false;
			fallspeed = 0f;
		}

		public Pixel(int textureid) {
			this(Vector.zero(),textureid,Vector.one());
		}
		
	}
}
