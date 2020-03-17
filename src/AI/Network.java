	/**
 * 
 */
package AI;

import java.io.File;

import PicSerialsization.Color;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */

public class Network {
	

	private Perceptron redP = new Perceptron("redP");
	private Perceptron greenP = new Perceptron("greenP");
	private Perceptron blueP = new Perceptron("blueP");
	private Perceptron yellowP = new Perceptron("yellowP");
	private BWPerceptron whiteP = new BWPerceptron("whiteP");
	
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
		
		System.out.println("training black perceptron");
		blackP.trainFromPicture("src/Training Data/NOTWHITE.jpg","src/Training Data/WHITE.jpeg");


	}
	
	/**
	 * evaluates a given Color of its main component (red green blue)
	 * @param pixelValue color which should be analyzed
	 * @return Perceptron.Color representing the main component
	 */
	public RGBColor evaluate(Color pixelValue) {
		
		// Black or white
		
		if(whiteP.guess(pixelValue.getColorData()) == 1) {
			
			return RGBColor.WHITE;

		}else if(blackP.guess(pixelValue.getColorData()) == 1) {
			
			return RGBColor.BLACK;
			
		}
		
		// only if not black or white
		
		// Color guessing
		float redStat = (float) redP.guessAnalog(pixelValue.getColorData(), RGBColor.RED);
		float greenStat = (float) greenP.guessAnalog(pixelValue.getColorData(), RGBColor.GREEN);
		float blueStat = (float) blueP.guessAnalog(pixelValue.getColorData(), RGBColor.BLUE);
		float yellowStat = (float) yellowP.guessAnalog(pixelValue.getColorData(), RGBColor.YELLOW);
		
		// Red guessing and saving
		float val = (float) redP.guessAnalog(pixelValue.getColorData(), RGBColor.RED);
		Status red = new Status(RGBColor.RED, val);
		
		// green guessing and saving
		val = (float) greenP.guessAnalog(pixelValue.getColorData(), RGBColor.GREEN);
		Status green = new Status(RGBColor.GREEN, val);
		
		// blue guessing and saving
		val = (float) blueP.guessAnalog(pixelValue.getColorData(), RGBColor.BLUE);
		Status blue = new Status(RGBColor.BLUE, val);
		
		// yellow guessing and saving
		val = (float) yellowP.guessAnalog(pixelValue.getColorData(), RGBColor.YELLOW);
		Status yellow = new Status(RGBColor.YELLOW, val);
		
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
	
	/**
	 * ensures that folder exists
	 * @param folder
	 */
	private void ensureFolder(File folder) {
		if(!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
	}
	
	/**
	 * @param folder load folder
	 * @return load succeeded
	 */
	public boolean loadWeights(File folder) {
		boolean result = true;
		ensureFolder(folder);
		
		result &= blueP.loadWeights(folder);
		result &= greenP.loadWeights(folder);
		result &= redP.loadWeights(folder);
		result &= whiteP.loadWeights(folder);
		result &= yellowP.loadWeights(folder);
		
		return result;
	}
	
	/**
	 * @param folder save folder
	 */
	public void saveWeights(File folder, AbstractPerceptron[] perceptrons) {
		ensureFolder(folder);
		
		for(AbstractPerceptron p: perceptrons){
			p.saveWeights(folder);
		}
		/**
		blueP.saveWeights(folder);
		greenP.saveWeights(folder);
		redP.saveWeights(folder);
		yellowP.saveWeights(folder);
		whiteP.saveWeights(folder);**/
	}
	
	private class Status {
		
		RGBColor color;
		float value;
		
		Status(RGBColor color, float value) {
			
			this.color = color;
			this.value = value;
			
		}
		
	}
}
