import java.util.Observable;


/**
 * Have this class return a 2D array
 * @author martinle
 *
 */
public class Player extends Observable {
	int position_x;
	int position_y;
	int NumMoves;
	//PriorityQueue<Integer> prevMoves;
	
	
	public Player(int x, int y) {
		this.position_x = x;
		this.position_y = y;
		this.NumMoves = 0;
	}
	
	public int getPosition_x() {
		return position_x;
	}
	public void setPosition_x(int position_x) {
		this.position_x = position_x;
	}
	public int getPosition_y() {
		return position_y;
	}
	public void setPosition_y(int position_y) {
		this.position_y = position_y;
	}
	public int getNumMoves() {
		return NumMoves;
	}
	public void setNumMoves(int numMoves) {
		NumMoves = numMoves;
	}

	@Override
	public String toString() {
		return "Player [position_x=" + position_x + ", position_y=" + position_y + ", NumMoves=" + NumMoves + "]";
	}
	

}
