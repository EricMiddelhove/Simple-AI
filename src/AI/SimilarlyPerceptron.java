/**
 * 
 */
package AI;

import PicSerialsization.Picture;

/**
 * @author ericmiddelhove
 * license = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class SimilarlyPerceptron extends Perceptron{
	
	int power = 300; //Describes all inputs added; Bigger than thsi value = white else black
	int variety = 7; //Describes difference between average input and actual input
	
	static int[] ins;
	
	@Override
	public int guess(int[] ins) {
		this.ins = ins;
		
		//Sum up inputs
		
		int sum = 0;
		for(int i : ins) sum += i;		
		
		if(sum / ins.length >= ins[0] - variety && sum / ins.length <= ins[0] + variety) {
			//Possibility of beeing white if power is high enaough
			
			if(sum >= power) return 1;
			
		}
		return -1;
		
	}
	
	public void train(int guess, int answer) {
		
		if(guess != answer) {
			
			//get average input
			int sum = 0;
			for(int i : ins) sum += i;
			int avg = sum / ins.length;
			
			//Getting average variety
			int sum2 = 0;
			for(int i : ins) {
				sum2 += (i - avg);
			}
			int avgVar = sum2 / ins.length;
			
			//Adjusting variety
			variety += avgVar * super.LEARNING_RATE;
			
			//getting power level & adjusting it
			power += (sum - power) * super.LEARNING_RATE;
			
			System.out.println("Power: " + this.power);
			System.out.println("Variety" + this.variety);

		}
	}
	
	public void trainFromPicture(String truePath, String falsePath) {

		Picture correct = new Picture(truePath);
		Picture wrong = new Picture(falsePath);

		int[] correctDimensions = correct.getDimensions();
		int[] falseDimensions = wrong.getDimensions();

		if (Main.verbose)
			System.out.println("Dimensions: x:" + correctDimensions[0] + " y: " + correctDimensions[1]);

		for (int i = 0; i < correctDimensions[1] ||i < falseDimensions[1]; i++) {
			for (int j = 0; j < correctDimensions[0] ||j < falseDimensions[0]; j++) {

				if (Main.verbose)
					System.out.println("Trained coordinate i: " + i + "j: " + j);

				int[] negRGBVal = {}, rgbValue = {};
				// Take RGB Value of current pixel checking if it exists in the picture
				if (j <= correctDimensions[0] && i <= correctDimensions[1]) {
					rgbValue = correct.getRGBOf(j, i);
					this.train(this.guess(rgbValue), 1);
				} else {
					return;
				}

				if (j <= falseDimensions[0] && i <= falseDimensions[1]) {
					negRGBVal = wrong.getRGBOf(j, i);
					this.train(this.guess(negRGBVal), -1);
				} else {
					return;
				}

				// Tell AI that this is red

			}
			

			
		}
	}
	
}
