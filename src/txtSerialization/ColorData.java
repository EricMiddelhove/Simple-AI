/**
 * 
 */
package txtSerialization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import dataModels.Color;

/**
 * @author ericmiddelhove
 * license = CC-BY-SA-NC
 * http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class ColorData {
	
	public static String path = "";
	public static Color[] colors; 
	
	private static ArrayList<Color> list = new ArrayList<Color>();
	
	/**
	 * Initializes a new ColorData Object 
	 * after initialization data will be in <b> colors </b> array
	 * 
	 * @param path
	 */
	public ColorData(String path){
		
		this.path = path; 	
		
		File file = new File(this.path); 
				
		try {
		
			BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
			String st; 
			
			//Iteriere durch das ganze Dokument und liest die Line
			while ((st = br.readLine()) != null) {
				String output = "new Color";
				
				char[] arr = st.toCharArray();
				
				if(arr[0] != 'r') return;
				if(arr[1] != 'g') return;
				if(arr[2] != 'b') return;
				if(arr[3] != '(') return;
				
				int[] rgb = new int[3];
				String number = "";
				
				int i = 4; 
				for(int x: rgb) {
					while(arr[i] != ','  && arr[i] != ')') {
						number += arr[i];
						i++;
					}
					x = intOfString(number);
				}
				
				//Got all color Data in rgb array
				
				//Adding color to arrayList
				list.add(new Color(rgb));
			}
		
			//saving complete list
						
			colors = list.toArray(new Color[list.size()]);
			
			br.close();
			
		
		} catch (Exception e){
			System.err.println(e);
		}
	}
	
	private int intOfString(String out) {
		
		char[] arr = out.toCharArray();
		
		int a = 0;
		
		for(int i = 0; i < arr.length; i++) {
			
			switch(arr[i]) {
				
				case '0': break;
				case '1': a += 1 * Math.pow(10, arr.length - 1 - i); break;
				case '2': a += 2 * Math.pow(10, arr.length - 1 - i); break;
				case '3': a += 3 * Math.pow(10, arr.length - 1 - i); break;
				case '4': a += 4 * Math.pow(10, arr.length - 1 - i); break;
				case '5': a += 5 * Math.pow(10, arr.length - 1 - i); break;
				case '6': a += 6 * Math.pow(10, arr.length - 1 - i); break;
				case '7': a += 7 * Math.pow(10, arr.length - 1 - i); break;
				case '8': a += 8 * Math.pow(10, arr.length - 1 - i); break;
				case '9': a += 9 * Math.pow(10, arr.length - 1 - i); break;
				default: System.err.println("Wrong int format"); break;
				
			}
		}
		
		return a;
		
		
	}
	
}