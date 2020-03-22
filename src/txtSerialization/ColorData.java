/**
 * 
 */
package txtSerialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataModels.Color;

/**
 * @author ericmiddelhove
 *         license = CC-BY-SA-NC
 *         http://creativecommons.org/licenses/by-nc-sa-/4.0/
 */
public class ColorData {
	
	private static String LINE_RGB_ENTRY = "rgb\\((?<r>\\d+),(?<g>\\d+),(?<b>\\d+)\\)";
	// private static String LINE_RGB_ENTRY_ALLOW_SPACES = "rgb\\(\\s*(?<r>\\d+)\\s*,\\s*(?<g>\\d+)\\s*,\\s*(?<b>\\d+)\\s*\\)";
	// allows things like rgb(255, 255, 255) or rgb(  255  ,  255  ,  255  ) even
	
	private Color[] colors;
	
	public ColorData(String path) {
		load(new File(path));
	}
	
	private void load(File file) {
		try(Scanner sc = new Scanner(file)) {
			ArrayList<Color> list = new ArrayList<>();
			Pattern linePattern = Pattern.compile(LINE_RGB_ENTRY);
			while(sc.hasNextLine()) {
				String next = sc.nextLine();
				// use a regex matcher for parsing
				Matcher m = linePattern.matcher(next);
				if(m.find()) {
					if(m.groupCount() == 3) {
						// m.group(0) is the whole match
						list.add(new Color(
								Integer.parseInt(m.group("r")),
								Integer.parseInt(m.group("g")),
								Integer.parseInt(m.group("b"))));
					}
				} else {
					System.err.println("Line \"" + next + "\" doesn't match");
				}
			}
			colors = list.toArray(new Color[list.size()]);
		} catch(NumberFormatException e) {
			// regex confirmed digits so why would this occur
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Color[] getColors() {
		return colors;
	}
	
}