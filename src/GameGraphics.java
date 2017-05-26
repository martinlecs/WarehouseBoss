import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
    private int width;
    private int height;

    private final int pixelSize = 70; // magic number ! please


    public GameGraphics (String title, GameMap gameMap){

        // initialise fields
        width = pixelSize * gameMap.getX() + 30;
        height = pixelSize * gameMap.getY() + 70;
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
    
    //menubar
    public void menuBar() {
    	JMenuBar menubar = new JMenuBar();
    	
    	JMenu file = new JMenu("Menu");
    	//binds menu to alt+e key
    	file.setMnemonic(KeyEvent.VK_F);
    	
    	JMenuItem main = new JMenuItem("Main Menu");
    	//binds exit to ALT+E combo
    	main.setMnemonic(KeyEvent.VK_T);
    	main.setToolTipText("Returning to menu");
    	main.addActionListener((ActionEvent menu) -> {
    		this.dispose();
    		new GameStartMenu();
    	});
    	
    	JMenuItem exit = new JMenuItem("Exit");
    	//binds exit to ALT+E combo
    	exit.setMnemonic(KeyEvent.VK_E);
    	//sets action
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
