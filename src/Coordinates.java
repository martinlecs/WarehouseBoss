/**
 * Stores coordiantes of a Sprite
 * @author martinle
 *
 */
public class Coordinates {
	//Sprite codes:
	//Player = 0, Box = 1, Path = 2, Goal = 3, 4 = Wall
	private int sprite;
	private int col;
	private int row;

	public Coordinates (int sprite, int col, int row) {
		this.sprite = sprite;
		this.col = col;
		this.row = row;
	}

	public int getSprite() {
		return sprite;
	}

	public void setSprite(int sprite) {
		this.sprite = sprite;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	
	
	
}
