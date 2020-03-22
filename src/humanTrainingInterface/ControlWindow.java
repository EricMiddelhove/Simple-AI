/**
 * 
 */
package humanTrainingInterface;

import java.awt.ComponentOrientation;

import javax.swing.JFrame;
import javax.swing.JLabel;

import AI.Network;

/**
 * @author ericmiddelhove
 * license = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class ControlWindow {

	JFrame frame = new JFrame();
	
	JLabel red[] = new JLabel[4];
	JLabel orange[] = new JLabel[4];
	JLabel yellow[] = new JLabel[4];
	JLabel green[] = new JLabel[4];
	JLabel blue[] = new JLabel[4];
	JLabel purple[] = new JLabel[4];
	Network network; 
	
	
	public ControlWindow(Network n){
		this.network = n;
		frame.setBounds(500, 0, 610, 350);
		
		for(int i = 0; i < red.length; i++) {
			
			red[i] = new JLabel();
			red[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			red[i].setBounds(30, i * 20 + 5, 200, 50);
			frame.add(red[i]);
			
		}
		
		for(int i = 0; i < red.length; i++) {
			
			orange[i] = new JLabel();
			orange[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			orange[i].setBounds(230, i * 20 + 5, 200, 50);
			frame.add(orange[i]);
			
		}
		
		for(int i = 0; i < red.length; i++) {
			
			yellow[i] = new JLabel();
			yellow[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			yellow[i].setBounds(430, i * 20 + 5, 200, 50);
			frame.add(yellow[i]);
			
		}
		
		for(int i = 0; i < red.length; i++) {
			
			green[i] = new JLabel();
			green[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			green[i].setBounds(30, i * 20 + 5 + 125, 200, 50);
			frame.add(green[i]);
			
		}
		
		for(int i = 0; i < red.length; i++) {
			
			blue[i] = new JLabel();
			blue[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			blue[i].setBounds(230, i * 20 + 5 + 125, 200, 50);
			frame.add(blue[i]);
			
		}
		
		for(int i = 0; i < red.length; i++) {
			
			purple[i] = new JLabel();
			purple[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			purple[i].setBounds(430, i * 20 + 5 + 125, 200, 50);
			frame.add(purple[i]);
			
		}
		
		frame.setLayout(null);
		frame.setVisible(true);
		reloadValues();
	}
	
	public void reloadValues() {
		// set Titles 
		red[0].setText("RED");
		orange[0].setText("ORANGE");
		yellow[0].setText("YELLOW");
		green[0].setText("GREEN");
		blue[0].setText("BLUE");
		purple[0].setText("PURPLE");
		
		//Set values
		red[1].setText( "R: " + network.redP.weights.getWeight(0));
		red[2].setText( "G: " + network.redP.weights.getWeight(1));
		red[3].setText( "B: " + network.redP.weights.getWeight(2));
		
		yellow[1].setText( "R: " + network.yellowP.weights.getWeight(0));
		yellow[2].setText( "G: " + network.yellowP.weights.getWeight(1));
		yellow[3].setText( "B: " + network.yellowP.weights.getWeight(2));
		
		green[1].setText( "R: " + network.greenP.weights.getWeight(0));
		green[2].setText( "G: " + network.greenP.weights.getWeight(1));
		green[3].setText( "B: " + network.greenP.weights.getWeight(2));
		
		blue[1].setText( "R: " + network.blueP.weights.getWeight(0));
		blue[2].setText( "G: " + network.blueP.weights.getWeight(1));
		blue[3].setText( "B: " + network.blueP.weights.getWeight(2));
	
		purple[1].setText( "R: " + network.purpleP.weights.getWeight(0));
		purple[2].setText( "G: " + network.purpleP.weights.getWeight(1));
		purple[3].setText( "B: " + network.purpleP.weights.getWeight(2));
		
		orange[1].setText( "R: " + network.orangeP.weights.getWeight(0));
		orange[2].setText( "G: " + network.orangeP.weights.getWeight(1));
		orange[3].setText( "B: " + network.orangeP.weights.getWeight(2));
	}
	
}
