import apple.laf.JRSUIConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * This class manages to store/load map data (from outside file)
 * and generate map data.
 */
public class GameMap extends Observable implements Constants{
    private ArrayList<ArrayList<Integer>> map;
    private int x;
    private int y;
    private int goal;
    private ArrayList<Integer> playerPosition;

    public GameMap (boolean AutoGenerate){
        //TODO, AUTO GENERATE map data
    }

    public GameMap (String filename){
        //TODO, LOAD OUTSIDE FILE, given the name of the file
        map = new ArrayList<>();
        playerPosition = new ArrayList<>();
        x = y = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(getClass().getResource(filename).toURI())));
            String line;
            while ((line = in.readLine())!= null){

                String[] temp = line.trim().split("\\s+");
                ArrayList<Integer> array = new ArrayList<>();
                x = (x < temp.length) ?  temp.length : x;
                for (int i = 0; i < temp.length; i ++){
                    int v = Integer.parseInt(temp[i]);
                    if (v ==  PLAYER) {
                        Random rand = new Random();
                        int randomNum = rand.nextInt((PLAYER_FACE_RIGHT - PLAYER_FACE_UP) + 1) + PLAYER_FACE_UP;
                        playerPosition.add(X, i); //set a random player facing direction
                        playerPosition.add(Y, y);
                        v = randomNum;
                    }
                    if (v == GOAL)
                        goal ++;
                    array.add(v);
                }
                map.add(array);
                y ++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private Integer setPlayerDirection (int d){
        int direction = PLAYER;
        if (d == UP) direction = PLAYER_FACE_UP;
        else if (d == DOWN) direction = PLAYER_FACE_DOWN;
        else if (d == LEFT) direction = PLAYER_FACE_LEFT;
        else if (d == RIGHT) direction = PLAYER_FACE_RIGHT;
        map.get(playerPosition.get(Y)).set(playerPosition.get(X), direction);
        return direction;
    }

    // getter for raw map data
    public ArrayList<ArrayList<Integer>> getMap (){
        return map;
    }

    public void setPlayerPosition (int x, int y){
        playerPosition.set(X, x);
        playerPosition.set(Y, y);
    }

    public void setPlayerPosition (){
        for (int y = 0; y < this.y; y ++){
            for (int x = 0; x < this.x; x++){
                if (map.get(y).get(x) == PLAYER){
                    playerPosition.set(X, x);
                    playerPosition.set(Y, y);
                    return;
                }
            }
        }
    }

    public boolean isPlayerOnGoal (){
        int type = map.get(playerPosition.get(Y)).get(playerPosition.get(X));
        if (type == PLAYER_ON_GOAL ||
                type == PLAYER_FACE_UP_ON_GOAL ||
                type == PLAYER_FACE_DOWN_ON_GOAL ||
                type == PLAYER_FACE_LEFT_ON_GOAL ||
                type == PLAYER_FACE_RIGHT_ON_GOAL) return true;
        return false;
    }

    public void updatePlayerDirection (int direction){
        ArrayList<Integer> re_render = new ArrayList<>();
        re_render.add(playerPosition.get(X));
        re_render.add(playerPosition.get(Y));
        if (isPlayerOnGoal()) {
            if (direction == UP) re_render.add(PLAYER_FACE_UP_ON_GOAL);
            else if (direction == DOWN) re_render.add(PLAYER_FACE_DOWN_ON_GOAL);
            else if (direction == LEFT) re_render.add(PLAYER_FACE_LEFT_ON_GOAL);
            else if (direction == RIGHT) re_render.add(PLAYER_FACE_RIGHT_ON_GOAL);
        }else {
            int type = setPlayerDirection(direction);
            re_render.add(type);
        }
        notifyObservers(re_render);
    }

    public int getGoal (){
        return goal;
    }

    public void doneGoal (){
        goal --;
    }

    public void undoGoal (){
        goal ++;
    }

    public int getX (){
        return  x;
    }

    public int getY (){
        return y;
    }

    public int getXY (int x, int y){
        return map.get(y).get(x);
    }

    public void setXY (int x, int y, int type){
        map.get(y).set(x, type);
        ArrayList<Integer> re_render = new ArrayList<>();
        re_render.add(x);
        re_render.add(y);
        re_render.add(type);
        notifyObservers(re_render);
    }

    public ArrayList<Integer> getPlayerPosition(){
        return playerPosition;
    }

    public Integer whatIsThere (int x, int y, int Direction){
        if (Direction == UP){
            if (y == 0) return INVALID;
            return map.get(y - 1).get(x);
        }else if (Direction == DOWN){
            if (y == this.y) return INVALID;
            return map.get(y + 1).get(x);
        }else if (Direction == LEFT){
            if (x == 0) return INVALID;
            return map.get(y).get(x - 1);
        }else if (Direction == RIGHT){
            if (x == this.x) return INVALID;
            return map.get(y).get(x + 1);
        }
        return -1;
    }



    public Integer whatIsThere (int x, int y){
        if (x < 0 || x > this.x - 1) return null;
        if (y < 0 || y > this.y - 1) return null;
        return map.get(y).get(x);
    }

    public GameMap (File file){
        //TODO, LOAD OUTSIDE FILE, given file itself
    }

    @Override
    public void notifyObservers(Object arg){
        setChanged();
        super.notifyObservers(arg);
    }

    // for debugging purposes only
    public void displayMap (){
        System.out.println(map.size());
        for (int y = 0; y < map.size(); y ++){
            for (int x = 0; x < map.get(y).size(); x ++){
                System.out.print (map.get(y).get(x) + ", ");
            }
            System.out.println();
        }
    }

    public void displayWH (){
        System.out.println("map's weight = " + this.x);
        System.out.println("map's height = " + this.y);
    }

    public void wallDestory() {
        int playerFacing = map.get(playerPosition.get(Y)).get(playerPosition.get(X));
        int dx = 0;
        int dy = 0;
        Integer type;
        if (playerFacing == PLAYER_FACE_UP || playerFacing == PLAYER_FACE_UP_ON_GOAL)
            dy = -1;
        else if (playerFacing == PLAYER_FACE_DOWN || playerFacing == PLAYER_FACE_DOWN_ON_GOAL)
            dy = 1;
        else if (playerFacing == PLAYER_FACE_LEFT || playerFacing == PLAYER_FACE_LEFT_ON_GOAL)
            dx = -1;
        else if (playerFacing == PLAYER_FACE_RIGHT || playerFacing == PLAYER_FACE_RIGHT_ON_GOAL)
            dx = 1;

        type = whatIsThere(playerPosition.get(X) + dx, playerPosition.get(Y) + dy);
        if (type == null) return;
        if (type == WALL)
            setXY(playerPosition.get(X) + dx, playerPosition.get(Y) + dy, ROAD);
    }
}

