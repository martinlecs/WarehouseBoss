import java.util.ArrayList;
import java.util.Observable;

/**
 * GridSystem implementation. Board is represented as a 2D array with each cell containing a char value to 
 * represent what is currently at that location
 * @author martinle
 *
 */
public class GridSystem extends Observable implements GameModel {
	private char[][] initialBoard;	//initialBoard does not get modified, stores a copy of the board in it's original state
	private char[][] playerBoard;	//The board that track the player's actions
	private int length;
	private int width;
	private Player player;
	private ArrayList<Box> boxes = new ArrayList<Box>();
	
	/**
	 * Have to put this into interface instead of the create() method
	 * @param state
	 */
	public GridSystem(char[][] state) {
		create(state);
	}

	/**
	 * Creates a gameboard by reading in a prefilled array
	 */
	@Override
	public void create(char[][] state) {
		
		this.length = state.length;
		this.width = state[0].length;

		char[][] array = new char[this.length][this.width];
		
		for(int row = 0; row < this.length; row++) {
			for(int col = 0; col < this.width; col++) {
				array[row][col] = state[row][col];
				if(array[row][col] == '2') {
					this.player = new Player(row, col);
				}
				if(array[row][col] == '3') {
					this.boxes.add(new Box(row, col));
				}
			}
		}
		this.initialBoard = array;
		this.playerBoard = cloneBoard();
	}
	
	/**
	 * Given a move command, move the structure in that cell
	 */
	@Override
	public void modifyTile(int move, char structure) {
		//Have addition checks to ensure that you cannot modify a wall?
		// check structure position and check if valid move
		
	}
	
	/**
	 * Resets the playerBoard to the starting state.
	 */
	@Override
	public void resetGrid() {
		playerBoard = cloneBoard();
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
	
	/**
	 * 
	 * @param player
	 * @param move	the move
	 * @return		a boolean value for whether the move is valid or not
	 */
	private boolean isValidMove(Player player, int move) {
		
		//Get player coordinates
		int x = player.getPosition_x();
		int y = player.getPosition_y();
		
		//Look ahead to see if move is valid (Empty Square)
		//Up
		if(move == 1 && this.getCurrentState()[x][y+1] == '0') {
			return true;
		}
		//Down
		if (move == 2 &&this.getCurrentState()[x][y-1] == '0') {
			return true;
		}
		//Left
		if (move == 3 && this.getCurrentState()[x+1][y] == '0') {
			return true;
		}
		//Right
		if (move == 4 && this.getCurrentState()[x-1][y] == '0') {
			return true;
		}
		
		//Move only if box occupies space and can be moved in the direction 
		if(move == 1 && this.getCurrentState()[x][y+1] == '3') {
			//Problem now is getting the position of the box, can iterate through box list and check for 
			return true;
		}
		//Down
		if (move == 2 &&this.getCurrentState()[x][y-1] == '3') {
			return true;
		}
		//Left
		if (move == 3 && this.getCurrentState()[x+1][y] == '3') {
			return true;
		}
		//Right
		if (move == 4 && this.getCurrentState()[x-1][y] == '3') {
			return true;
		}
		
		return false;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public char[][] getCurrentState() {
		return playerBoard;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Box> getBoxes() {
		return boxes;
	}
	
	
	
}
