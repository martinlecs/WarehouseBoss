import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;

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
 * This class over writes the default paint method for JPanel,
 * by default JPanel cannot contain an image without other component like JLabel.
 */

public class Pixel extends JPanel{
    private Image icon;
    private int width;
    private int height;

    /**
     * constructor of pixel
     * @param icon image from source folder to be rendered
     * @param width defined width of pixel
     * @param height defined height of pixel
     * @pre all input fields are valid
     */
    public Pixel (Image icon, int width, int height){
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    /**
     * updates icon with new asset
     * @param icon new asset to be used
     * @pre asset exists/found
     */
    public void updateIcon (Image icon){
        this.icon = icon;
    }
    
    /**
     * painter in charge of initial drawing and redrawing
     */
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(icon, 0, 0,
                        width, height,
                        this);
    }
}
