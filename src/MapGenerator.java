import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Read a map from a text file
 * 1 = premade level
 * 2 = randomly generated level
 * @author martinle
 *
 */
public class MapGenerator {
	
	private char[][] map;
	
	public MapGenerator() {
		// does nothing
		
	}
	/**
	 * Store size of array in the first line
	 * 
	 * @return
	 */
	public void premade(String filename) {
		File inFile = new File(filename);
		Scanner sc = null;
		try {

			sc = new Scanner(new FileReader(inFile));
			//create array from first line
			String line = "";
			line = sc.nextLine().trim();
			String[] num = line.split("[ ]+");
			char[][] array = new char[Integer.parseInt(num[0])][Integer.parseInt(num[1])];
			int colNum = 0;
			
			while(sc.hasNextLine()) {
				
				line = sc.nextLine().trim();
				num = line.split("[ ]+");
				
				for(int i = 0; i < num.length; i++) {
					array[colNum][i] = num[i].charAt(0);
				}
				
				colNum++;
				
			}
			this.map = array;
			
		} catch (FileNotFoundException e) {
			System.err.println("File was not found");
		} finally {
			if (sc != null) sc.close();
		}
	}
	
	public char[][] random() {
		return null;
	}
	public char[][] getMap() {
		return map;
	}
	public void setMap(char[][] map) {
		this.map = map;
	}
	
	
}
