import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class BitBlock extends JPanel {

    private File icon;
    private BufferedImage image;

    public BitBlock (File icon){
        this.icon = icon;
        try {
            image = ImageIO.read(icon);
            //image = ImageIO.read(new File(getClass().getResource("boss.png").toURI()));
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void changeIcon (File icon){
        try {
            image = ImageIO.read(icon);
            //image = ImageIO.read(new File(getClass().getResource("boss.png").toURI()));
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public File getIcon (){
        return icon;
    }
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 70, 70, this);
    }
}