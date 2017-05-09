import java.util.Observable;
import java.util.Observer;

/**
 * GameUI is a concrete implementation of the View from MVC
 * Think about renaming the class to something that can't be confused with the base MVC classes
 * @author martinle
 *
 */
public class GameUI implements GameView, Observer{
	
	//Display Menu
	//Display game
	/**
	 * Displays the board to the output stream
	 */
	@Override
	public void showBoard(GameModel g) {
		for (int i = 0; i < g.getLength(); i++) {
			for (int j = 0; j < g.getWidth(); j++) {
				System.out.print("[" + g.getCurrentState()[i][j] + "]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


}
