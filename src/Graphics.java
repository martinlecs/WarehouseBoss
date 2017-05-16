import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by b46qqq on 10/5/17.
 */
public class Graphics extends JFrame implements Observer{

    private Map map; // the map of the current game
    private JFrame gui;
    private Pixel[][] grid;
    private HashMap<Integer, BufferedImage> icon;



    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Integer> returnValue = (ArrayList<Integer>) arg;
    }
}
