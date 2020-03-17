/**
 * 
 */
package AI;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */

public class BWPerceptron extends AbstractPerceptron {
	
	public BWPerceptron(String id) {
		super(id);
	}
	
	int power = 300; // Describes all inputs added; Bigger than thsi value = white else black
	int variety = 7; // Describes difference between average input and actual input
	
	@Override
	public int guess(int[] ins) {
		
		inputs = new double[ins.length];
		
		for(int i = 0; i < ins.length; i++) {
			
			inputs[i] = ins[i];
			
		}
		
		return this.guess(inputs);
		
	}
	
	@Override
	public void train(int guess, int answer) {
		
		if(guess != answer) {
			
			// get average input
			int sum = 0;
			for(double i : inputs) {
				sum += i;
			}
			int avg = sum / inputs.length;
			
			// Getting average variety
			int sum2 = 0;
			for(double i : inputs) {
				sum2 += (i - avg);
			}
			int avgVar = sum2 / inputs.length;
			
			// Adjusting variety
			variety += avgVar * LEARNING_RATE;
			
			// getting power level & adjusting it
			power += (sum - power) * LEARNING_RATE;
			
			if(Main.verbose) {
				System.out.println("Power: " + power);
				System.out.println("Variety" + variety);
			}
		}
	}
	
	@Override
	public int guess(double[] inputs) {
		// TODO Auto-generated method stub
		
		super.inputs = inputs;
		// Sum up inputs
		
		int sum = 0;
		for(double i : this.inputs) {
			sum += i;
		}
		
		if(((sum / this.inputs.length) >= (this.inputs[0] - variety)) && ((sum / this.inputs.length) <= (this.inputs[0] + variety))) {
			// Possibility of beeing white if power is high enaough
			
			if(sum >= power) {
				return 1;
			}
			
		}
		
		return -1;
	}
	
	@Override
	public double guessAnalog(int[] js, RGBColor c) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
