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
 * Result menu after game is completed
 * This class extends JFrame inorder to create a separate window
 * and display components to help the user navigate through the game.
 *
 */

public class ResultMenu extends JFrame {

    private final String title = "Remhouse Boss";
    private final Dimension userScreenDimension;
    private final int width;
    private final int height;

    private BufferedImage background;

    private final String EASY_GAME_START = "easy game start";
    private final String GAME_EXIT = "game end";
    private final String MAIN_MENU = "returning to main menu";

    /**
     * constructor of class
     * creates new window after previous game menu was destroyed
     */
    public ResultMenu (int moves){
        userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        //width = userScreenDimension.width / 2;
        //height = userScreenDimension.height / 2;
        try {
            background = ImageIO.read(getClass().getResource("src/source/poster3.png"));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        width = background.getWidth();
        height = background.getHeight();

        // initialise JFrame properties
        setBackground(background);
        setButton(moves);
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
     * creates and binds buttons and actionlistners for use in the menu 
     * used to complete the level, creates and binds buttons 
     * and actionlisteners for use in the menu 
     * @post buttons are created
     */
    private void setButton (int moves){
    	// number of steps button
    	// does nothing but display move count
    	JButton moveCount = new JButton(Integer.toString(moves));
    	moveCount.setFont(new Font("Arvo", Font.PLAIN, 30));
    	moveCount.setForeground(Color.white);
    	
    	moveCount.setBounds(250, 230, 230, 50);
    	moveCount.setOpaque(false);
    	moveCount.setContentAreaFilled(false);
    	moveCount.setBorderPainted(false);
    	moveCount.setFocusPainted(false);
    	add(moveCount);
    	
        //next level button
    	JButton nextLevel = new JButton();
        nextLevel.setBounds(170, 300, 230, 50);
        nextLevel.addActionListener(this::actionPerformed);
        nextLevel.setActionCommand(EASY_GAME_START);

        nextLevel.setOpaque(false);
        nextLevel.setContentAreaFilled(false);
        nextLevel.setBorderPainted(false);

        nextLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextLevel.setBorderPainted(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextLevel.setBorderPainted(false);
            }
        });
        add(nextLevel);

        //main menu button
        JButton mainMenu = new JButton();
        mainMenu.setBounds(170, 370, 230, 50);
        mainMenu.addActionListener(this::actionPerformed);
        mainMenu.setActionCommand(MAIN_MENU);

        mainMenu.setOpaque(false);
        mainMenu.setContentAreaFilled(false);
        mainMenu.setBorderPainted(false);

        mainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mainMenu.setBorderPainted(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mainMenu.setBorderPainted(false);
            }
        });
        add(mainMenu);

        //exiting button
        JButton exit = new JButton();
        exit.setBounds(170, 440, 230, 50);
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
     * sets bg of menu
     * @param background image used for bg of menu
     * @pre image file is found in source folder and exists
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
     * assigns actions for each of the button
     * @param e actions for each button from listners
     * @pre action e was valid
     * @post actions were performed
     */
    public void actionPerformed (ActionEvent e){
        if (e.getActionCommand().equals(EASY_GAME_START)) {
        	this.dispose();
            new GameEngine("src/maps/map.txt");
        } else if (e.getActionCommand().equals(GAME_EXIT)){
            this.dispose();
            System.exit(0);
        } else if (e.getActionCommand().equals(MAIN_MENU)){
        	this.dispose();
        	new GameStartMenu();
        }

    }
}
