package PicSerialsization;

/**
 * @author ericmiddelhove
 */
public class Color {
	public int r,g,b;
	
	/**
	 * 
	 * @param d redValue 0-255
	 * @param e greenValue 0-255
	 * @param f blueValue 0-255
	 */
	public Color(int d, int e, int f) {
		this.r = d;
		this.g = e;
		this.b = f;
	}
	
	public Color(int[] d) {
		this.r = d[0];
		this.g = d[1];
		this.b = d[2];
	}
	
	public int[] getColorData() {
		int[] a = {r,g,b};
		return a;
	}
}
