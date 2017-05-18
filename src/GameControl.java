import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by b46qqq on 13/5/17.
 */
public class GameControl implements KeyListener{

    private GameEngine engine;

    public GameControl (GameEngine engine){
        this.engine = engine;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                System.out.println("up arrow key is pressed");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down arrow key is pressed");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left arrow key is pressed");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right arrow key is pressed");
                break;
            default: //Do NOTHING
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}