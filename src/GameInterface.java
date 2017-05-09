import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by b46qqq on 9/5/17.
 */

public class GameInterface extends JFrame {

    private Map map;
    private JFrame frame;
    private BitBlock[][] grid;
    private ArrayList<File> icons;

    final private int PLAYER_ICON = 0;
    final private int WALL_ICON = 1;
    final private int ROAD_ICON = 2;
    final private int BOX_ICON = 3;
    final private int GOAL_ICON = 4;


    final private int PLAYER = 1;
    final private int WALL   = -1;
    final private int BOX    = 2;
    final private int GOAL   = 3;
    final private int ROAD   = 0;
    final private int X      = 0;
    final private int Y      = 1;


    public static void main (String[] args){
        new GameInterface();

    }

    public GameInterface () {
        loadIcon();
        map = new Map();
        frame = new JFrame("Warehouse boss");
        init(frame);
    }

    public void init (JFrame frame){
        construct();
        //addPanel(size);
        frame.setSize(new Dimension(610, 610));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(map.getWidth() , map.getHeight()));
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(new getKey());
    }

    private class getKey extends KeyAdapter {
        @Override
        public void keyPressed (KeyEvent e){
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_UP:
                    playerMoveUp();
                    System.out.println("UP key is pressed");
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("DOWN key is pressed");
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("LEFT key is pressed");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("RIGHT key is pressed");
                    break;
                default:
                    //System.out.println("Still workes"); IGNORE
                    break;
            }
        }
        @Override
        public void keyReleased (KeyEvent e){
            //System.out.println("Key released !!! ");
            // IGNORE for now
        }
    }

    public void playerMoveUp (){
        if (map.playerMoveUp()) {
            int[] curr = map.getPlayerPostion();
            int x = curr[X];
            int y = curr[Y];
            BitBlock update = grid[x][y];
            BitBlock swap = grid[x+1][y];
            update.changeIcon(swap.getIcon());
            update.repaint();
        }
    }

    public void loadIcon (){
        icons = new ArrayList<>();
        try {
            icons.add(new File(getClass().getResource("boss.png").toURI()));
            icons.add(new File(getClass().getResource("wall.png").toURI()));
            icons.add(new File(getClass().getResource("road.png").toURI()));
            icons.add(new File(getClass().getResource("box.png").toURI()));
            icons.add(new File(getClass().getResource("goal.png").toURI()));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addPanel (int x) {
        grid = new BitBlock[x][x];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                grid[i][j] = new BitBlock(icons.get(0));
                frame.add(grid[i][j]);
            }
        }
    }

    public void construct (){

        int[][] imageMap = map.getMap();
        grid = new BitBlock[map.getHeight()][map.getWidth()];

        for (int x = 0; x < map.getHeight(); x ++){
            for (int y = 0; y < map.getWidth(); y ++){

                System.out.println("x = " + x + " y = " + y);

                if (imageMap[x][y] == PLAYER)
                    grid[x][y] = new BitBlock(icons.get(PLAYER_ICON));
                else if (imageMap[x][y] == WALL)
                    grid[x][y] = new BitBlock(icons.get(WALL_ICON));
                else if (imageMap[x][y] == ROAD)
                    grid[x][y] = new BitBlock(icons.get(ROAD_ICON));
                else if (imageMap[x][y] == BOX)
                    grid[x][y] = new BitBlock(icons.get(BOX_ICON));
                else if (imageMap[x][y] == GOAL)
                    grid[x][y] = new BitBlock(icons.get(GOAL_ICON));


                frame.add(grid[x][y]);
            }
        }
    }
}
