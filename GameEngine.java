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

    public void player_move_up(){

    }

    public void player_move_down(){

    }

    public void player_move_left(){

    }

    public void player_move_right(){

    }

    public void player_move (int direction){

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
            // for dubugging use only.
            System.out.println("PLAYER POSITON " + playerPosition.get(X) + ", " + playerPosition.get(Y));
            System.out.println("new position " + newPos.get(X) + ", " +  newPos.get(Y));

            // update the new data for player's old coord, correspond with the correct pixel type.
            map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
            // update the new data for player's next coord, correspond with the correct pixel type.
            map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
            // update player's position
            map.setPlayerPosition(newPos.get(X), newPos.get(Y));
        }if (type == BOX){ // if player's next move is a BOX, then check if the box is movable
            Integer nextType = map.whatIsThere(newPos.get(X) + dx,
                                                newPos.get(Y) + dy);
            if (nextType == INVALID || nextType == WALL || nextType == BOX) return false;
            if (nextType == ROAD){
                map.setXY(playerPosition.get(X), playerPosition.get(Y), ROAD);
                map.setXY(newPos.get(X), newPos.get(Y), PLAYER);
                map.setPlayerPosition(newPos.get(X), newPos.get(Y));
                map.setXY(newPos.get(X) + dx, newPos.get(Y) + dy, BOX);
            }
        }

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
                System.out.println("up");
                player_move_up();
                break;
            case KeyEvent.VK_DOWN:
                moveValidation(DOWN, 0, 1);
                player_move_down();
                break;
            case KeyEvent.VK_LEFT:
                moveValidation(LEFT, -1, 0);
                player_move_left();
                break;
            case KeyEvent.VK_RIGHT:
                moveValidation(RIGHT, 1, 0);
                player_move_right();
                break;
            default:break;// DO NOTHING
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
