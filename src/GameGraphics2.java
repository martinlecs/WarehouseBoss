import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

//maze generator
public class GameGraphics2 extends JPanel implements Constants, ActionListener{

	private ArrayList<ArrayList<Pixel>> map;
	private int x;
	private int y;
	private IconLibrary icons;
	private Timer timer;

	public GameGraphics2(ArrayList<ArrayList<Pixel>> map, int x, int y, int pixelSize) {
		super();
		this.map = map;
		this.x = x;
		this.y = y;
        icons = new IconLibrary();
        timer = new Timer(200, this);
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
		//super.paintComponent(g);
		for (int y = 0; y < this.y; y ++){
            for (int x = 0; x < this.x; x ++){
            	
            	//issue lies in that prev tile is not updating when obj is in same location.
            	//ie player moves into prev box tile or player hits wall
            	
            	//needs player location 
            	//g.clearRect(map.get(y).get(x), map.get(x), 70, 70);
            	
            	//rendering road under player
            	if (map.get(y).get(x).getType() == PLAYER || map.get(y).get(x).getType() == BOX) {
            		g.drawImage(icons.getIcon(ROAD), x*70, y*70, 70, 70, null);
            	}
            	//regular rendering
                g.drawImage(icons.getIcon(map.get(y).get(x).getType()), x*70, y*70, 70, 70, null);
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	//backend needs to update to send commands for player on goal to be transparent
	//and the walk commands to update with animation
}
