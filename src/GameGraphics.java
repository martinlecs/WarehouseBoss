import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by b46qqq on 13/5/17.
  */

public class GameGraphics extends JFrame implements Constants, Observer{

    private GameMap gameMap;
    private IconLibrary icons;
    private ArrayList<ArrayList<Pixel>> map;
    //private final Dimension userScreenDimension;
    private int width;
    private int height;

    private final int pixelSize; // magic number ! please


    public GameGraphics (String title, GameMap gameMap){

        // initialise fields
        pixelSize = 70;
        //userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = pixelSize * gameMap.getX();
        height = pixelSize * gameMap.getY();
        icons = new IconLibrary();
        this.gameMap = gameMap;
        gameMap.addObserver(this);


        // initialise JFrame properties
        init();
        setTitle(title);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width , height);
        //setPreferredSize(new Dimension((int) (width * 0.5), (int) (height * 0.5)));
        //setLocation(userScreenDimension.width/2 - this.getSize().width/2 , userScreenDimension.height/2 - this.getSize().height/2);
        setLayout(new GridLayout(gameMap.getY(), gameMap.getX(), 0 , 0)); // yes, it is Y, X for some reason. I digged in a bit, examples showed me the parameter for gridlayout is row , col    ref : http://www.ugrad.cs.ubc.ca/~cs219/CourseNotes/Swing/swing-LayoutManagers-Grid.html
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public void init (){
        map = new ArrayList<>();
        for (int y = 0; y < gameMap.getY(); y ++){
            ArrayList<Pixel> temp = new ArrayList<>();
            for (int x = 0; x < gameMap.getX(); x ++){
                int pixelType = gameMap.getXY(x, y);
                Pixel p = new Pixel(icons.getIcon(pixelType), pixelSize, pixelSize);
                temp.add(p);
                this.add(p);
            }
            map.add(temp);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        ArrayList<Integer> render = (ArrayList<Integer>) arg;
        Pixel re_render = map.get(render.get(Y)).get(render.get(X));
        re_render.updateIcon(icons.getIcon(render.get(TYPE)));
        re_render.repaint();
    }
}
