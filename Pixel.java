
/**
 * This class over writes the default paint method for JPanel.
 * By default JPanel cannot contain an image without
 * other component like JLabel.
 */
public class Pixel{
    private int type;

    public Pixel (int type){
        this.type = type;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
