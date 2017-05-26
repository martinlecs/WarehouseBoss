import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by b46qqq on 17/5/17.
 */
public class GameStartMenu extends JFrame implements Constants{

    private final String title = "Start menu";
    private final Dimension userScreenDimension;
    private final int width;
    private final int height;

    private BufferedImage background;

    private final String EASY_GAME_START = "easy game start";
    private final String GAME_EXIT = "game end";
    
    public GameStartMenu (){
        userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        //width = userScreenDimension.width / 2;
        //height = userScreenDimension.height / 2;
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

    private void setButton (){
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

    private void setBackground (BufferedImage background){
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, width, height, this);
            }
        });
    }

    public void actionPerformed (ActionEvent e){
        if (e.getActionCommand().equals(EASY_GAME_START)) {
            this.dispose();
            new GameEngine("maps/map.txt");
        } else if (e.getActionCommand().equals(GAME_EXIT)){
            System.out.println("game end !!! ");
            this.dispose();
        }

    }
}