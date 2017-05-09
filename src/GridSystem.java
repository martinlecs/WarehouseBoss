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
//	public GridSystem(char[][] state) {
//		create(state);
//	}

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
	
	public void movePlayer(int move) {
		//Check if valid move
		if (isValidMovePlayer(this.player, move) {
			
		}
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
	 * @param player the Player object
	 * @param move	the move
	 * @return		a boolean value for whether the move is valid or not
	 */
	private boolean isValidMovePlayer(Player player, int move) {
		
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
			if(isValidMoveBox(getBox(x, y-1), move)) {
				//move the fucking box
			}
			return true;
		}
		//Down
		if (move == 2 &&this.getCurrentState()[x][y-1] == '3') {
			if(isValidMoveBox(getBox(x, y-1), move)) {
				//move box
			}
			return true;
		}
		//Left
		if (move == 3 && this.getCurrentState()[x+1][y] == '3') {
			if(isValidMoveBox(getBox(x, y-1), move)) {
				//move box
			}
			return true;
		}
		//Right
		if (move == 4 && this.getCurrentState()[x-1][y] == '3') {
			if(isValidMoveBox(getBox(x, y-1), move)) {
				//move box
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Sees if a box can be moved to a valid position
	 * @param b			The box object
	 * @param move		The move taken
	 * @return			a boolean value which represents whether the move can be made
	 */
	private boolean isValidMoveBox(Box b, int move) {
		
		//Get box coordinates
		int x = b.getPosition_x();
		int y = b.getPosition_y();
		
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
		return false;
	}
	
	/**
	 * Returns the box object that is associated to a position on the board.
	 * @return
	 */
	private Box getBox(int x, int y) {
		for(Box curr: this.getBoxes()) {
			if (curr.getPosition_x() == x && curr.getPosition_y() == y) {
				return curr;
			}
		}
		return null;
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
