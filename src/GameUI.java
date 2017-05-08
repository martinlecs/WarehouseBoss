/**
 * GameUI is a concrete implementation of the View from MVC
 * @author martinle
 *
 */
public class GameUI implements GameView{

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


}
