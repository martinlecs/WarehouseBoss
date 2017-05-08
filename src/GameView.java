/**
 * Updates the GUI, queries the Model for changes
 * @author martinle
 *
 */
public interface GameView /*implements Observer*/ {
	
	// shows the current game state
	public void showBoard(GameModel g);
}
