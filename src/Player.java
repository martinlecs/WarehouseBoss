
public class Player {
	int position_x;
	int position_y;
	int NumMoves;
	
	
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
	
	

}
