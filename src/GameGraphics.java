import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
 * GameGraphics extends JFrame to create a window that display the game.
 * Two interfaces are implemented:
 *                                1. Constants   - local interface
 *                                2. Observer    - from java.util.Observer
 */

public class GameGraphics extends JFrame implements Constants, Observer{

    private GameMap gameMap;
    private IconLibrary icons;
    private ArrayList<ArrayList<Pixel>> map;
    private int width;
    private int height;

    private final int pixelSize = 70; // magic number ! please


    /**
     * constructor of GameGraphics
     * @param title title of game window
     * @param gameMap matrix of game data to be used to create and update map
     * @pre title is not null and gameMap is non-corrupted and non-null GameMap object
     */
    public GameGraphics (String title, GameMap gameMap){

        // initialise fields
        width = pixelSize * gameMap.getX();
        height = pixelSize * gameMap.getY();
        icons = new IconLibrary();
        this.gameMap = gameMap;
        gameMap.addObserver(this);

     // initialise JFrame properties
        init();
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width , height);
        setLayout(new GridLayout(gameMap.getY(), gameMap.getX(), 0 , 0)); // yes, it is Y, X for some reason. I digged in a bit, examples showed me the parameter for gridlayout is row , col    ref : http://www.ugrad.cs.ubc.ca/~cs219/CourseNotes/Swing/swing-LayoutManagers-Grid.html
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        menuBar();
    }

    /**
     * uses nested array based on gamemap's coord. system 
     * to produce an array of jpanels (pixel)
     * @post map is created and rendered
     */
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

    /**
     * updates the game map
     * @param arg An array contain the x, y coordinate of the pixel that needs to be updated
     *            and the type that pixel is going to be updated to
     * @pre both input fields are valid
     */
    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Integer> render = (ArrayList<Integer>) arg;
        Pixel re_render = map.get(render.get(Y)).get(render.get(X));
        re_render.updateIcon(icons.getIcon(render.get(TYPE)));
        re_render.repaint();
    }
    
    /**
     * Creates a menubar instance above the game allowing for player to exit
     * @post creates a menubar
     */
    public void menuBar() {
    	JMenuBar menubar = new JMenuBar();
    	
    	JMenu file = new JMenu("Menu");

    	
    	JMenuItem main = new JMenuItem("Main Menu");

    	main.setToolTipText("Returning to menu");
    	main.addActionListener((ActionEvent menu) -> {
    		this.dispose();
    		new GameStartMenu();
    	});
    	
    	JMenuItem exit = new JMenuItem("Exit");

    	exit.setToolTipText("Exit application");
    	exit.addActionListener((ActionEvent event) -> {
    		System.exit(0);
    	});
    	
    	file.add(main);
    	menubar.add(file);
    	file.add(exit);
    	menubar.add(file);
    	setJMenuBar(menubar);
    }
}
