import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;

/**
 * c-2911 Group Project
 * Members :: {
 * @author     Alan Wan     z5076302
 * @author     Allan Lai    z5117352
 * @author     Martin Le    z3466361
 * @author     Zhaohan Bao  z5114676
 *            }
 * @version 5.0
 *
 * Import image file from local storage, store them in a hashmap for later O(1) use
 *
 */


public class IconLibrary implements Constants{
    private HashMap<Integer, Image> icon;

    /**
     * constructor of class
     */
    public IconLibrary (){
        icon = new HashMap<>();
        try {
            icon.put (PLAYER, ImageIO.read(getClass().getResource("src/source/playerfacedown.png")));
            icon.put (BOX   , ImageIO.read(getClass().getResource("src/source/box.png")));
            icon.put (ROAD  , ImageIO.read(getClass().getResource("src/source/road.png")));
            icon.put (GOAL  , ImageIO.read(getClass().getResource("src/source/goal.png")));
            icon.put (WALL  , ImageIO.read(getClass().getResource("src/source/wall.png")));
            icon.put (GOAL_REACHED,  ImageIO.read(getClass().getResource("src/source/goalreached.png")));

            icon.put (PLAYER_FACE_UP, ImageIO.read(getClass().getResource("src/source/playerfaceup.png")));
            icon.put (PLAYER_FACE_DOWN, ImageIO.read(getClass().getResource("src/source/playerfacedown.png")));
            icon.put (PLAYER_FACE_LEFT, ImageIO.read(getClass().getResource("src/source/playerfaceleft.png")));
            icon.put (PLAYER_FACE_RIGHT, ImageIO.read(getClass().getResource("src/source/playerfaceright.png")));

            icon.put (PLAYER_FACE_UP_ON_GOAL, ImageIO.read(getClass().getResource("src/source/playerfaceup_on_goal.png")));
            icon.put (PLAYER_FACE_DOWN_ON_GOAL, ImageIO.read(getClass().getResource("src/source/playerfacedown_on_goal.png")));
            icon.put (PLAYER_FACE_LEFT_ON_GOAL, ImageIO.read(getClass().getResource("src/source/playerfaceleft_on_goal.png")));
            icon.put (PLAYER_FACE_RIGHT_ON_GOAL, ImageIO.read(getClass().getResource("src/source/playerfaceright_on_goal.png")));

        } catch (Exception e){
            System.out.println(e + "image not found!");
        }
    }

    /**
     * returns icon for use in drawing and redrawing
     * @param type index for relevant icon
     * @return icon for drawing
     * @pre input was valid
     * @post icon was served
     */
    public Image getIcon (int type){
        return icon.get(type);
    }

    /**
     * returns relevant icon (orientation of player)
     * @param type index for icon
     * @return icon for drawing
     * @pre input is valid
     * @post icon was served
     */
    public Image getPlayerDirectionIcon (Integer type){
        if (type == UP) return icon.get(PLAYER_FACE_UP);
        else if (type == DOWN) return icon.get(PLAYER_FACE_DOWN);
        else if (type == LEFT) return icon.get(PLAYER_FACE_LEFT);
        else if (type == RIGHT) return icon.get(PLAYER_FACE_RIGHT);
        return null;
    }
}
