import java.util.Observable;

/**
 * Have this class return a 2D array
 * @author martinle
 * 
 * B = box
 * 0 = transparent background
 *
 */
public class Box extends Observable {
	int position_x;
	int position_y;
	boolean goal;
	//PriorityQueue<Integer> prevMoves;
	
	public Box(int x, int y) {
		this.position_x = x;
		this.position_y = y;
		this.goal = false;
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

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	@Override
	public String toString() {
		return "Box [position_x=" + position_x + ", position_y=" + position_y + ", goal=" + goal + "]";
	}
	
	
	
}
