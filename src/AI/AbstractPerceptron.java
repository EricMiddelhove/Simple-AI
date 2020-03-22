/**
 * 
 */
package AI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dataModels.Color;
import dataModels.RGBColor;
import dataModels.Weights;
import pictureSerialization.Picture;
import txtSerialization.ColorData;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public abstract class AbstractPerceptron {
	
	// if a perceptron has no id it cannot be saved
	public String id;
	public double[] inputs;
	public Weights weights;
	
	public AbstractPerceptron() {}
	
	public AbstractPerceptron(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public static double LEARNING_RATE = 0.002;
	
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
	
	public void train(int guess, int target, int[] ins) {
		inputs[0] = ins[0];
		inputs[1] = ins[1];
		inputs[2] = ins[2];
		
		train(guess, target);
	}
	
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
	
	/**
	 * Trains the perceptron with a txt file where each line is a training data
	 * Each line must be formattetd like this: <b>rgb(int,int,int)<b>
	 * @param truePath path to txt file
	 */
	public void trainFromTxtFile(String truePath, String falsePath) {
		Color[] trueData = new ColorData(truePath).getColors();
		Color[] falseData = new ColorData(falsePath).getColors();
		
		for(int i = 0; (i < trueData.length) || (i < falseData.length); i++) {
			if(i < trueData.length) {
				train(this.guess(trueData[i].getColorData()), 1);
			}
			if(i < falseData.length) {
				train(this.guess(falseData[i].getColorData()), 1);
			}
		}
	}
	
	private void ensureId() {
		if(id == null) {
			throw new RuntimeException("Perceptron has no id and cannot be saved/loaded");
		}
	}
	
	public void saveWeights(File folder) {
		ensureId();
		
		File apropFile = new File(folder, getId() + ".weightsave");
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(apropFile))) {
			oos.writeObject(weights);
		} catch(IOException e) {
			// oooh something happened
			e.printStackTrace();
		}
	}
	
	public boolean loadWeights(File folder) {
		ensureId();
		
		File apropFile = new File(folder, getId() + ".weightsave");
		if(apropFile.exists() && apropFile.isFile()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(apropFile))) {
				weights = (Weights) ois.readObject();
				return true;
			} catch(ClassNotFoundException e) {
				// this should (absolutely) not happen with the current implementation
				e.printStackTrace();
			} catch(IOException e) {
				// oooh something happened
				e.printStackTrace();
			}
		}
		return false;
	}
}
