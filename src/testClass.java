
public class testClass {
	public static void main(String[] args) {
		GameModel m = new GridSystem();
		GameView v = new GameUI();
		
		char[][] array = new char[][]{
			{'1', '1', '1', '1', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '1', '1', '1', '1'}
		};
		
		m.create(array);
		v.showBoard(m);
	}
}
