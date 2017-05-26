import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by b46qqq on 11/5/17.
 */
public class IconLibrary implements Constants{
    private HashMap<Integer, Image> icon;

    public IconLibrary (){
        icon = new HashMap<>();
        try {
            icon.put (PLAYER, ImageIO.read(getClass().getResource("source/playerfacedown.png")));
            icon.put (BOX   , ImageIO.read(getClass().getResource("source/box.png")));
            icon.put (ROAD  , ImageIO.read(getClass().getResource("source/road.png")));
            icon.put (GOAL  , ImageIO.read(getClass().getResource("source/goal.png")));
            icon.put (WALL  , ImageIO.read(getClass().getResource("source/wall.png")));
            icon.put (GOAL_REACHED,  ImageIO.read(getClass().getResource("source/goalreached.png")));

            icon.put (PLAYER_FACE_UP, ImageIO.read(getClass().getResource("source/playerfaceup.png")));
            icon.put (PLAYER_FACE_DOWN, ImageIO.read(getClass().getResource("source/playerfacedown.png")));
            icon.put (PLAYER_FACE_LEFT, ImageIO.read(getClass().getResource("source/playerfaceleft.png")));
            icon.put (PLAYER_FACE_RIGHT, ImageIO.read(getClass().getResource("source/playerfaceright.png")));

            icon.put (PLAYER_FACE_UP_ON_GOAL, ImageIO.read(getClass().getResource("source/playerfaceup_on_goal.png")));
            icon.put (PLAYER_FACE_DOWN_ON_GOAL, ImageIO.read(getClass().getResource("source/playerfacedown_on_goal.png")));
            icon.put (PLAYER_FACE_LEFT_ON_GOAL, ImageIO.read(getClass().getResource("source/playerfaceleft_on_goal.png")));
            icon.put (PLAYER_FACE_RIGHT_ON_GOAL, ImageIO.read(getClass().getResource("source/playerfaceright_on_goal.png")));

        } catch (Exception e){
            System.out.println(e + "image not found!");
        }
    }

    public Image getIcon (int type){
        return icon.get(type);
    }

    public Image getPlayerDirectionIcon (Integer type){
        if (type == UP) return icon.get(PLAYER_FACE_UP);
        else if (type == DOWN) return icon.get(PLAYER_FACE_DOWN);
        else if (type == LEFT) return icon.get(PLAYER_FACE_LEFT);
        else if (type == RIGHT) return icon.get(PLAYER_FACE_RIGHT);
        return null;
    }
}
