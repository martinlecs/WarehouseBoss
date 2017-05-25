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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + sprite;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (sprite != other.sprite)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinates [sprite=" + sprite + ", col=" + col + ", row=" + row + "]";
	}
	
	
	
	
	
	
	
	
}
