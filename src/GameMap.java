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
    private final Integer NUMBER = 3;
    /**
     * Randomly generates a map or returns a premade map.
     * @param AutoGenerate		Boolean value: True to invoke auto-generation, False to load a premade map.
     */
    public GameMap (boolean AutoGenerate){
    	
    	if (!AutoGenerate) {
    		getCustomMap ("maps/random2");
    	} else {
    		getCustomMap ("maps/random");
    		
    		
    	//Alternate strategy, start with empty room. Add goals and random walls. Use pulling method to drag boxes around and deposit them around the room
    	System.out.println("RANDOM GENERATION IN PROGRESS");

    	ArrayList<ArrayList<Integer>> map = this.map;
    	int NumCols = map.get(0).size();
    	int NumRows = map.size();
    	
    	System.out.println(x);
    	ArrayList<Coordinates> list = new ArrayList<Coordinates>();
    	//random number generator
    	Random rand = new Random();
    	
    	//Randomly Place player, randomly place box, randomly place goal for box
    	//Find path between these items
    	//Player  = 0, Box = 1, Goal = 3
    	//will place a random road (2) somewhere for fun i guess
    	
    	HashSet<Coordinates> h = new HashSet<Coordinates>();
    	
    	int j = 0;
    	int col, row;
    	while (j <= NUMBER*2 -1) {
    		int k = j%2; //will produce either 1s or zeros
//    		System.out.println(k); 
    		
    		if (k == 0) {
    			//Make a box
    			//Make sure not to place the box against the wall
            	col = rand.nextInt(NumCols - 4) + 2;			//Might need to tweak these numbers 3.02PM MAY 23
            	row = rand.nextInt(NumRows - 4) + 2; 
    		} else {
    			//Make a goal
	        	col = rand.nextInt(NumCols - 2) + 1;
	        	row = rand.nextInt(NumRows - 2) + 1; 
    		}
    		
        	Coordinates n = new Coordinates (99, col, row); //Check that nothing has been placed on the same coordinates
        	
        	if (!h.contains(n)) {
               	h.add(n);
               	if(k == 0) {
               		//Place a box
	        		map.get(row).set(col, BOX);
	        		Coordinates n1 = new Coordinates(BOX, col, row);
//	        		list.add(new Coordinates(BOX, col, row));
	        		list.add(n1);
	        		System.out.println("box="+ n1);
	        		
               	} else {
               		//Place a goal
               		map.get(row).set(col, GOAL);
	        		Coordinates n2 = new Coordinates(GOAL, col, row);
//    	        	list.add(new Coordinates(GOAL, col, row));
	        		list.add(n2);
	        		System.out.println("goal="+n2);
	        		this.goal++;
    	        	
               	}
	        	j++;
        	}
    	}
    	
    	//Randomly add player to game
    	while (true) {
	    	col = rand.nextInt(NumCols - 2) + 1;
	    	row = rand.nextInt(NumRows - 2) + 1; 
	    	
	    	Coordinates n = new Coordinates (99, col, row); 
	    	if (!h.contains(n)) {
	    		map.get(row).set(col, PLAYER);
	    		System.out.println("col="+col + " " + "row=" +row);
	    		this.playerPosition.add(X, col);
	    		this.playerPosition.add(Y, row);
	    		break;
	    	}
    	}
    	
    	ArrayList<ArrayList<Coordinates>> pathList = new ArrayList<ArrayList<Coordinates>>();
    	
    	//perform bfs on every two objects
    	for (int counter = 0; counter < NUMBER*2 -1; counter++) {
    		pathList.add(bfs(list.get(counter), list.get(counter+1)));
    		counter++;
    	}
    	
    	for(int temp = 0; temp < pathList.size(); temp++) {
	        	for (Coordinates curr : pathList.get(temp)) {
	    		if (curr.getSprite() != PLAYER && curr.getSprite() != BOX && curr.getSprite() != GOAL) 
	    			map.get(curr.getRow()).set(curr.getCol(), 2);
	    	}
    	}
    	
    	//Add whitespace around boxes and goals
    	for (int i = 0; i < NUMBER*2; i++) {
	    	addWhitespace(list.get(i), map);
    	}
    	
    	//boxes are stored in lists
    	ArrayList<ArrayList<Coordinates>> playerToBoxes = new ArrayList<ArrayList<Coordinates>>();
    	Coordinates playerPos = new Coordinates(PLAYER, playerPosition.get(0), playerPosition.get(1));
    	
    	//Find path between player and every box
    	for (int box = 0; box < NUMBER*2 - 1; box++) {
    		playerToBoxes.add(bfs(playerPos, list.get(box)));
    		box++;
    	}
    	//Put whitespace in the path
    	for(int temp = 0; temp < playerToBoxes.size(); temp++) {
        	for (Coordinates curr : playerToBoxes.get(temp)) {
    		if (curr.getSprite() != PLAYER && curr.getSprite() != BOX && curr.getSprite() != GOAL) 
    			map.get(curr.getRow()).set(curr.getCol(), 2);
    	}
	}
    	
    	
    	System.out.println("MAP GENERATION COMPLETE");
    	

//    	//BFS UNIT TEST
//    	map.get(1).set(4, 0);
//    	map.get(5).set(0, 1);
//    	Coordinates player = new Coordinates (Constants.PLAYER, 4, 1);
//    	Coordinates box    = new Coordinates (Constants.BOX, 0, 5);
//    	Coordinates box = list.get(0);
//    	Coordinates goal = list.get(1);
//    	System.out.println("Testing BFS");
//    	ArrayList<Coordinates> path = bfs(box, goal); //BFS FAILS!!
//    	System.out.println(path);
    	
    	}	
    	
    }
    /**
     * Places roads around a box or a goal
     * @param origin
     * @param dimensions
     */
    private void addWhitespace(Coordinates origin, ArrayList<ArrayList<Integer>> map) {
    	int row = origin.getRow();
    	int col = origin.getCol();
    	
    	int NumCols = map.get(0).size();
    	int NumRows = map.size();
    	
    	//UP
    	if (!(row - 1 == 0) && noNeighbours(row-1, col)) {
    		this.map.get(row-1).set(col, 2);
    	}
    	
    	//DOWN
    	if(!(row+1 == NumRows - 1) && noNeighbours(row+1, col)) {
    		this.map.get(row+1).set(col, 2);
    	}
    	
    	//LEFT
    	if (!(col - 1 == 0) && noNeighbours(row, col-1)) {
    		this.map.get(row).set(col-1, 2);	
    	}
    	
    	//RIGHT
    	if (!(col + 1 == NumCols - 1) && noNeighbours(row, col+1)) {
    		this.map.get(row).set(col+1, 2);	
    	}
    	
    	//UP-LEFT
    	if(!(row - 1 == 0) && !(col - 1 == 0) && noNeighbours(row-1, col-1)) {
    		this.map.get(row-1).set(col-1, 2);		
    	}
    	
    	//UP-RIGHT
    	if(!(row - 1 == 0) && !(col + 1 == NumCols - 1) && noNeighbours(row-1, col+1)) {
    		this.map.get(row-1).set(col+1, 2);		
    	}
    	
    	//DOWN-LEFT
    	if(!(row + 1 == NumRows - 1) && !(col - 1 == 0) && noNeighbours(row+1, col-1)) {
    		this.map.get(row+1).set(col-1, 2);	
    	}
    	
    	//DOWN-RIGHT
    	if(!(row + 1 == NumRows- 1) && !(col + 1 == NumCols - 1) && noNeighbours(row+1, col+1)) {
    		this.map.get(row+1).set(col+1, 2);
    	}
    }
    
    /**
     * Checks that there are boxes, players or goals directly connected to the current square.
     * @param row
     * @param col
     * @return
     */
    private boolean noNeighbours (int row, int col) {
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
//    		System.out.println("current=" + current);
    		
    		if (current.equals(end)) {
    			//Basically pops everything off queue and terminates, not actually going into this
    			//System.out.println("loop="+ current);
    			flag = true;
    			break;
    		}
    		
    		ArrayList<Coordinates> neighbours = findNeighbours(current, this.map);
//    		System.out.println("neigh" + neighbours);
    		for (Coordinates curr : neighbours) {
//    			System.out.println("curr=" + curr);
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
    private static ArrayList<Coordinates> findNeighbours(Coordinates origin, ArrayList<ArrayList<Integer>> map) {
    	
    	ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
    	int col = origin.getCol();
    	int row = origin.getRow();
    	
    	int NumCols = map.get(0).size();
    	int NumRows = map.size();
    	
//    	System.out.println("col=" + col + ", row=" + row);

    	if(row - 1 >= 0) {
    		Coordinates up = new Coordinates(map.get(row-1).get(col), col, row-1);
    		//System.out.println("go up");
    		neighbours.add(up);
    	}
    	if (row + 1 <= NumRows- 1) {
    		Coordinates down = new Coordinates(map.get(row+1).get(col), col, row+1);
    		//System.out.println(down);
    		neighbours.add(down);
    	}
    	if (col - 1 >= 0) {
    		Coordinates left = new Coordinates(map.get(row).get(col-1), col-1, row);
    		neighbours.add(left);
    	} 
    	if (col + 1 <= NumCols - 1) {
    		Coordinates right = new Coordinates(map.get(row).get(col+1), col+1, row);
//    		System.out.println(right);
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

