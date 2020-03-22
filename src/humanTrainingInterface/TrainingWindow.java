/**
 * 
 */
package humanTrainingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import AI.Network;
import PicSerialsization.Picture;
import dataModels.Color;
import dataModels.RGBColor;

/**
 * @author ericmiddelhove
 * license = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class TrainingWindow implements ActionListener{
	
	private JFrame frame = new JFrame();
	
	private JButton redButton = new JButton();
	private JButton orangeButton = new JButton();
	private JButton yellowButton = new JButton();
	private JButton greenButton = new JButton();
	private JButton blueButton = new JButton();
	private JButton purpleButton = new JButton();
	private JButton correctButton = new JButton();
	
	private JLabel imageContainer = new JLabel();
	private JLabel colorGuess = new JLabel("Placeholder");
	private ImageIcon colorImg = new ImageIcon();	
	
	private RGBColor guess; 
	private Network network; 
	
	
	private Color displayedColor; 
	
	private ControlWindow w2;
	
	public TrainingWindow(Color c) {
		this.network = new Network();

		displayedColor = c;
		
		Picture p = new Picture(300,300);
		p.setAllPixelsTo(c);
		BufferedImage image = p.getBufferedImage();
		colorImg.setImage(image);
		
		imageContainer.setBounds(50, 50, 250, 250);
		imageContainer.setIcon(colorImg);
		
		colorGuess.setBounds(50,310,300,20);
		
		
		redButton.setBounds(50, 340, 100, 50);
		redButton.setText("RED");
		redButton.addActionListener(this);
		
		orangeButton.setBounds(200,340, 100, 50);
		orangeButton.setText("ORANGE");
		orangeButton.addActionListener(this);
		
		yellowButton.setBounds(50,400, 100, 50);
		yellowButton.setText("YELLOW");
		yellowButton.addActionListener(this);
		
		greenButton.setBounds(200,400, 100, 50);
		greenButton.setText("GREEN");
		greenButton.addActionListener(this);
		
		blueButton.setBounds(50,460, 100, 50);
		blueButton.setText("BLUE");
		blueButton.addActionListener(this);
		
		purpleButton.setBounds(200,460, 100, 50);
		purpleButton.setText("PURPLE");
		purpleButton.addActionListener(this);
		
		correctButton.setBounds(50,520, 250, 50);
		correctButton.setText("CORRECT/ UNDEFINED");
		correctButton.addActionListener(this);
		
		frame.add(colorGuess);
		frame.setSize(350, 600);
		frame.add(imageContainer);
		
		frame.add(orangeButton);
		frame.add(redButton);
		frame.add(yellowButton);
		frame.add(greenButton);
		frame.add(blueButton);
		frame.add(purpleButton);
		frame.add(correctButton);
		
		frame.setLayout(null);
		frame.setVisible(true);
		
		w2 = new ControlWindow(network);

		
		reload();
		

	}
	
	/**
	 * Sets text of guess value
	 * 
	 * @param s Content string
	 */
	public void setColorGuesLabel(String s) {
		colorGuess.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		RGBColor correct = null;
		RGBColor guess = null;
		
		if(e.getSource() == redButton) {
			correct = RGBColor.RED;
		}else if(e.getSource() == orangeButton){
			
			correct = RGBColor.ORANGE;

		}else if(e.getSource() == yellowButton){
			
			correct = RGBColor.YELLOW;

		}else if(e.getSource() == greenButton){
			
			correct = RGBColor.GREEN;

		}else if(e.getSource() == blueButton){
			
			correct = RGBColor.BLUE;

		}else if(e.getSource() == purpleButton){
			
			correct = RGBColor.PURPLE;

		}else {
			System.err.println("CORRECT / UNDEFINDED");
			reload();
			return;
		}

		network.trainAll(correct, displayedColor.getColorData());
		network.saveWeights();
		reload();
	}
	
	public void reload(){
		Color c = new Color(getRandom() ,getRandom() , getRandom());
		displayedColor = c;
		System.out.println("R: " + c.r + " G: " + c.g + " B: " + c.b);
		
		Picture p = new Picture(250,250);
		p.setAllPixelsTo(c);
		
		colorImg.setImage(p.getBufferedImage());
		
		String text = "";
		for (RGBColor col : network.evaluateHard(displayedColor)) {
			text += col.name() + " ";
		}
		
		colorGuess.setText(text);
		frame.repaint();
		
		w2.reloadValues();
	}
	
	private void trainSpecifiedPerceptron(RGBColor correct, RGBColor guess) {
		
		/**
		switch(correct) {
			case BLACK: network.blackP.train(-1, 1, displayedColor.getColorData()); break;
			case WHITE: network.whiteP.train(-1, 1, displayedColor.getColorData()); break;
			case RED: network.redP.train(-1, -1, displayedColor.getColorData()); break;
			case GREEN: network.greenP.train(-1, 1, displayedColor.getColorData()); break;
			case BLUE: network.blueP.train(-1, 1, displayedColor.getColorData()); break;
			case YELLOW: network.yellowP.train(-1,1, displayedColor.getColorData()); break;
			default: break;
		}
		
	
		switch(guess) {
			case BLACK: network.blackP.train(1, -1, displayedColor.getColorData()); break;
			case WHITE: network.whiteP.train(1, -1, displayedColor.getColorData()); break;
			case RED: network.redP.train(1, -1, displayedColor.getColorData()); break;
			case GREEN: network.greenP.train(1, -1, displayedColor.getColorData()); break;
			case BLUE: network.blueP.train(1, -1, displayedColor.getColorData()); break;
			case YELLOW: network.yellowP.train(1,-1, displayedColor.getColorData()); break;
			default: break;
		}**/
		
	}

	
	
 	public int getRandom() {
		
		return (int) (Math.random() * 1000 % 255);
		
	}
}

