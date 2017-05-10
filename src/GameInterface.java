import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by b46qqq on 9/5/17.
 */

public class GameInterface extends JFrame implements Observer {

    //private Map map;
	private GridSystem board;
    private JFrame frame;
    private BitBlock[][] grid;
    private ArrayList<File> icons;

    final private int PLAYER_ICON = 0;
    final private int WALL_ICON = 1;
    final private int ROAD_ICON = 2;
    final private int BOX_ICON = 3;
    final private int GOAL_ICON = 4;

    final private char EMPTY  = '0';
    final private char WALL   = '1';
    final private char PLAYER = '2';
    final private char BOX    = '3';
    final private char GOAL   = '4';

//    final private int PLAYER = 1;
//    final private int WALL   = -1;
//    final private int BOX    = 2;
//    final private int GOAL   = 3;
//    final private int ROAD   = 0;
//    final private int X      = 0;
//    final private int Y      = 1;
    
    final private int UP = 1;
    final private int DOWN = 2;
    final private int LEFT = 3;
    final private int RIGHT = 4;


    public static void main (String[] args){
    	GameInterface n = new GameInterface();
        n.getBoard().addObserver(n);

    }

    public GameInterface () {
        loadIcon();
        //map = new Map();
        this.board = new GridSystem();
//		char[][] array = new char[][]{
//			{'1', '1', '1', '1', '1'},
//			{'1', '2', '0', '0', '1'},
//			{'1', '0', '3', '0', '1'},
//			{'1', '0', '0', '0', '1'},
//			{'1', '0', '0', '0', '1'},
//			{'1', '1', '1', '1', '1'}
//		};
        char[][] array = new char[][] {
            { WALL , WALL , WALL , WALL , WALL , EMPTY , EMPTY , EMPTY , EMPTY },
            { WALL ,PLAYER, EMPTY , EMPTY , WALL , EMPTY , EMPTY , EMPTY , EMPTY },
            { WALL , EMPTY ,  BOX ,  BOX , WALL , EMPTY , WALL , WALL , WALL },
            { WALL , EMPTY ,  BOX , EMPTY , WALL , EMPTY , WALL , GOAL , WALL },
            { WALL , WALL , WALL , EMPTY , WALL , WALL , WALL , GOAL , WALL },
            { EMPTY , WALL , WALL , EMPTY , EMPTY , EMPTY , EMPTY , GOAL , WALL },
            { EMPTY , WALL , EMPTY , EMPTY , EMPTY , WALL , EMPTY , EMPTY , WALL },
            { EMPTY , WALL , EMPTY , EMPTY , EMPTY , WALL , WALL , WALL , WALL },
            { EMPTY , WALL , WALL , WALL , WALL , WALL , EMPTY , EMPTY , EMPTY },
        };
 
        this.board.create(array);
        frame = new JFrame("Warehouse boss");
        init(frame);
    }

    public void init (JFrame frame){
        construct();
        //addPanel(size);
        frame.setSize(new Dimension(610, 610));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(board.getLength(), board.getWidth() ));
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
                    board.movePlayer(1);
                    break;
                case KeyEvent.VK_DOWN:
                	board.movePlayer(2);
                    break;
                case KeyEvent.VK_LEFT:
                	board.movePlayer(3);
                    break;
                case KeyEvent.VK_RIGHT:
                	board.movePlayer(4);
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

//    public void playerMoveUp (){
//        if (map.playerMoveUp()) {
//            int[] curr = map.getPlayerPostion();
//            int x = curr[X];
//            int y = curr[Y];
//            BitBlock update = grid[x][y];
//            BitBlock swap = grid[x+1][y];
//            update.changeIcon(swap.getIcon());
//            update.repaint();
//        }
//    }

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

        char[][] imageMap = this.board.getCurrentState();
        grid = new BitBlock[board.getLength()][board.getWidth()];

        for (int x = 0; x < board.getLength(); x++){
            for (int y = 0; y < board.getWidth(); y ++){

                if (imageMap[x][y] == PLAYER) {
                    grid[x][y] = new BitBlock(icons.get(PLAYER_ICON));
                }
                else if (imageMap[x][y] == WALL) {
                    grid[x][y] = new BitBlock(icons.get(WALL_ICON));
                }
                else if (imageMap[x][y] == EMPTY) {
                    grid[x][y] = new BitBlock(icons.get(ROAD_ICON));
                }
                else if (imageMap[x][y] == BOX) {
                    grid[x][y] = new BitBlock(icons.get(BOX_ICON));
                }
                else if (imageMap[x][y] == GOAL)
                    grid[x][y] = new BitBlock(icons.get(GOAL_ICON));
                
                frame.add(grid[x][y]);
            }
        }
    }

	public void showBoard(GameModel g) {
		for (int i = 0; i < g.getLength(); i++) {
			for (int j = 0; j < g.getWidth(); j++) {
				System.out.print("[" + g.getCurrentState()[i][j] + "]");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	@Override
	public void update(Observable o, Object arg) {
        frame = new JFrame("Warehouse boss");
		init(frame);
		
	}

	public GridSystem getBoard() {
		return board;
	}

	public void setBoard(GridSystem board) {
		this.board = board;
	}
	
}
