
/**
 * The View queries 
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
	public void modifyTile(int x, int y, char structure);
	
	//If the player has reached a fail state, reset the level to the beginning
	public void resetGrid();
	
	//Returns the length of the board
	public int getLength();
	
	//Returns the width of the board
	public int getWidth();
	
	//Returns the current state of the board
	public char[][] getCurrentState();
}
