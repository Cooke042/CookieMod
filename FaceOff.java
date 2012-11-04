package cookieMod;

public class FaceOff {
	
	private int x, y, z;
	private int fdir = -1;
	
	public FaceOff(int dir) {
		setOffset(dir);
	}

	private void setOffset(int dir) {
		if (dir>5 || dir == fdir)
			return;
		fdir = dir;
		x = 0; y = 0; z = 0;
		switch (dir) {
		case 0:
			y++;
			break;
		case 1:
			y--;
			break;
		case 2:
			z++;
			break;
		case 3:
			z--;
			break;
		case 4:
			x++;
			break;
		case 5:
			x--;
			break;

		default:
			break;
		}
	}
}
