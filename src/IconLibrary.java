import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by b46qqq on 11/5/17.
 */
public class IconLibrary implements Constants{
    private HashMap<Integer, BufferedImage> icon;

    public IconLibrary (){
        icon = new HashMap<>();
        try {
            icon.put (PLAYER, ImageIO.read(getClass().getResource("source/r.png")));
            icon.put (BOX   , ImageIO.read(getClass().getResource("source/box.png")));
            icon.put (ROAD  , ImageIO.read(getClass().getResource("source/road.png")));
            icon.put (GOAL  , ImageIO.read(getClass().getResource("source/goal.png")));
            icon.put (WALL  , ImageIO.read(getClass().getResource("source/wall.png")));
            icon.put (GOAL_REACHED,  ImageIO.read(getClass().getResource("source/goalreached.png")));
            icon.put (PLAYER_ON_GOAL,  ImageIO.read(getClass().getResource("source/playerongoal.png")));
            // two (or more) icons to add,
            // one . when the box reached the goal
            // two . when the player is relocating .e.g the walking animation
            // three. when player is pushing the box.
            // icons can be GIF. if we know how to create one.

            // resource for implementation
            // http://stackoverflow.com/questions/5613120/java-animated-gif-without-using-a-jlabel
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public BufferedImage getIcon (int type){
        return icon.get(type);
    }
}
