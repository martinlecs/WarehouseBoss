import java.util.ArrayList;

/**
 * The View queries the Model for updates.
 * This model interface assumes that you are using are representing the board as a grid
 * @author martinle
 *
 */
public interface GameModel {

	//Assuming that we will use a 2D array to represent the board state
	// x = length, y = width
	public void create(char state[][]);
	
	//Modifies what structure is on a specified tile (ie. used to move players or to set boxes or traps)
	// 0 = empty tile
	// 1 = wall
	// 2 = Player
	// 3 = Box
	// 4 = goal
	void modifyTile(int x, int y, char structure);
	
	
	// 1 = Up
	// 2 = Down
	// 3 = Left
	// 4 = Right
	// Move player, only if the move is valid
	public void movePlayer(int move);
	
	//If the player has reached a fail state, reset the level to the beginning
	public void resetGrid();
	
	//Returns the length of the board
	public int getLength();
	
	//Returns the width of the board
	public int getWidth();
	
	//Returns the current state of the board
	public char[][] getCurrentState();

	public Player getPlayer();
	
	public ArrayList<Box> getBoxes();


	
}
