import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is in control of the game rule.
 * rules such as:
 * - move validation
 * -
 */
public class GameEngine implements Constants, KeyListener{
    private GameMap map;

    public GameEngine (GameMap map){
        this.map = map;
    }
/*
    public boolean moveValidation (int direction, int dx, int dy){
        ArrayList<ArrayList<Integer>> renderPixel = new ArrayList<>();
        ArrayList<Integer> playerPosition = map.getPlayerPosition();

        renderPixel.add(map.getPlayerPosition());

        Integer type = map.whatIsThere(playerPosition.get(X),
                                        playerPosition.get(Y),
                                        direction);

        if (type == INVALID || type == WALL) return false;
        if (type == ROAD){
            ArrayList<Integer> toAdd = new ArrayList<>();
            toAdd.add(playerPosition.get(X) + dx);
            toAdd.add(playerPosition.get(Y) + dy);
            renderPixel.add(toAdd);

            System.out.println("PLAYER POSITON " + playerPosition.get(X) + ", " + playerPosition.get(Y));
            System.out.println("TO ADD COORD " + toAdd.get(X) + ", " +  toAdd.get(Y));

            map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
            map.setXY(toAdd.get(X), toAdd.get(Y), PLAYER);
            map.setPlayerPosition();
            //map.setPlayerPosition(playerPosition.get(X) + dx, playerPosition.get(Y) + dy);

            //map.update(renderPixel);
        }

        return true;
    }
*/
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

            //TODO, if the player go-to the goal position the icon should change
            //TODO, as it should contain both the player and the goal icon.
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
            default:break;// DO NOTHING
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
