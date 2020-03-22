package dataModels;

import java.io.Serializable;
/**
 * 
 * @author Fabian Gabriel
 * license = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class Weights implements Serializable {
	
	private static final long serialVersionUID = -2259896498971209419L;
	
	private double[] weights;
	
	public Weights(double[] weights) {
		this.weights = weights;
	}
	
	public int length() {
		return weights.length;
	}
	
	public void setWeight(int i, float value) {
		weights[i] = value;
	}
	
	public void setWeight(int i, double value) {
		weights[i] = value;
	}
	
	public double getWeight(int i) {
		return weights[i];
	}
	
	public double[] getWeights() {
		return weights;
	}
	
}
