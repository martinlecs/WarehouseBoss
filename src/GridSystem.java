/**
 * GridSystem implementation. Board is represented as a 2D array with each cell containing a char value to 
 * represent what is currently at that location
 * @author martinle
 *
 */
public class GridSystem implements Board{
	private char[][] initialBoard;	//initialBoard does not get modified, stores a copy of the board in it's original state
	private char[][] playerBoard;	//The board that track the player's actions
	private int length;
	private int width;

	@Override
	public void create(char[][] state) {
		
		this.length = state.length;
		this.width = state[0].length;
		//Create 2D array with each tile set to 0
		char[][] array = new char[this.length][this.width];
		
		for(int row = 0; row < this.length; row++) {
			for(int col = 0; col < this.width; col++) {
				array[row][col] = state[row][col];
			}
		}
		this.initialBoard = array;
		this.playerBoard = cloneBoard();
	}

	@Override
	public void modifyTile(int x, int y, char structure) {
		//Have addition checks to ensure that you cannot modify a wall?
		this.playerBoard[x][y] = structure;
		
	}

	@Override
	public void resetGrid() {
		playerBoard = cloneBoard();
	}

	@Override
	public void displayBoard() {
		for (int i = 0; i < this.length; i++) {
			for (int j = 0; j < this.width; j++) {
				System.out.print("[" + this.playerBoard[i][j] + "]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	/**
	 * Makes a copy of the initial Board state.
	 * The player should only modify a COPY of the initial board state.
	 * @return		a copy of initialBoard
	 */
	private char[][] cloneBoard() {
		char [][] myChar = new char[this.initialBoard.length][];
		for(int i = 0; i < this.initialBoard.length; i++) {
		    myChar[i] = this.initialBoard[i].clone();
		}
		return myChar;
	}


}
