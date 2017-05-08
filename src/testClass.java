
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
		m.modifyTile(1, 1, '2');
		v.showBoard(m);
		m.modifyTile(3, 2, '3');
		m.modifyTile(4, 1, '3');
		v.showBoard(m);
	}
}
