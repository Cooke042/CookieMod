package CookieMod;

import java.util.ArrayList;
import java.util.EnumSet;
import java.awt.List;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.net.URL;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.tree.analysis.Value;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.ModelZombieVillager;
import net.minecraft.src.Tessellator;

@SideOnly(Side.CLIENT)
public class Model {

	public static ArrayList<Model> models = new ArrayList<Model>();

	private boolean fileLoaded = false;

	String name;
	private int id;
	String texture;

	// -------Vertex Data
	ArrayList<Vector> verts;
	ArrayList<Face> faces;
	ArrayList<Uv> uvs;
	ArrayList<Vector> normals;

	// -------Screen Data
	private boolean hasScreen = false;
	Vector screenPos = Vector.zero();
	public float screenwidth, screenheight;

	public Model() {
		this("unNamed");
	}

	public Vector getScreenPos() {
		return this.screenPos;
	}

	public Model(String name) {
		this.name = name;
		verts = new ArrayList<Vector>();
		uvs = new ArrayList<Uv>();
		faces = new ArrayList<Face>();
		normals = new ArrayList<Vector>();
	}

	public static void drawModelById(int id, Tessellator tessellator) {
		models.get(id).drawModel(tessellator);
	}

	public void drawModel(Tessellator tessellator) {
		for (int f = 0; f < faces.size(); f++) {
			Face face = faces.get(f); // get face for this itteration
			if (face.num == 4)
				tessellator.startDrawingQuads();
			else
				tessellator.startDrawing(GL11.GL_TRIANGLES);
			Vector normal = normals.get(face.vData[1].c); // get normal from vertdata and normalize
			tessellator.setNormal(normal.x, normal.y, normal.z); // set normal for following quad
			for (int v = 0; v < face.num; v++) { // iterate over face verticals
				Uv uvdata = uvs.get(faces.get(f).vData[v].b);
				Vector vertdata = verts.get(faces.get(f).vData[v].a);

				tessellator.addVertexWithUV(vertdata.x, vertdata.y, vertdata.z, uvdata.u, -uvdata.v); // v
			}

			tessellator.draw();
		}

	}
	
	public boolean hasScreen() {
		return hasScreen;
	}

	// -------
	// this does quite a few different things
	// gets the model file, stores texture location
	// reads the file line by line, and checking the leading characters
	// if there is a line what starts with g screen, it has found an object named screen in the 3d file.
	// these will not be added to the faces array instead it will store
	// the positions in the screen position variables and flag the model for having a display set up
	// tag ref:
	// # - comment
	// g <name>- new object follows this line
	// v - vertex coordinate x,y,z
	// vn - vertex normal vector x,y,z
	// vt - texture coordinae u,v
	// f - face data, this stores the id's that define each vertex of the face
	// formated:"f vID/vtID/vnID vID/vtID/vnID vID/vtID/vnID [vID/vtID/vnID]" 3 for a tri face, 4 for a quad
	// note: the IDs in the obj file are base 1, not 0 like the arrays they are stored in
	// ----------------------------------------------
	public static Model LoadModelFromFile(String fileName, String texture) {

		URL url = Model.class.getResource(fileName);
		File file = new File(url.getFile());

		if (file.exists()) {
			try {
				Model model = new Model();
				FileInputStream fis = new FileInputStream(file.getPath());
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				// String[] fileString = file.getName()//.split("/"); //splits the file string
				model.name = file.getName();// fileString[fileString.length - 1];
				model.texture = texture;

				String line; // holder for each line as it iterates over the file;

				boolean screenFaces = false;

				while ((line = br.readLine()) != null) {
					if (line.startsWith("#"))
						continue;

					// if it's a screen face we dont want to draw it but to get position/size data from it;
					if (line.startsWith("g screen")) {
						System.out.println("found face");
						screenFaces = true;
					} else if (!line.startsWith("f"))
						screenFaces = false;

					if (line.startsWith("v ")) { // --------vertex coordinate
						float x, y, z;
						line = line.substring(3);
						String[] s = line.split(" ");
						x = Float.valueOf(s[0]).floatValue() / 16f;
						y = Float.valueOf(s[1]).floatValue() / 16f;
						z = Float.valueOf(s[2]).floatValue() / 16f;
						// System.out.println("v = " + x + ", " + y + ", " + z);
						model.verts.add(new Vector(x, y, z));

					} else if (line.startsWith("vn ")) { // ----------vertex
															// normal
						float x, y, z;
						line = line.substring(3);
						String[] s = line.split(" ");
						x = Float.valueOf(s[0]).floatValue();
						y = Float.valueOf(s[1]).floatValue();
						z = Float.valueOf(s[2]).floatValue();
						// System.out.println(x + ", " + y + ", " + z);
						model.normals.add(new Vector(x, y, z));

					} else if (line.startsWith("vt ")) { // -------texture coordinate
						float u, v;
						line = line.substring(3);
						String[] s = line.split(" ");
						u = Float.valueOf(s[0]);
						v = Float.valueOf(s[1]);
						// System.out.println(u + ", " + v);
						model.uvs.add(new Uv(u, v));

					} else if (line.startsWith("f ")) { // face declaration if all requirements met. 4 verts and 3 id per face

						int i = 0;
						line = line.substring(2);
						// System.out.println(line);
						String[] s = line.split(" ");
						String[] temp = s[0].split("/");
						if (screenFaces) { // dont add screen faces so they will be drawn, instead store position data

							Vector maxPos = Vector.zero();
							Vector vert;
							for (int index = 0; index < s.length; index++) {
								vert = model.verts.get(Integer.valueOf(s[index].split("/")[0]) - 1); //
								if (index == 0) {
									model.screenPos.x = (float) vert.x;
									model.screenPos.y = (float) vert.y;
									model.screenPos.z = (float) vert.z;
									maxPos.x = (float)vert.x;
									maxPos.y = (float)vert.y;
									maxPos.z = (float)vert.z;
								} else {
									if (vert.x > model.screenPos.x)
										model.screenPos.x = (float) vert.x;
									if (vert.y > model.screenPos.y)
										model.screenPos.y = (float) vert.y;
									if (vert.z > model.screenPos.z)
										model.screenPos.z = (float) vert.z;
									if (vert.x < maxPos.x)
										maxPos.x = (float) vert.x;
									if (vert.y < maxPos.y)
										maxPos.y = (float) vert.y;
									if (vert.z < maxPos.z)
										maxPos.z = (float) vert.z;
								}

							}

							model.screenwidth = (model.screenPos.x - maxPos.x);
							model.screenheight = (model.screenPos.y - maxPos.y);
							//System.out.println(model.screenwidth);
							//System.out.println(model.screenheight);

							System.out.println("screen position found at:" + model.screenPos.toString());
							model.hasScreen = true;
						} else if ((s.length == 4 || s.length == 3) && temp.length == 3) {

							int3 v1 = new int3((short) (Short.valueOf(temp[0]) - 1), (short) (Short.valueOf(temp[1]) - 1), (short) (Short.valueOf(temp[2]) - 1));
							// System.out.println(v1.a + ", " + v1.b + ", " + v1.c);

							temp = s[++i].split("/");
							int3 v2 = new int3((short) (Short.valueOf(temp[0]) - 1), (short) (Short.valueOf(temp[1]) - 1), (short) (Short.valueOf(temp[2]) - 1));
							// System.out.println(v2.a + ", " + v2.b + ", " + v2.c);

							temp = s[++i].split("/");
							int3 v3 = new int3((short) (Short.valueOf(temp[0]) - 1), (short) (Short.valueOf(temp[1]) - 1), (short) (Short.valueOf(temp[2]) - 1));
							// System.out.println(v3.a + ", " + v3.b + ", " + v3.c);

							if (s.length == 4) {
								temp = s[++i].split("/");
								int3 v4 = new int3((short) (Short.valueOf(temp[0]) - 1), (short) (Short.valueOf(temp[1]) - 1), (short) (Short.valueOf(temp[2]) - 1));
								// System.out.println(v4.a + ", " + v4.b + ", " + v4.c);
								model.faces.add(new Face(v1, v2, v3, v4));
							} else {
								model.faces.add(new Face(v1, v2, v3));
							}
						} else {
							if (s.length != 4)
								System.out.println("ERROR: face does not have 3 or 4 verticals");
							if (temp.length != 3)
								System.out.println("ERROR: make sure texture coords and normals are ON in obj export");
						}
					}
				}

				// System.out.println(model.normals.get(1).x + ", " +
				// model.normals.get(1).y + ", " + model.normals.get(1).z);

				br.close();
				Model.models.add(model);
				model.id = models.indexOf(model);
				System.out.println("Model " + model.name + " id:" + model.id + " loaded:" + model.verts.size() + " verts, " + model.uvs.size() + "uvs, " + model.faces.size()
						+ "faces, and " + model.normals.size() + "normals.");

				return model;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("gameConsole: Wavefront obj at" + url.getPath() + " is missing!");
		}

		return new Model();
	}

}

// Each face stores 4 sets of id's each set stores vertexid, uvid, and normalid
class Face {
	int num;
	int3[] vData;

	public Face(int3 v1, int3 v2, int3 v3, int3 v4) {
		num = 4;
		vData = new int3[4];
		vData[0] = v1;
		vData[1] = v2;
		vData[2] = v3;
		vData[3] = v4;
	}

	public Face(int3 v1, int3 v2, int3 v3) {
		num = 3;
		vData = new int3[4];
		vData[0] = v1;
		vData[1] = v2;
		vData[2] = v3;
	}

	public void addVertsToTessellator(Tessellator tessellator) {
		for (int i = 0; i < vData.length; i++) {

		}

	}

	@Override
	public String toString() {
		String string = num + "vert face:";
		for (int i = 0; i < vData.length; i++) {
			string += ( " " + vData[i].a + "/" + vData[i].b + "/" + vData[i].c);
		}
		return string;
	}
}

class int3 {
	short a;
	short b;
	short c;

	public int3(short a, short b, short c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}

// -------------------------------------------
// very simple vector class
// -------------------------------------------
class Vector {
	float x, y, z;

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}

	public static Vector zero() {
		return new Vector(0f, 0f, 0f);
	}

	public void nomalize() {
		float mag = (float) Math.sqrt(x * x + y * y + z * z);
		this.x /= mag;
		this.y /= mag;
		this.z /= mag;
	}

	public static void nomalizeAll(ArrayList<Vector> list) {
		for (Vector vert : list) {
			vert.nomalize();
		}
		return;
	}

	@Override
	public String toString() {
		return new String(x + ", " + y + ", " + z);
	}

	public static Vector one() {
		return new Vector(1, 1, 1);		
	}
}

class Uv {
	double u, v;

	public Uv(double u, double v) {
		this.u = u;
		this.v = v;
	}
}