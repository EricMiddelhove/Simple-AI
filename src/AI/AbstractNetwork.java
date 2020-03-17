package AI;

import java.io.File;

public abstract class AbstractNetwork {
	
	protected abstract AbstractPerceptron[] getCandidatesForPersistence();
	
	/**
	 * ensures that the folder exists
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
		
		for(AbstractPerceptron perceptron : getCandidatesForPersistence()) {
			result &= perceptron.loadWeights(folder);
		}
		
		return result;
	}
	
	/**
	 * @param folder save folder
	 */
	public void saveWeights(File folder) {
		ensureFolder(folder);
		
		for(AbstractPerceptron perceptron : getCandidatesForPersistence()) {
			perceptron.saveWeights(folder);
		}
	}
	
}
