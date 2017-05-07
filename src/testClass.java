
public class testClass {
	public static void main(String[] args) {
		Board b = new GridSystem();
		char[][] array = new char[][]{
			{'1', '1', '1', '1', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '0', '0', '0', '1'},
			{'1', '1', '1', '1', '1'}
		};
		
		b.create(array);
		b.modifyTile(1, 1, '2');
		b.displayBoard();
		b.modifyTile(3, 2, '3');
		b.modifyTile(4, 1, '3');
		b.displayBoard();
	}
}
