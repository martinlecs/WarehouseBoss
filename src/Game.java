/**
 * Created by b46qqq on 10/5/17.
 */

public class Game implements Constants{

    public static void main (String[] args){

        GameMap map = new GameMap("map.txt");
        GameGraphics graphics = new GameGraphics("Game -test01", map);
    }
}
