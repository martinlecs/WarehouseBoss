import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
    	
    	if (!AutoGenerate) {
    		getCustomMap ("maps/map.txt");
    	} else {
    		getCustomMap ("maps/random");
    		
    	System.out.println("RANDOM GENERATION IN PROGRESS");

    	ArrayList<ArrayList<Integer>> map = this.map;
    	int dimensions = map.size();
    	ArrayList<Coordinates> list = new ArrayList<Coordinates>();
    	//random number generator
    	Random rand = new Random();
    	
    	//Randomly Place player, randomly place box, randomly place goal for box
    	//Find path between these items
    	//Player  = 0, Box = 1, Goal = 3
    	//will place a random road (2) somewhere for fun i guess
    	ArrayList<Coordinates> path1 = null;
    	ArrayList<Coordinates> path2 = null;
    	
    	HashSet<Coordinates> h = new HashSet<Coordinates>();
    	
    	int j = 0;
    	int col, row;
    	while (j <= 3) {
    		
    		//Generate random coordinates
    		if (j == BOX) {
    			//Make sure not to place the box against the wall
            	col = rand.nextInt(dimensions - 4) + 2;
            	row = rand.nextInt(dimensions - 4) + 2; 
            	this.goal++;
    		} else {
	        	col = rand.nextInt(dimensions - 2) + 1;
	        	row = rand.nextInt(dimensions - 2) + 1; 
    		}
    		
    		//Update playerPosition
    		if (j == PLAYER) {
    			this.playerPosition.add(X, col);
    			this.playerPosition.add(Y, row);
    		}
    		
    		
        	Coordinates n = new Coordinates (99, col, row); //Check that nothing has been placed on the same coordinates
        	
        	if (!h.contains(n)) {
               	h.add(n);
	        	map.get(row).set(col, j);
	        	list.add(new Coordinates(j, col, row));
	        	j++;
        	}
    	}

    	System.out.println("player="+list.get(0));
    	System.out.println("box="+list.get(1));
    	System.out.println("goal="+list.get(3));
    	path1 = bfs(list.get(0), list.get(1));
    	path2 = bfs(list.get(1), list.get(3));
    	
    	if (path1 == null) System.out.println("path1 sucks");
    	if (path2 == null) System.out.println("path2 sucks");
    	
    	//Generate roads from path data
    	for (Coordinates curr1 : path1) {
    		if (curr1.getSprite() != 0 && curr1.getSprite() != 1 && curr1.getSprite() != 3) 
    			map.get(curr1.getRow()).set(curr1.getCol(), 2);
    	}
    	for (Coordinates curr2 : path2) {
    		if (curr2.getSprite() != 0 && curr2.getSprite() != 1 && curr2.getSprite() != 3) 
    			map.get(curr2.getRow()).set(curr2.getCol(), 2);
    	}
    	//Add whitespace around box
    	addWhitespace(list.get(1), dimensions);
    	addWhitespace(list.get(3), dimensions);
    	
    	//re-add the player in case i got eaten HOTFIX
    	map.get(list.get(0).getRow()).set(list.get(0).getCol(), PLAYER);
    	
    	System.out.println("MAP GENERATION COMPLETE");
    	

    	
//    	//BFS UNIT TEST
//    	map.get(1).set(4, 0);
//    	map.get(5).set(0, 1);
//    	Coordinates player = new Coordinates (Constants.PLAYER, 4, 1);
//    	Coordinates box    = new Coordinates (Constants.BOX, 0, 5);
//    	System.out.println("Testing BFS");
//    	ArrayList<Coordinates> path = bfs(player, box);
//    	System.out.println(path);
    	
    	}	
    	
    }
    private void addWhitespace(Coordinates origin, int dimensions) {
    	int row = origin.getRow();
    	int col = origin.getCol();
    	
    	if (!(row - 1 == 0) && noNeighbours(row-1, col))
    		this.map.get(row-1).set(col, 2);															//up
    	if (!(row + 1 == dimensions - 1) && noNeighbours(row+1, col))
    		this.map.get(row+1).set(col, 2);															//down
    	if (!(col - 1 == 0) && noNeighbours(row, col-1))
    		this.map.get(row).set(col-1, 2);															//left
    	if (!(col + 1 == dimensions - 1) && noNeighbours(row, col+1))
    		this.map.get(row).set(col+1, 2);															//right
    	if(!(row - 1 == 0) && !(col - 1 == 0) && noNeighbours(row-1, col-1))
    		this.map.get(row-1).set(col-1, 2);															//up-left
    	if(!(row + 1 == dimensions - 1) && !(col - 1 == 0) && noNeighbours(row+1, col-1))
    		this.map.get(row+1).set(col-1, 2);															//down-left
    	if(!(row + 1 == dimensions - 1) && !(col + 1 == dimensions - 1) && noNeighbours(row+1, col+1))
    		this.map.get(row-1).set(col+1, 2);															//up-right
    	if(!(row + 1 == dimensions - 1) && !(col + 1 == dimensions - 1) && noNeighbours(row+1, col+1))
    		this.map.get(row+1).set(col+1, 2);															//down-right
    }
    
    private boolean noNeighbours (int row, int col) {
    	//Eats the player rip
    	if (this.map.get(row).get(col) == ROAD || this.map.get(row).get(col) == WALL) {
    		return true;
    	}
    	return false;
    }
    
    public ArrayList<Coordinates> bfs (Coordinates start, Coordinates end) {
    	Queue<Coordinates> frontier = new LinkedList<Coordinates>(); //possible extension to A*
    	frontier.add(start);
    	HashMap<Coordinates, Coordinates> came_from = new HashMap<Coordinates, Coordinates>();
    	came_from.put(start, null);
    	boolean flag = false;
    	
    	while (!frontier.isEmpty()) {
    		Coordinates current = frontier.poll();
    		//System.out.println("current=" + current);
    		
    		if (current.equals(end)) {
    			//Basically pops everything off queue and terminates, not actually going into this
    			//System.out.println("loop="+ current);
    			flag = true;
    			break;
    		}
    		
    		ArrayList<Coordinates> neighbours = findNeighbours(current, this.map, this.map.size());
    		//System.out.println("neigh" + neighbours);
    		for (Coordinates curr : neighbours) {
    			//System.out.println("curr=" + curr);
    			if(!came_from.containsKey(curr)) {
    				frontier.add(curr);
    				came_from.put(curr, current);
    			}
    		}
    	}
    	if (flag) {
    		return getPath (start, end, came_from);
    	}
    	return null;
    }
    
    private static ArrayList<Coordinates> getPath (Coordinates start, Coordinates end, HashMap<Coordinates, Coordinates> came_from) {
		//generate path
		Coordinates current = end;
		ArrayList<Coordinates> path = new ArrayList<Coordinates>();
		path.add(current);
		//System.out.println(came_from.get(end) + " fuck me");
		while (!current.equals(start)) {
			current = came_from.get(current);
			path.add(current);
		}
		//Remove start and end
		Collections.reverse(path);
//		path.remove(0);
//		path.remove(path.size());
		
		return path;
    }
    private static ArrayList<Coordinates> findNeighbours(Coordinates origin, ArrayList<ArrayList<Integer>> map, int dimensions) {
    	
    	ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
    	int col = origin.getCol();
    	int row = origin.getRow();
    	//System.out.println("dimen=" + dimensions);
    	
    	//System.out.println("col=" + col + ", row=" + row);

    	if(row - 1 >= 0) {
    		Coordinates up = new Coordinates(map.get(row-1).get(col), col, row-1);
    		//System.out.println("go up");
    		neighbours.add(up);
    	}
    	if (row + 1 <= dimensions - 1) {
    		Coordinates down = new Coordinates(map.get(row+1).get(col), col, row+1);
    		//System.out.println(down);
    		neighbours.add(down);
    	}
    	if (col - 1 >= 0) {
    		Coordinates left = new Coordinates(map.get(row).get(col-1), col-1, row);
    		neighbours.add(left);
    	} 
    	if (col + 1 <= dimensions - 1) {
    		Coordinates right = new Coordinates(map.get(row).get(col+1), col+1, row);
    		//System.out.println(right);
    		neighbours.add(right);
    	}
    	return neighbours;
    }

    private void getCustomMap (String filename) {
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
            if (in != null) in.close();

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

