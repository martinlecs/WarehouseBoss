
public class testClass {
	public static void main(String[] args) {
		GameModel m = new GridSystem();
		GameView v = new GameUI();
		//Make it so that v automatically prints shit when I update
		//implementing observer functions
		
		char[][] array = new char[][]{
			{'1', '1', '1', '1', '1'},
			{'1', '2', '0', '0', '1'},
			{'1', '0', '3', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '1', '1', '1', '1'}
		};
		
		m.create(array);
		v.showBoard(m);
		int[] a = {4,2,3,2,4}; 
		for(int i = 0; i < a.length; i++) {
			m.movePlayer(a[i]);
			v.showBoard(m);
			System.out.println(m.getPlayer() + "\n");
			System.out.println(m.getBoxes() + "\n");
		}
	}
}
