/**
 * c-2911 Group Project
 * Members :: {
 * @author     Alan Wan     z5076302
 * @author     Allan Lai    z5117352
 * @author     Martin Le    z3466361
 * @author     Zhaohan Bao  z5114676
 *            }
 * @version 5.0
 *
 * Class for spirtes 
 */
public class Coordinates {

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

	/**
	 * Derives a hashCode for a Coordinates Object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + sprite;
		return result;
	}

	/**
	 * Defines equality for a Coordinates object
	 */
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
	
	/**
	 * Defines a toString method for a Coordinates object
	 */
	@Override
	public String toString() {
		return "Coordinates [sprite=" + sprite + ", col=" + col + ", row=" + row + "]";
	}
}
