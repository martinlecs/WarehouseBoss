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
    private String mapFileName;
    private GameGraphics graphics;
    private Integer total_move;
    private boolean GAME_END;


    public GameEngine (String mapFileName){
        this.mapFileName = mapFileName;
        map = new GameMap(mapFileName); // load map data
        graphics = new GameGraphics("Game -test01", map); // load graphics
        graphics.addKeyListener(this);
        GAME_END = false;
        total_move = 0;
    }

    public void moveValidation2 (int dx, int dy){
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

        if (map.getGoal() == 0) {
            System.out.println("Congratulation the game is about to end");
            GAME_END = true;
        }
    }

    public void newGame (){
        graphics.dispose();
        map = new GameMap(mapFileName);
        graphics = new GameGraphics("test game", map);
        graphics.addKeyListener(this);
    }

    public void gameEnd (){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        graphics.setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                //moveValidation(UP, 0, -1);
                moveValidation2 (0, -1);
                map.updatePlayerDirection(UP);
                total_move ++;
                break;
            case KeyEvent.VK_DOWN:
                //moveValidation(DOWN, 0, 1);
                moveValidation2 (0, 1);
                map.updatePlayerDirection(DOWN);
                total_move ++;
                break;
            case KeyEvent.VK_LEFT:
                //moveValidation(LEFT, -1, 0);
                moveValidation2 (-1, 0);
                map.updatePlayerDirection(LEFT);
                total_move ++;
                break;
            case KeyEvent.VK_RIGHT:
                //moveValidation(RIGHT, 1, 0);
                moveValidation2 (1, 0);
                map.updatePlayerDirection(Constants.RIGHT);
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
        map.displayMap();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
