/**
 * Created by b46qqq on 9/5/17.
 */
public class Map {
    private int[][] map;
    private int[] playerPosition;

    final private int PLAYER = 1;
    final private int WALL   = -1;
    final private int BOX    = 2;
    final private int GOAL   = 3;
    final private int ROAD   = 0;
    final private int X      = 0;
    final private int Y      = 1;

    public Map (){
        map = new int[][]{
                { WALL , WALL , WALL , WALL , WALL , ROAD , ROAD , ROAD , ROAD },
                { WALL ,PLAYER, ROAD , ROAD , WALL , ROAD , ROAD , ROAD , ROAD },
                { WALL , ROAD ,  BOX ,  BOX , WALL , ROAD , WALL , WALL , WALL },
                { WALL , ROAD ,  BOX , ROAD , WALL , ROAD , WALL , GOAL , WALL },
                { WALL , WALL , WALL , ROAD , WALL , WALL , WALL , GOAL , WALL },
                { ROAD , WALL , WALL , ROAD , ROAD , ROAD , ROAD , GOAL , WALL },
                { ROAD , WALL , ROAD , ROAD , ROAD , WALL , ROAD , ROAD , WALL },
                { ROAD , WALL , ROAD , ROAD , ROAD , WALL , WALL , WALL , WALL },
                { ROAD , WALL , WALL , WALL , WALL , WALL , ROAD , ROAD , ROAD },
        };
        playerPosition = new int[2];
        setPlayerPostion();
    }

    public int getHeight (){
        return map.length;
    }

    public int getWidth (){
        return map[0].length;
    }

    public void setPlayerPostion (){
        for (int x = 0; x < map.length; x ++){
            for (int y = 0; y < map[x].length; y ++){
                if (map[x][y] == PLAYER){
                    playerPosition[X] = x;
                    playerPosition[Y] = y;
                    return;
                }
            }
        }
    }

    public boolean playerMoveUp(){
        int x = playerPosition[X];
        int y = playerPosition[Y];
        playerPosition[X] = x++;
        map[x][y] = ROAD;
        map[x+1][y] = PLAYER;
        return true;
    }

    public int[] getPlayerPostion(){
        return playerPosition;
    }

    public int[][] getMap (){
        return map;
    }
}