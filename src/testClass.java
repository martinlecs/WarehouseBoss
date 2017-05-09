
public class testClass {
	public static void main(String[] args) {
		GameModel m = new GridSystem();
		GameView v = new GameUI();
		
		char[][] array = new char[][]{
			{'1', '1', '1', '1', '1'},
			{'1', '2', '3', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '1', '1', '1', '1'}
		};
		
		m.create(array);
		v.showBoard(m);
		int[] a = {4,4,}; 
		for(int i = 0; i < a.length; i++) {
			m.movePlayer(a[i]);
			v.showBoard(m);
			System.out.println(m.getPlayer() + "\n");
			System.out.println(m.getBoxes() + "\n");
		}
	}
}
