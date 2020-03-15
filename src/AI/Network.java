/**
 * 
 */
package AI;

import PicSerialsization.Color;

/**
 * @author ericmiddelhove
 */

public class Network {
	
	// Neurons
	private Perceptron redP = new Perceptron();
	private Perceptron greenP = new Perceptron();
	private Perceptron blueP = new Perceptron();
	private Perceptron yellowP = new Perceptron();
	private Perceptron whiteP = new SimilarlyPerceptron();
	
	/**
	 * initializing neural network and trains it with picture preset datas
	 */
	public Network() {
		
		System.out.println("training red perceptron");
		redP.trainFromPicture("src/Training Data/RED.jpg", "src/Training Data/NOTRED.jpg");
		
		System.out.println("training green perceptron");
		greenP.trainFromPicture("src/Training Data/GREEN.jpg", "src/Training Data/NOTGREEN.jpg");
		
		System.out.println("training blue perceptron");
		blueP.trainFromPicture("src/Training Data/BLUE.jpg", "src/Training Data/NOTBLUE.jpg");
		
		System.out.println("training yellow perceptron");
		yellowP.trainFromPicture("src/Training Data/YELLOW.jpeg", "src/Training Data/NOTYELLOW.jpg");
		
		System.out.println("training white perceptron");
		whiteP.trainFromPicture("src/Training Data/WHITE.jpeg", "src/Training Data/NOTWHITE.jpg");
		
	}
	
	/**
	 * evaluates a given Color of its main component (red green blue)
	 * @param pixelValue color which should be analyzed
	 * @return Perceptron.Color representing the main component
	 */
	public AI.Perceptron.Color evaluate(Color pixelValue) {
		
		// Black or white
		
		if(whiteP.guess(pixelValue.getColorData()) == 1) {
			
			return Perceptron.Color.WHITE;
			
		}
		
		// only if not black or white
		
		// Color guessing
		float redStat = (float) redP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.RED);
		float greenStat = (float) greenP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.GREEN);
		float blueStat = (float) blueP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.BLUE);
		float yellowStat = (float) yellowP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.YELLOW);
		
		// Red guessing and saving
		float val = (float) redP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.RED);
		Status red = new Status(Perceptron.Color.RED, val);
		
		// green guessing and saving
		val = (float) greenP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.GREEN);
		Status green = new Status(Perceptron.Color.GREEN, val);
		
		// blue guessing and saving
		val = (float) blueP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.BLUE);
		Status blue = new Status(Perceptron.Color.BLUE, val);
		
		// yellow guessing and saving
		val = (float) yellowP.guessAnalog(pixelValue.getColorData(), Perceptron.Color.YELLOW);
		Status yellow = new Status(Perceptron.Color.YELLOW, val);
		
		// saving all guessing status data into an array so we can sort it
		Status[] guesses = {red, green, blue, yellow};
		
		// sorting it by value, bigges first, simple bubblesort
		for(int j = 0; j < guesses.length; j++) {
			for(int i = 0; i < (guesses.length - 1); i++) {
				
				if(guesses[i].value < guesses[i + 1].value) {
					Status tmp = guesses[i];
					guesses[i] = guesses[i + 1];
					guesses[i + 1] = tmp;
				}
				
			}
		}
		
		// Returning color of biggest element
		return guesses[0].color;
	}
	
	private class Status {
		
		AI.Perceptron.Color color;
		float value;
		
		Status(AI.Perceptron.Color color, float value) {
			
			this.color = color;
			this.value = value;
			
		}
		
	}
}
