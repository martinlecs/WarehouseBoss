import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class over writes the default paint method for JPanel.
 * By default JPanel cannot contain an image without
 * other component like JLabel.
 */
public class Pixel extends JPanel{
    private Image icon;
    private int width;
    private int height;

    public Pixel (Image icon, int width, int height){
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    public void updateIcon (Image icon){
        this.icon = icon;
    }

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(icon, 0, 0,
                        width, height,
                        this);
    }
}
