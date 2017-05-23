import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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


    public GameEngine (String mapFileName){
        this.mapFileName = mapFileName;
        map = new GameMap(mapFileName); // load map data
        graphics = new GameGraphics("Game -test01", map); // load graphics
        graphics.addKeyListener(this);
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            graphics.dispose();
        }
    }

    /*
    public boolean moveValidation (int direction, int dx, int dy){

        ArrayList<Integer> playerPosition = map.getPlayerPosition();

        Integer type = map.whatIsThere(playerPosition.get(X) + dx,
                                        playerPosition.get(Y) + dy);

        if (type == INVALID || type == WALL) return false;

        ArrayList<Integer> newPos = new ArrayList<>();      // declare a new list, that contain the new X-Y coord
        newPos.add(playerPosition.get(X) + dx);
        newPos.add(playerPosition.get(Y) + dy);

        if (type == ROAD){  // if the player's next move is a ROAD.
            // if the current position if a goal
            if (map.getXY(playerPosition.get(X), playerPosition.get(Y)) == PLAYER_ON_GOAL) {
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL);
            } else {
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
            }
            map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));
        }

        if (type == BOX){ // if player's next move is a BOX, then check if the box is movable
            Integer nextType = map.whatIsThere(newPos.get(X) + dx,
                                                newPos.get(Y) + dy);
            // If the BOX cannot be moved, where the position next to the box is a wall or another box
            if (nextType == INVALID || nextType == WALL || nextType == BOX || nextType == GOAL_REACHED)
                return false;

            // else the box can be moved,
            // the original player position will definitely be ROAD !
            // the original box position will definitely be PLAYER !
            // Thus update the player's position as well.
            if (map.getXY(playerPosition.get(X), playerPosition.get(Y)) == PLAYER_ON_GOAL)
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL);
            else
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
            map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));

            // the new x-y coord next to the box have two choice
            if (nextType == ROAD){  // if its a ROAD, change to road
                map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
            }
            if (nextType == GOAL){ // if its a GOAL, then change to GOAL_REACHED
                map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, GOAL_REACHED);
                map.doneGoal();
                //TODO, once the box reach a goal position. Game state should be updated,
                //TODO, as the game should
            }
        }
        //
        if (type == GOAL){ // if the PLAYER's next move its a goal position
            // when the next move if another goal
            if (map.getXY(playerPosition.get(X), playerPosition.get(Y)) == PLAYER_ON_GOAL){
                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL);
            }else {
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
            }
            map.setXY(newPos.get(X), newPos.get(Y), PLAYER_ON_GOAL);
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));
        }

        if (type == GOAL_REACHED){
            Integer nextType = map.whatIsThere(newPos.get(X) + dx,
                                                newPos.get(Y) + dy);
            // if the next-next pixel not reachable !!!
            if (nextType == GOAL_REACHED || nextType == WALL || nextType == BOX || nextType == INVALID)
                return false;

            if (map.getXY(playerPosition.get(X), playerPosition.get(Y)) == PLAYER_ON_GOAL){

                map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL);

                if (nextType == GOAL){
                    map.setXY(newPos.get(X), newPos.get(Y), PLAYER_ON_GOAL);
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, GOAL_REACHED);
                }
                if (nextType == ROAD){
                    map.setXY(newPos.get(X), newPos.get(Y), PLAYER_ON_GOAL);
                    //map.setXY(newPos.get(X), newPos.get(Y), PLAYER); //original version (bug)
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
                    map.undoGoal();
                }

            } else { // else if the player is on a ROAD pixel

                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
                map.setXY(newPos.get(X), newPos.get(Y), PLAYER_ON_GOAL);
                if (nextType == GOAL) {
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, GOAL_REACHED);
                }
                if (nextType == ROAD){
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
                    map.undoGoal();
                }
            }
            // update player's current position
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));
        }
        if (type == PLAYER_ON_GOAL){
            Integer nextType = map.whatIsThere(newPos.get(X) + dx,
                                            newPos.get(Y) + dy);
            map.setXY(playerPosition.get(X), playerPosition.get(Y), GOAL);
            if (nextType == ROAD){
                map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
            }
            if (nextType == GOAL){
                map.setXY(playerPosition.get(X), playerPosition.get(Y), PLAYER_ON_GOAL);
            }
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));
        }
        // for debugging only
        if (map.getGoal() == 0)
            graphics.dispose();
        return true;
    }
*/
    public void newGame (){
        graphics.dispose();
        map = new GameMap(mapFileName);
        graphics = new GameGraphics("test game", map);
        graphics.addKeyListener(this);
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
                break;
            case KeyEvent.VK_DOWN:
                //moveValidation(DOWN, 0, 1);
                moveValidation2 (0, 1);
                map.updatePlayerDirection(DOWN);
                break;
            case KeyEvent.VK_LEFT:
                //moveValidation(LEFT, -1, 0);
                moveValidation2 (-1, 0);
                map.updatePlayerDirection(LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                //moveValidation(RIGHT, 1, 0);
                moveValidation2 (1, 0);
                map.updatePlayerDirection(Constants.RIGHT);
                break;
            case KeyEvent.VK_R: // if R key is activated, game restart / reset
                newGame();
                break;
            case KeyEvent.VK_A: // if A key is activated, the wall infront of the player will be destroyed.
                map.wallDestory();
                break;
            default:break;// DO NOTHING
        }
    }

    private void wallDestory() {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
