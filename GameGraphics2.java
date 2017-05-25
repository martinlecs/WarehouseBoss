import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameGraphics2 extends JPanel implements Constants{

	private ArrayList<ArrayList<Pixel>> map;
	private int x;
	private int y;
	private IconLibrary icons;

	public GameGraphics2(ArrayList<ArrayList<Pixel>> map, int x, int y, int pixelSize) {
		super();
		this.map = map;
		this.x = x;
		this.y = y;
        icons = new IconLibrary();
	}

	public void updateMap(ArrayList<ArrayList<Pixel>> map) {
		this.map = map;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(70*y, 70*x);
	}
	@Override
    protected void paintComponent (Graphics g){
		for (int y = 0; y < this.y; y ++){
            for (int x = 0; x < this.x; x ++){
            	if (map.get(y).get(x).getType() == PLAYER || map.get(y).get(x).getType() == BOX) {
            		g.drawImage(icons.getIcon(ROAD), x*70, y*70, null);
            	}
                g.drawImage(icons.getIcon(map.get(y).get(x).getType()), x*70, y*70, null);
            }
        }
    }
}