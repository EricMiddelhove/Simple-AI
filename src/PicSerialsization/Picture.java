/**
 * 
 */
package PicSerialsization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author ericmiddelhove
 */
public class Picture{
	
	private BufferedImage img = null;
	
	/**
	 * Constructs new jpg Picture
	 * 
	 * @param path path to .jpeg picture
	 */
	public Picture(String path) {
	    File f = null;

	    //read image
	    try{
	      f = new File(path);
	      img = ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	    }
	    
	    
	}
	
	public Picture(int w_, int h_) {
		//Creates new image wit RGB Range
		img = new BufferedImage(w_ ,h_ ,BufferedImage.TYPE_INT_RGB);
	}
	
	/**
	 * Sets pixel for specified coordinate
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public void setPixel(int x, int y, int r, int g, int b) {
		int rgb = 0; 
		
		rgb = r;
		rgb = (rgb << 8) | g;
		rgb = (rgb << 8) | b;
		
		
		img.setRGB(x,y,rgb);
	}
	
	/**
	 * get RGB Value of Pixel
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return array [red, green, blue] value
	 */
 	public int[] getRGBOf(int x, int y) {
		
		int[] rgb = new int[3];
		
		int rgbRaw = img.getRGB(x, y);
		
		rgb[0] = (rgbRaw >> 16) & 0xFF; //Red
		rgb[1] = (rgbRaw >> 8) & 0xFF; //Green
		rgb[2] = (rgbRaw >> 0) & 0xFF; //Blue
		
		return rgb;
		
	}
	
	/**
	 * Get width and height of the image | count starts at 0
	 * 
	 * @return int Array [width, height];
	 */
	public int[] getDimensions() {
		int[] d = new int[2];
		
		d[0] = img.getWidth() - 1;
		d[1] = img.getHeight() - 1;
		
		return d;
		
	}
	
	public void saveImage() {
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Saved Image successfully");
	}
	
}
