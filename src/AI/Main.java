package AI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import PicSerialsization.Color;
import PicSerialsization.Picture;

/**
 * @author ericmiddelhove
 */
public class Main {

	static Perceptron p = new Perceptron();	

	//Training data
	static Picture red = new Picture("src/Training Data/RED.jpg");
	static Picture notRed = new Picture("src/Training Data/NOTRED.jpg");

	//analyzing data
	static Picture picture = new Picture("src/Source img Data/mon3.jpeg");
	
	//Output data | Array same size as Picture in px
	static boolean [][] picInBool = new boolean[picture.getDimensions()[0] + 1][picture.getDimensions()[1] + 1]; 

	//Setting
	static boolean verbose = false;
	
	private static Perceptron per = new Perceptron();

	public static void main(String[] args) {
		
		Network n = new Network();
		
		Perceptron.Color c;
		
		
		int [] dimensions = picture.getDimensions();
		
		Picture newPic = new Picture(dimensions[0], dimensions[1]);
		
		System.out.println("Printing new image ...");
		
		for(int y = 0; y < dimensions[0]; y++) {
			
			for(int x = 0; x < dimensions[1]; x++) {
				
				
				Color col = new Color(picture.getRGBOf(y,x));
				c = n.evaluate(col);
				
				/**
				 * 
				 * m = y2 - y1 / x2 -x1
				 * m = - dimensions[1] / - dimensions [0]
				 * 
				 * 
				 */
				//f(y,newPic) < x // queer line
				if(true) {
					if(c == Perceptron.Color.RED) {
						newPic.setPixel(y, x, 255, 0, 0);
					}else if(c == Perceptron.Color.GREEN) {
						newPic.setPixel(y, x, 0, 255, 0);
					}else if(c == Perceptron.Color.BLUE) {
						newPic.setPixel(y, x, 0, 0, 255);
					}else if(c == Perceptron.Color.YELLOW) {
						newPic.setPixel(y, x, 255, 255, 0);
					}else {
						newPic.setPixel(y, x, 0, 0, 0);
					}
					if(verbose) System.out.println(" change ");
					continue;
				}else {
					newPic.setPixel(y,x,col.r,col.g,col.b);
					if(verbose) System.out.println(" keep ----- ");

				}
				
			}
			
		}
		newPic.saveImage();
		
	}
	
	
	/**
	 * 
	 * function for line from 0,0 corner to max,max corner
	 * 
	 * @param x
	 * @return
	 */
	private static double f(int x, Picture p) {
		
		/**
		 *
		 * m = y2 - y1 / x2 -x1
		 * m = dimensions[1] - 0 / dimensions [0] - 0
		 * m = 
		 * 
		 */
		
		int [] dimensions = p.getDimensions();		
		
		double raw = ((double) dimensions[1]) / ((double) dimensions[0]);
		return raw * x;
		
		
	}
	
	public static void changeColor() {
		int[] dimensions = picture.getDimensions();
		
		Picture newPic = new Picture(dimensions[0],dimensions[1]);

		trainDemo();
		
		for(int i = 0; i < dimensions[1]; i++) {
			for(int j = 1; j < dimensions[0]; j++) {
					
				/**
				 * Für jeden pixel des alten bildes:
				 * 	1. Prüfen ob Rot
				 * 		Ja -> durch grün ersetzen
				 * 		Nein -> Übertragen
				 */
					
				//Holen uns Farbdaten und speichern sie in Double Array
				double[] currentColorData = new double[3];
				int[] currentColorInt = picture.getRGBOf(j,i);
					
					
				for(int x = 0; x < currentColorInt.length; x++) {
					currentColorData[x] = currentColorInt[x];
				}
					
					
				if(per.isRed(currentColorData)) {
					newPic.setPixel(j,i,currentColorInt[0],currentColorInt[1],currentColorInt[2]);
				}else {
					newPic.setPixel(j,i,255,255,255);
				}
			}
		}	
		newPic.saveImage();
		
	}
	
	public static void trainDemo() {

		System.out.println("\n\nTraining ...");
		
		if (verbose) System.out.println("x: " + red.getDimensions()[0]);
		if (verbose) System.out.println("y: " + red.getDimensions()[1]);
		// System.out.println(notRed.getDimensions()[1]);

		int[] dimensions = red.getDimensions();

		for (int i = 0; i < 210; i++) {
			for (int j = 0; j < dimensions[0]; j++) {

				// Take RGB Value of current pixel
				int[] rgbValue = red.getRGBOf(j, i);
				int[] negRGBVal = notRed.getRGBOf(j, i);

				// Tell AI that this is red
				per.train(per.guess(rgbValue), 1);
				per.train(per.guess(negRGBVal), -1);

				if(verbose) System.out.println("Trained Pixel " + j + " | " + i);
				
				if (!verbose && j % dimensions[0]-1 == 0) System.out.print(".");
			}
		}
		
		if(verbose) {
			System.out.println("\nFinal weights: ");
			for (double il : per.weights) {
				System.out.println(il);
			}
	
			System.out.println("\nTotal Trained Datasets: " + (dimensions[0] * 210 * 2));
	
			System.out.println("\nFinal Guess: ");
			System.out.println("\nValues: 135, 255, 153 (Green)");
			System.out.println(per.guess(new double[] { 135, 255, 153 }));
	
			System.out.println("\nValues: 168, 36, 36 (Red)");
			System.out.println(per.guess(new double[] { 168, 36, 36 }));
		}

		// per.weights = null;
	}

	public static void guessRed() {

		System.out.println("\n\nGuessing ...");
		
		int[] dimensions = picture.getDimensions();

		int y = 0, n = 0;

		try {

			for (int i = 0; i < dimensions[1]; i++) {

				for (int j = 0; j < dimensions[0]; j++) {

					double[] val = new double[3];

					val[0] = (double) picture.getRGBOf(j, i)[0];
					val[1] = (double) picture.getRGBOf(j, i)[1];
					val[2] = (double) picture.getRGBOf(j, i)[2];

					picInBool[j][i] = per.isRed(val);

					
					if(verbose) System.out.print("r: " + val[0] + " g: " + val[1] + " b: " + val[2]);

					if (per.isRed(val)) {
						if (verbose) System.out.print(" |yes| x: " + j + " y: " + i + "\n");
						
						y++;
					} else {
						if (verbose) System.out.println("\n");
						n++;
					}

					if (!verbose && j % dimensions[0]-1 == 0) System.out.print(".");
				}
				
			}			
			
			System.out.println("\nYes: " + y + " No: " + n);

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error");

			System.out.println("\nYes: " + y + " No: " + n);

		}
	}

	public static void printOutputArray() {
		//Picture dimensions 
		int dimensions[] = picture.getDimensions(); 
		
		
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("\n");

		
		for(int i = 0; i <= dimensions[1]; i++) {
			
			for(int j = 0; j <= dimensions[0]; j++) {
				System.out.print(picInBool[j][i]? "1" : "0");
			}
			System.out.print("\n");
		}
	}
	
	public static void printOutputToFile() {
		try {
			File myObj = new File("output.txt");
			
		    if (myObj.createNewFile()) {
		    	System.out.println("File created: " + myObj.getName());
		    } else {
		    	System.out.println("File already exists.");
		    }
		    
		    FileWriter myWriter = new FileWriter("output.txt");
		    
			int dimensions[] = picture.getDimensions(); 

		    
		    for(int i = 0; i <= dimensions[1]; i++) {
				
				for(int j = 0; j <= dimensions[0]; j++) {
					myWriter.write(picInBool[j][i]? "1" : "0");
				}
				myWriter.write("--\n");
			}
		    
			myWriter.write(":\n");

		    
		    myWriter.write("End");
		    
		    
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		    
		    
		    
		    
		    
		 } catch (IOException e) {
			 System.out.println("An error occurred.");
		     e.printStackTrace();
		 }
	}
	
}
