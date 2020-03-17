package AI;

import PicSerialsization.Picture;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */

public class Perceptron extends AbstractPerceptron {
	
	/**
	 * initialize Perceptron with preinstallt weight
	 * @param weights weightObject
	 */
	public Perceptron(String id, Weights weights) {
		super(id);
		super.weights = weights;
	}
	
	/**
	 * Initialize untrained perceptron
	 */
	public Perceptron(String id) {
		super(id);
		
		// TODO:Initialize weights with random values
		
	}
	
	public Perceptron() {}
	
	@Override
	public int guess(double[] ins) {
		
		inputs = new double[ins.length + 1];
		
		// Zuweisung der Inputs auf das einen längere Array
		for(int i = 0; i < ins.length; i++) {
			inputs[i] = ins[i];
		}
		
		// Zuweisung des 0 Korrektur Inputs -> Falls alle Inputs 0 sind
		inputs[inputs.length - 1] = 1;
		
		// Initialize weight if neccessary
		if(weights == null) {
			
			weights = new Weights(new double[inputs.length]);
			
			for(int i = 0; i < weights.length(); i++) {
				
				double rand = Math.random() * 10;
				double sign = (((int) (Math.random() * 10) % 2) == 0) ? -1 : 1;
				
				double realRand = (rand * sign) / 10;
				
				if(Main.verbose) {
					System.out.println("realRand " + i + " : " + realRand);
				}
				
				weights.setWeight(i, (float) realRand);
			}
			
		}
		
		// check if input amount is correct
		if(inputs.length != weights.length()) {
			System.out.println("input array has wrong size");
			return 0;
		}
		
		float sum = 0;
		// Sum inputs and weights
		for(int i = 0; i < inputs.length; i++) {
			
			sum += (inputs[i] * weights.getWeight(i));
			
		}
		
		// activation
		if(sum >= 0) {
			return +1;
		}
		return -1;
		
	}
	
	/**
	 * Returns value between 0-1 how much ist thinks it is a hit
	 * @param inputs RGB Array
	 * @return value between 0-1 1 = hit 0 = no hit
	 */
	@Override
	public double guessAnalog(int[] js, RGBColor color) {
		
		// Sum up mechanism {
		inputs = new double[js.length + 1];
		
		// Zuweisung der Inputs auf das einen längere Array
		for(int i = 0; i < js.length; i++) {
			inputs[i] = js[i];
		}
		
		// Zuweisung des 0 Korrektur Inputs -> Falls alle Inputs 0 sind
		inputs[inputs.length - 1] = 1;
		
		// Initialize weight if neccessary
		if(weights == null) {
			
			weights = new Weights(new double[inputs.length]);
			
			for(int i = 0; i < weights.length(); i++) {
				
				double rand = Math.random() * 10;
				double sign = (((int) (Math.random() * 10) % 2) == 0) ? -1 : 1;
				
				double realRand = (rand * sign) / 10;
				
				if(Main.verbose) {
					System.out.println("realRand " + i + " : " + realRand);
				}
				
				weights.setWeight(i, (float) realRand);
			}
			
		}
		
		// check if input amount is correct
		if(inputs.length != weights.length()) {
			System.out.println("input array has wrong size");
			return 0;
		}
		
		float sum = 0;
		// Sum inputs and weights
		for(int i = 0; i < inputs.length; i++) {
			
			sum += (inputs[i] * weights.getWeight(i));
			
		}
		// } End sum up mechanism
		
		if(sum < 0) {
			return 0;
		} else {
			
			// Prozentuale Aufteilung zwischen 0 und 100% Rot
			// Array Values: {red, green, blue, 0 catch}
			
			inputs = new double[] {0, 0, 0, 1};
			System.arraycopy(color.get(), 0, inputs, 0, 3);
			
			// Guessing for 100% value
			float ground = 0;
			for(int i = 0; i < inputs.length; i++) {
				ground += inputs[i] * weights.getWeight(i);
			}
			
			// percentage p = w * 100 / g
			// down to 1 instead of 100 p = w/g
			// w = sum; g = ground
			
			return sum / ground;
			
		}
		
	}
	
	/**
	 * Executes one trainingProcess
	 * @param guess
	 * @param target
	 */
	@Override
	public void train(int guess, int target) {
		int error = target - guess;
		
		if(Main.verbose) {
			System.out.println("Guess: 					" + guess);
		}
		for(int i = 0; i < weights.length(); i++) {
			weights.setWeight(i, weights.getWeight(i) + (error * inputs[i] * LEARNING_RATE));
			if(Main.verbose) {
				System.out.println("Input: " + inputs[i] + " | Weight " + i + " :" + weights.getWeight(i) + " diff: "
						+ (error * inputs[i] * LEARNING_RATE));
			}
		}
		
	}
	
	/**
	 * Trains the perceptron with a jpg where each pixel is a training data
	 * @param truePath
	 * @param falsePath
	 */
	@Override
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
				train(this.guess(rgbValue), 1);
				train(this.guess(negRGBVal), -1);
				
			}
		}
	}
	
}
