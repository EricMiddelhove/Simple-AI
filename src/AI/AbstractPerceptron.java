/**
 * 
 */
package AI;

import PicSerialsization.Picture;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public abstract class AbstractPerceptron {
	
	public double[] inputs;
	public Weights weights;
	
	public static double LEARNING_RATE = 0.2;
	
	/**
	 * Ask Perceptron if it is the coller it is trained for only use after training
	 * else NullPointerException
	 * @param inputs colorData {r,g,b}
	 * @return 1 || -1 = true || false
	 */
	public abstract int guess(double[] inputs);
	
	/**
	 * Ask Perceptron if it is the coller it is trained for only use after training
	 * else NullPointerException
	 * @param inputs colorData {r,g,b}
	 * @return 1 || -1 = true || false
	 */
	public int guess(int[] inputs) {
		
		double[] newInputs = new double[inputs.length];
		for(int i = 0; i < inputs.length; i++) {
			newInputs[i] = inputs[i];
		}
		
		return guess(newInputs);
	}
	
	public abstract double guessAnalog(int[] js, RGBColor color);
	
	public abstract void train(int guess, int target);
	
	/**
	 * Trains the perceptron with a jpg where each pixel is a training data
	 * @param truePath
	 * @param falsePath
	 */
	public void trainFromPicture(String truePath, String falsePath) {
		
		Picture correct = new Picture(truePath);
		Picture wrong = new Picture(falsePath);
		
		int[] correctDimensions = correct.getDimensions();
		int[] falseDimensions = wrong.getDimensions();
		
		if(Main.verbose) {
			System.out.println("Dimensions: x:" + correctDimensions[0] + " y: " + correctDimensions[1]);
		}
		
		for(int i = 0; i < correctDimensions[1]; i++) {
			for(int j = 0; j < correctDimensions[0]; j++) {
				
				if(Main.verbose) {
					System.out.println("Trained coordinate i: " + i + "j: " + j);
				}
				
				int[] negRGBVal = {}, rgbValue = {};
				// Take RGB Value of current pixel
				if((j <= correctDimensions[0]) && (i <= correctDimensions[1])) {
					rgbValue = correct.getRGBOf(j, i);
				} else {
					return;
				}
				
				if((j <= falseDimensions[0]) && (i <= falseDimensions[1])) {
					negRGBVal = wrong.getRGBOf(j, i);
				} else {
					return;
				}
				
				// Tell AI that this is red
				train(guess(rgbValue), 1);
				train(guess(negRGBVal), -1);
				
			}
		}
		
	}
}
