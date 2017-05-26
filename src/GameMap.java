import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;
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
    		getCustomMap ("src/maps/random3");
    	} else {
    		getCustomMap ("src/maps/random");

	    	ArrayList<ArrayList<Integer>> map = this.map;
	    	int NumCols = map.get(0).size();
	    	int NumRows = map.size();
	    	
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
	    		int k = j%2;
	    		
	    		if (k == 0) {
	    			//Make a box
	    			//Make sure not to place the box against the wall
	            	col = rand.nextInt(NumCols - 4) + 2;
	            	row = rand.nextInt(NumRows - 4) + 2; 
	    		} else {
	    			//Make a goal
		        	col = rand.nextInt(NumCols - 2) + 1;
		        	row = rand.nextInt(NumRows - 2) + 1; 
	    		}
	    		
	        	Coordinates n = new Coordinates (99, col, row); 
	        	
	        	//Check that nothing has been placed on the same coordinates
	        	if (!h.contains(n)) {
	               	h.add(n);
	               	if(k == 0) {
	               		//Place a box
		        		map.get(row).set(col, BOX);
		        		list.add(new Coordinates(BOX, col, row));	
	               	} else {
	               		//Place a goal
	               		map.get(row).set(col, GOAL);
	    	        	list.add(new Coordinates(GOAL, col, row));
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
		    		if (curr.getSprite() != PLAYER && curr.getSprite() != BOX && curr.getSprite() != GOAL) {
		    			map.get(curr.getRow()).set(curr.getCol(), 2);
		        	}
	        	}
	    	}
    	
    	writeMap("src/maps/random3", this.map);
    	
    	} //part of else block from the beginning
    }
    
    private void writeMap(String filename, ArrayList<ArrayList<Integer>> map) {
    	try {
    		
    		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
    		
    		for (int i = 0; i < map.size(); i++) {
    			for(int j = 0; j < map.get(0).size(); j++) {
    				bw.write(map.get(i).get(j) + " ");
    			}
    			bw.newLine();
    		}
    		bw.flush();
    		if (bw != null) bw.close();
    	} catch (IOException e) {
    		System.err.println("Error: " + e.getMessage());
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
    
    private ArrayList<Coordinates> bfs (Coordinates start, Coordinates end) {
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
		while (!current.equals(start)) {
			current = came_from.get(current);
			path.add(current);
		}
		//Remove start and end
		Collections.reverse(path);
		
		return path;
    }
    private static ArrayList<Coordinates> findNeighbours(Coordinates origin, ArrayList<ArrayList<Integer>> map) {
    	
    	ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
    	int col = origin.getCol();
    	int row = origin.getRow();
    	
    	int NumCols = map.get(0).size();
    	int NumRows = map.size();
    	
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
            if (in != null) in.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private Integer playerDirectionConvert (int d){
        int direction = PLAYER;
        if (d == UP) direction = PLAYER_FACE_UP;
        else if (d == DOWN) direction = PLAYER_FACE_DOWN;
        else if (d == LEFT) direction = PLAYER_FACE_LEFT;
        else if (d == RIGHT) direction = PLAYER_FACE_RIGHT;
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

    public void updatePlayerDirection (Integer direction){
        if (direction == null) return;
        int x, y;
        x = playerPosition.get(X);
        y = playerPosition.get(Y);
        Integer type = null;
        if (isPlayerOnGoal()) {
            if (direction == UP) type = PLAYER_FACE_UP_ON_GOAL;
            else if (direction == DOWN) type = PLAYER_FACE_DOWN_ON_GOAL;
            else if (direction == LEFT) type = PLAYER_FACE_LEFT_ON_GOAL;
            else if (direction == RIGHT) type = PLAYER_FACE_RIGHT_ON_GOAL;
        }else {
            type = playerDirectionConvert(direction);
        }
        setXY(x, y, type);
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

    public Integer whatIsThere (int x, int y){
        if (x < 0 || x > this.x - 1) return null;
        if (y < 0 || y > this.y - 1) return null;
        return map.get(y).get(x);
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
//    
//	public void setMap(ArrayList<ArrayList<Integer>> map) {
//		this.map = map;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public void setGoal(int goal) {
//		this.goal = goal;
//	}
//
//	public void setPlayerPosition(ArrayList<Integer> playerPosition) {
//		this.playerPosition = playerPosition;
//	}
////
////	@Override
////	public GameMap clone() {
////		
////		GameMap g = new GameMap(false);
////		g.setMap(this.deepCopyMap(this.map));
////		g.setX(this.getX());
////		g.setY(this.getY());
////		g.setGoal(this.getGoal());
////		g.setPlayerPosition(deepCopyPlayerPosition(this.playerPosition));
////
////		return g;
////
////	}
////	private ArrayList<ArrayList<Integer>> deepCopyMap (ArrayList<ArrayList<Integer>> map) {
////		if (map != null) {
////			
////			ArrayList<ArrayList<Integer>> n = new ArrayList<ArrayList<Integer>>();
////			for (int i = 0; i < map.size(); i++) {
////				ArrayList<Integer> array = new ArrayList<Integer>();
////				for(int j = 0; j < map.get(i).size(); j++) {
////					array.add(map.get(i).get(j));
////				}
////				n.add(array);
////			}
////			return n;
////		} 
////		return null;
////	}
////	
////	private ArrayList<Integer> deepCopyPlayerPosition (ArrayList<Integer> playerPosition) {
////		if (playerPosition != null) {
////			ArrayList<Integer> array = new ArrayList<Integer>();
////			for(int i = 0; i < playerPosition.size(); i++) {
////				array.add(playerPosition.get(i));
////			}
////			return array;
////		}
////		return null;
////	}
	
    
    
}

