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


    public GameEngine (String mapFileName){
        this.mapFileName = mapFileName;
        map = new GameMap(mapFileName); // load map data
        graphics = new GameGraphics("Game -test01", map); // load graphics
        graphics.addKeyListener(this);
    }

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
                    map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
                }

            } else { // else if the player is on a ROAD pixel

                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
                map.setXY(newPos.get(X), newPos.get(Y), PLAYER_ON_GOAL);
                if (nextType == GOAL) {
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, GOAL_REACHED);
                }
                if (nextType == ROAD){
                    map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
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

        map.displayMap();

        return true;
    }

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
                moveValidation(UP, 0, -1);
                break;
            case KeyEvent.VK_DOWN:
                moveValidation(DOWN, 0, 1);
                break;
            case KeyEvent.VK_LEFT:
                moveValidation(LEFT, -1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                moveValidation(RIGHT, 1, 0);
                break;
            case KeyEvent.VK_R: // if R key is activated, game restart / reset
                newGame();
                break;
            case KeyEvent.VK_A: // if A key is activated, the wall infront of the player will be destroyed.
                System.out.println("A key is pressed");
                break;
            default:break;// DO NOTHING
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
