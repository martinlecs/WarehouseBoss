import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

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
                        playerPosition.add(X, i);
                        playerPosition.add(Y, y);
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
}

