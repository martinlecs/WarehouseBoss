import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * This class is in control of the game rule.
 * rules such as:
 * - move validation
 * -
 */
public class GameEngine implements Constants, KeyListener{
    private GameMap map;
    private String mapFileName; // Need to just load map here
    private GameGraphics graphics;
    private Integer total_move;

    public GameEngine (String mapFileName) {
        this.mapFileName = mapFileName;
        map = new GameMap(true); // load map data
        graphics = new GameGraphics("Game", map); // load graphics
        graphics.addKeyListener(this);
        total_move = 0;
    }

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

    public void newGame (){
        graphics.dispose();
        map = new GameMap(true);//if statement to check for premade or randomly generated map
        graphics = new GameGraphics("test game", map);
        graphics.addKeyListener(this);
    }

    public void gameEnd (){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        graphics.dispose();
        new ResultMenu();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (map.getGoal() == 0) gameEnd();

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
            	graphics.dispose();
            	map = new GameMap(false);
                graphics = new GameGraphics("Game -test01", map);
                graphics.addKeyListener(this);
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
            default:
            	break;// DO NOTHING
        }
        moveValidation2 (dx, dy);
        map.updatePlayerDirection(move);
        //map.displayMap(); // for debugging purposes
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
