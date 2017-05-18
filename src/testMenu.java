import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class testMenu extends JFrame{
    private JPanel p1;
    private JLabel background;
    private JButton play; 
    private JButton options;
    private JButton instructions;
    private JButton exit;
    
    public testMenu(int width, int height) {
    	p1 = new JPanel();
    	// make see through
    	p1.setOpaque(false);
    	
    	setTitle("Warehouse Boss");
    	setPreferredSize(new Dimension(width, height));
    	
    	
    	background = new JLabel();
    	background.setLayout(new FlowLayout());
    	
    	try {
    		Image toLoad = ImageIO.read(getClass().getResource("test.png"));
    		background.setIcon(new ImageIcon(toLoad));
    	} catch (Exception ex) {}
    	
    	
    	//background.setIcon(new ImageIcon("test.png"));
    	background.setLayout(new BorderLayout());
    	
    	play = new JButton("Play");
    	options = new JButton("Options");
    	instructions = new JButton("Instuctions");
    	exit = new JButton("Exit");
    	
    	p1.add(play);
    	p1.add(options);
    	p1.add(instructions);
    	p1.add(exit);
    	
    	background.add(p1);
    	add(background);	
    }
    
    public static void main(String[] args) {
    	JFrame frame = new testMenu(1000, 1000);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setVisible(true);
    	
    }
}
