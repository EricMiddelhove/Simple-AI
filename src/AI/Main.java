package AI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import PicSerialsization.Color;
import PicSerialsization.Picture;

/**
 * @author ericmiddelhove
 * 
 * icense = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class Main {
	
	private static final File folder = new File("weights");
	
	static Perceptron p = new Perceptron();
	
	// Training data
	static Picture red = new Picture("src/Training Data/RED.jpg");
	static Picture notRed = new Picture("src/Training Data/NOTRED.jpg");
	
	// analyzing data
	static Picture picture = new Picture("src/Source img Data/th-4.jpeg");
	
	// Output data | Array same size as Picture in px
	static boolean[][] picInBool = new boolean[picture.getDimensions()[0] + 1][picture.getDimensions()[1] + 1];
	
	// Setting
	static boolean verbose = false;
	
	private static Perceptron per = new Perceptron();
	
	public static void main(String[] args) {
		Network n = new Network();
		// simple
		if(!n.loadWeights(folder)) {
			System.err.println("could not load weights because there is no data");
			// halt execution because weights are uninitialized
			// System.exit(1);
		}
		
		Colors c;
		
		int[] dimensions = picture.getDimensions();
		
		Picture newPic = new Picture(dimensions[0], dimensions[1]);
		
		System.out.println("Printing new image ...");
		
		for(int y = 0; y < dimensions[0]; y++) {
			
			for(int x = 0; x < dimensions[1]; x++) {
				
				Color col = new Color(picture.getRGBOf(y, x));
				c = n.evaluate(col);
				
				/**
				 * m = y2 - y1 / x2 -x1
				 * m = - dimensions[1] / - dimensions [0]
				 */
				// f(y,newPic) < x // queer line
				if(true) {
					if(c == Colors.RED) {
						newPic.setPixel(y, x, 255, 0, 0);
					} else if(c == Colors.GREEN) {
						newPic.setPixel(y, x, 0, 255, 0);
					} else if(c == Colors.BLUE) {
						newPic.setPixel(y, x, 0, 0, 255);
					} else if(c == Colors.YELLOW) {
						newPic.setPixel(y, x, 255, 255, 0);
					} else if(c == Colors.WHITE) {
						newPic.setPixel(y, x, 255, 255, 255);
					} else {
						newPic.setPixel(y, x, 0, 0, 0);
					}
					if(verbose) {
						System.out.println(" change ");
					}
					continue;
				} else {
					newPic.setPixel(y, x, col.r, col.g, col.b);
					if(verbose) {
						System.out.println(" keep ----- ");
					}
					
				}
				
			}
			
		}
		newPic.saveImage();
		
		// simple
		n.saveWeights(folder);
	}
	
	/**
	 * function for line from 0,0 corner to max,max corner
	 * @param x
	 * @return
	 */
	private static double f(int x, Picture p) {
		
		/**
		 * m = y2 - y1 / x2 -x1
		 * m = dimensions[1] - 0 / dimensions [0] - 0
		 * m =
		 */
		
		int[] dimensions = p.getDimensions();
		
		double raw = ((double) dimensions[1]) / ((double) dimensions[0]);
		return raw * x;
		
	}
	
	public static void printOutputArray() {
		// Picture dimensions
		int dimensions[] = picture.getDimensions();
		
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("\n");
		
		for(int i = 0; i <= dimensions[1]; i++) {
			
			for(int j = 0; j <= dimensions[0]; j++) {
				System.out.print(picInBool[j][i] ? "1" : "0");
			}
			System.out.print("\n");
		}
	}
	
	public static void printOutputToFile() {
		try {
			File myObj = new File("output.txt");
			
			if(myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
			
			FileWriter myWriter = new FileWriter("output.txt");
			
			int dimensions[] = picture.getDimensions();
			
			for(int i = 0; i <= dimensions[1]; i++) {
				
				for(int j = 0; j <= dimensions[0]; j++) {
					myWriter.write(picInBool[j][i] ? "1" : "0");
				}
				myWriter.write("--\n");
			}
			
			myWriter.write(":\n");
			
			myWriter.write("End");
			
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
			
		} catch(IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
}
