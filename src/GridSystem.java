
public class GridSystem implements Board{
	private char[][] board;
	private int length;
	private int width;

	@Override
	public void create(int x, int y) {
		//Create 2D array with each tile set to 0
		char[][] array = new char[x][y];
		this.length = x;
		this.width = y;
		this.board = array;
	}

	@Override
	public Board setGrid(char[][] state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyTile(int x, int y, char structure) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board resetGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DisplayBoard() {
		for (int i = 0; i < this.length; i++) {
			for (int j = 0; j < this.width; j++) {
				System.out.print("[" + this.board[i][j] + "]");
			}
			System.out.print("\n");
		}
	}

}
