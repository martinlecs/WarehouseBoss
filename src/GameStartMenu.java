import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
 * This class extends JFrame inorder to create a separate window
 * and display components to help the user navigate through the game.
 *
 */


public class GameStartMenu extends JFrame implements Constants{

    private final String title = "Remhouse Boss";
    private final Dimension userScreenDimension;
    private final int width;
    private final int height;

    private BufferedImage background;

    private final String EASY_GAME_START = "easy game start";
    private final String GAME_EXIT       = "game end";

    /**
     * Constructor
     * Load an image from local storage set that as the background.
     * And add button components to it.
     */
    public GameStartMenu (){
        userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            background = ImageIO.read(getClass().getResource("src/source/poster.png"));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        width = background.getWidth();
        height = background.getHeight();

        // initialise JFrame properties
        setBackground(background);
        setButton();
        // init();  forget about this
        setTitle(title);
        setLayout(null);
        //pack();
        setSize(width , height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Creates buttons and its functionality when actived.
     * @pre the frame exists, visiable and valid
     * @post buttons are created
     */
    private void setButton (){
        //game start button
    	JButton play = new JButton();
        play.setBounds(196, 264, 215, 50);
        play.addActionListener(this::actionPerformed);
        play.setActionCommand(EASY_GAME_START);

        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);

        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                play.setBorderPainted(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                play.setBorderPainted(false);
            }
        });
        add(play);

        //exit button
        JButton exit = new JButton();
        exit.setBounds(196, 322, 215, 50);
        exit.addActionListener(this::actionPerformed);
        exit.setActionCommand(GAME_EXIT);

        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);

        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exit.setBorderPainted(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exit.setBorderPainted(false);
            }
        });
        add(exit);
    }

    /**
     * sets bg image of menu
     * @param background image found in source folder
     * @pre input exists and is found
     */
    private void setBackground (BufferedImage background){
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, width, height, this);
            }
        });
    }

    /**
     * sets actions from actionlistners
     * @param e event from actionlisteners
     * @pre input is valid
     * @post relevant actions are performed
     */
    public void actionPerformed (ActionEvent e){
        if (e.getActionCommand().equals(EASY_GAME_START)) {
        	System.out.println("starting new game ");
        	this.dispose();
            new GameEngine("src/maps/map.txt");
        } else if (e.getActionCommand().equals(GAME_EXIT)){
            System.out.println("game end !!! ");
            this.dispose();
        }

    }
}
