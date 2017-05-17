import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Queue;
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
    	
    	new GameMap("maps/random");
    	ArrayList<ArrayList<Integer>> map = this.map;
    	System.out.println(map.size());
    	int dimensions = map.size();
    	
    	//random number generator
    	Random rand = new Random();
    	
    	//Randomly Place player, randomly place box, randomly place goal for box
    	//Find path between these items
    	//Player  = 0, Box = 1, Goal = 3
    	//will place a random road somewhere for fun i guess
    	for (int i = 0; i < 3; i++) {
    		//Generate random coordinates
        	//dimensions=10-1=9 is max while 0 is min
        	int x = rand.nextInt(dimensions - 1) + 0;
        	int y = rand.nextInt(dimensions - 1) + 0; 	
        	map.get(x).set(y, i);
    	}
    	
    	//Use BFS to generate path from player to box, then box to goal.
    	//Perform two BFSs????
    	
    	
    	
    	
    	
    }
    
    public void bfs(Coordinates start, Coordinates end) {
    	PriorityQueue<Coordinates> frontier = new PriorityQueue<Coordinates>(); //possible extension to A*
    	frontier.add(start);
    	HashMap<Coordinates, Coordinates> came_from = new HashMap<Coordinates, Coordinates>();
    	came_from.put(start, null);
    	
    	while (!frontier.isEmpty()) {
    		Coordinates current = frontier.poll();
    		findNeighbours(current, this.map, this.map.size());
    		//iterate through neighbours
    		
    	}
    }
    private static ArrayList<Coordinates> findNeighbours(Coordinates origin, ArrayList<ArrayList<Integer>> map, int dimensions) {
    	
    	ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
    	int col = origin.getCol();
    	int row = origin.getRow();

    	if(row - 1 > 0) {
    		Coordinates up = new Coordinates(map.get(row-1).get(col), col, row-1);
    		neighbours.add(up);
    	}
    	else if (row + 1 < dimensions - 1) {
    		Coordinates down = new Coordinates(map.get(row+1).get(col), col, row+1);
    		neighbours.add(down);
    	}
    	else if (col - 1 > 0) {
    		Coordinates left = new Coordinates(map.get(row).get(col-1), col-1, row);
    		neighbours.add(left);
    	} 
    	else if (col + 1 < dimensions - 1) {
    		Coordinates right = new Coordinates(map.get(row).get(col+1), col+1, row);
    		neighbours.add(right);
    	}
    	return neighbours;
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

