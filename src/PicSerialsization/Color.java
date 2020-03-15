package PicSerialsization;

/**
 * @author ericmiddelhove
 */

public class Color {
	
	public int r, g, b;
	
	/**
	 * @param d redValue 0-255
	 * @param e greenValue 0-255
	 * @param f blueValue 0-255
	 */
	public Color(int d, int e, int f) {
		r = d;
		g = e;
		b = f;
	}
	
	public Color(int[] d) {
		r = d[0];
		g = d[1];
		b = d[2];
	}
	
	public int[] getColorData() {
		int[] a = {r, g, b};
		return a;
	}
}
