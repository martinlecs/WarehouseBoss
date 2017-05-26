import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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
 * GameEngine, the heart of the game. Manages the process of the game based on user's operation.
 * Two interfaces are implemented:
 *                                1. Constants   - local interface
 *                                2. KeyListener - from java.awt.event.KeyListener
 *
 */


public class GameEngine implements Constants, KeyListener{
    private GameMap map;
    private String mapFileName;
    private GameGraphics graphics;
    private Integer total_move;

    /**
     * Constructor for this class.
     * store map's file name in argument for reset use.
     * @param mapFileName name of the file containing map data
     */
    public GameEngine (String mapFileName) {
        this.mapFileName = mapFileName;
        map = new GameMap(true); // load map data
        graphics = new GameGraphics("Game", map); // load graphics
        graphics.addKeyListener(this);
        total_move = 0;
        new Thread(){
            @Override
            public void run (){
                while (true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(map.getGoal());
                    if (map.getGoal() == 0){
                        gameEnd();
                        break;
                    }
                }
            }
        }.start();
    }

    /**
     * Checks if player's operation is valid.
     * Once validation checked update the map date and graphics.
     *
     * dx, dy has to be either 0 or 1.
     * @param dx change in x with respect to current position
     * @param dy change in y with respect to current position
     */
    public void moveValidation2 (Integer dx, Integer dy){
        if (dx == null || dy == null) return;
        ArrayList<Integer> playerPosition = map.getPlayerPosition();

        Integer nextPixelType = map.whatIsThere(playerPosition.get(X) + dx,
                                                playerPosition.get(Y) + dy);

        Integer nextNextPixelType = map.whatIsThere(playerPosition.get(X) + (2 * dx),
                                                    playerPosition.get(Y) + (2 * dy));
        int nextX = playerPosition.get(X) + dx;
        int nextY = playerPosition.get(Y) + dy;

        if (nextPixelType == null) return;
        // if the next move is NOT valid. example, wall block
        if (nextPixelType == INVALID || nextPixelType == WALL) return;

        // if current position is a combination pixel block
        // combination as in player on top of a goal pixel or a box reached a goal state
        // if the next move is not valid, for example wall blocked, then simple return and do nothing.
        if (nextPixelType == INVALID || nextPixelType == WALL) return;

        int nextNextX = playerPosition.get(X) + (2 * dx);
        int nextNextY = playerPosition.get(Y) + (2 * dy);
        // if next move is a road. Update the next and curr pixel block
        if (nextPixelType == ROAD){
            if (map.isPlayerOnGoal())
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL); // update current pixel
            else
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD); // update current pixel
            map.setXY(nextX, nextY, PLAYER);                                // update next pixel
            map.setPlayerPosition(nextX, nextY);                            // update player position
        }else if (nextPixelType == BOX || nextPixelType == GOAL_REACHED){    // if next pixel is a box
            // since we will be pushing a box, check if the box is movable
            if (nextNextPixelType == null) return;
            if (nextNextPixelType == INVALID || nextNextPixelType == WALL ||
                    nextNextPixelType == BOX || nextNextPixelType == GOAL_REACHED) return;
            // once we checked that the box is movable
            // we can be sure that the current pixel and the next pixel is going to be.
            if (map.isPlayerOnGoal())
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL); // update current pixel
            else
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD); // update current pixel
            map.setPlayerPosition(nextX, nextY);                           // update player position

            if (nextPixelType == BOX)
                map.setXY(nextX, nextY, PLAYER);                               // update next pixel to player
            else if (nextPixelType == GOAL_REACHED){
                map.setXY(nextX, nextY, PLAYER_ON_GOAL);                               // update next pixel to player
                map.undoGoal();
            }
            if (nextNextPixelType == ROAD)  // if next next pixel is road/empty
                map.setXY(nextNextX, nextNextY, BOX);                       // update current pixel
            else if (nextNextPixelType == GOAL) { // when next next pixel if the goal
                map.setXY(nextNextX, nextNextY, GOAL_REACHED);              // GOAL REACHED !!
                map.doneGoal();
            }
        }else if (nextPixelType == GOAL) {   // if the next pixel is a goal --> lead to new image
            if (map.isPlayerOnGoal())
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL); // update current pixel
            else
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD); // update current pixel
            map.setXY(nextX, nextY, PLAYER_ON_GOAL);
            map.setPlayerPosition(nextX, nextY);                            // update player position
        }
    }

    /**
     * Method called when user's operation is reset.
     * Simply trash the current graphics.
     * Load in a new map, initialise new graphics with newly loaded map.
     * And plug in key listener to the new graphics.
     */
    public void newGame (){
        graphics.dispose();
        map = new GameMap(true);
        graphics = new GameGraphics("test game", map);
        graphics.addKeyListener(this);
    }

    /**
     * Method called when user have completed the current level,
     * by which all boxes are pushed on to goal.
     * Then trash the current graphics and display the result menu.
     */
    public void gameEnd (){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        graphics.dispose();
        new ResultMenu();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //if (map.getGoal() == 0) gameEnd();

        Integer move    = null;
        Integer dx      = null;
        Integer dy      = null;

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                dx = 0; dy = -1; move = UP;
                total_move ++;
                break;
            case KeyEvent.VK_DOWN:
                dx = 0; dy = 1; move = DOWN;
                total_move ++;
                break;
            case KeyEvent.VK_LEFT:
                dx = -1; dy = 0; move = LEFT;
                total_move ++;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1; dy = 0; move = RIGHT;
                total_move ++;
                break;
            case KeyEvent.VK_R: // if R key is activated, game restart / reset
                newGame();
                break;
            case KeyEvent.VK_A: // if A key is activated, the wall infront of the player will be destroyed.
                map.wallDestory();
                break;
            case KeyEvent.VK_Q:
                gameEnd ();
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("escape key pressed!");
                break;
            default:break;// DO NOTHING
        }
        moveValidation2 (dx, dy);
        map.updatePlayerDirection(move);
        //map.displayMap(); // for debugging purposes
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
