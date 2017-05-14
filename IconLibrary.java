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
            icon.put (PLAYER, ImageIO.read(getClass().getResource("player.png")));
            icon.put (BOX   , ImageIO.read(getClass().getResource("box.png")));
            icon.put (ROAD  , ImageIO.read(getClass().getResource("road.png")));
            icon.put (GOAL  , ImageIO.read(getClass().getResource("goal.png")));
            icon.put (WALL  , ImageIO.read(getClass().getResource("wall.png")));
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
