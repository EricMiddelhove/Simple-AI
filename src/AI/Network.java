package AI;

import java.io.File;
import java.util.ArrayList;

import dataModels.Color;
import dataModels.RGBColor;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class Network {
	
	public static final File FOLDER = new File("weights");

	
	public Perceptron redP = new Perceptron("redP");
	public Perceptron orangeP = new Perceptron("orangeP");
	public Perceptron yellowP = new Perceptron("yellowP");
	public Perceptron greenP = new Perceptron("greenP");
	public Perceptron blueP = new Perceptron("blueP");
	public Perceptron purpleP = new Perceptron("purpleP");
	
	public BWPerceptron whiteP = new BWPerceptron("whiteP");
	public BWPerceptron blackP = new BWPerceptron("blackP");

	private boolean shouldSave = true;
	private boolean shouldLoad = true;

	/**
	 * initializing neural network and trains it with picture preset datas
	 */
	public Network() {
		if(!shouldLoad) { 
			/**
			System.out.println("Have not loaded weights");
			// halt execution because weights are uninitialized
			// System.exit(1);
			System.out.println("training red perceptron");
			redP.trainFromPicture("src/Training Data/RED.jpg", "src/Training Data/NOTRED.jpg");
			
			System.out.println("training green perceptron");
			greenP.trainFromPicture("src/Training Data/GREEN.jpg", "src/Training Data/NOTGREEN.jpg");
			
			System.out.println("training blue perceptron");
			blueP.trainFromTxtFile("src/Training Data/allBlue.txt", "src/Training Data/emptyTxt.txt");
			blueP.trainFromPicture("src/Training Data/BLUE.jpg", "src/Training Data/NOTBLUE.jpg");
			
			System.out.println("training yellow perceptron");
			yellowP.trainFromPicture("src/Training Data/YELLOW.jpeg", "src/Training Data/NOTYELLOW.jpg");
			
			System.out.println("training white perceptron");
			whiteP.trainFromPicture("src/Training Data/WHITE.jpeg", "src/Training Data/NOTWHITE.jpg");
			
			System.out.println("training black perceptron");
			blackP.trainFromPicture("src/Training Data/NOTWHITE.jpg","src/Training Data/WHITE.jpeg");
			*/
		}else {
			loadWeights(FOLDER);
			System.out.println("Successfully loaded weights");
		}

		if(shouldSave) saveWeights(FOLDER, new AbstractPerceptron[] {redP,greenP,blueP,yellowP,whiteP,blackP,orangeP,purpleP});

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
		float orangeStat = (float) orangeP.guessAnalog(pixelValue.getColorData(), RGBColor.ORANGE);
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
		
		if(guesses[0].value < 0.5) {
			System.out.println("Unsure");
		}
		
		// Returning color of biggest element
		return guesses[0].color;
	}
	
	/**
	 * Guesses the color returns colors which it thinks could be
	 * 
	 * @param pixelValue
	 * @return
	 */
	public RGBColor[] evaluateHard(Color pixelValue) {
		
		ArrayList<RGBColor> out = new ArrayList<RGBColor>();
		
		if(redP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.RED);
		}else if(orangeP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.ORANGE);
		}else if(yellowP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.YELLOW);
		}else if(greenP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.GREEN);
		}else if(blueP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.BLUE);
		}else if(purpleP.guess(pixelValue.getColorData()) == 1) {
			out.add(RGBColor.PURPLE);
		}
		
		return out.toArray(new RGBColor[out.size()]);
		
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
		result &= orangeP.loadWeights(folder);
		result &= purpleP.loadWeights(folder);

		
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
	
	/**
	 * 
	 */
	public void trainAll(RGBColor correctAnswer, int[] ins) {
		
		int redStat = -1, orangeStat = -1, yellowStat = -1, greenStat = -1, blueStat = -1, purpleStat = -1 ;
		
		
		switch(correctAnswer) {
			case RED: redStat = 1;
			case ORANGE: orangeStat = 1;
			case YELLOW: yellowStat = 1;
			case GREEN: greenStat = 1;
			case BLUE: blueStat = 1;
			case PURPLE: purpleStat = 1;
			default: break;
		}
		
		
		redP.train(redP.guess(ins), redStat);
		orangeP.train(orangeP.guess(ins), orangeStat);
		yellowP.train(yellowP.guess(ins), yellowStat);
		greenP.train(greenP.guess(ins), greenStat);
		blueP.train(blueP.guess(ins), blueStat);
		purpleP.train(purpleP.guess(ins), purpleStat);
		
	}
	public void saveWeights() {
		saveWeights(FOLDER, new AbstractPerceptron[] {redP,greenP,blueP,yellowP,whiteP,blackP,orangeP,purpleP});
		System.out.println("Saved weights");

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
