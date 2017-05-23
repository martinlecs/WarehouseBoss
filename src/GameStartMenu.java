import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public class GameStartMenu extends JFrame{

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
            background = ImageIO.read(getClass().getResource("source/poster.png"));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        width = background.getWidth();
        height = background.getHeight();

        // initialise JFrame properties
        setBackground(background);
        setButton();
        setTitle(title);
        setLayout(null);
        setSize(width , height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setButton (){
        JButton easygameButton = new JButton("Start");
        easygameButton.setBounds(270, 390, 70, 30);
        easygameButton.addActionListener(this::actionPerformed);
        easygameButton.setActionCommand(EASY_GAME_START);
        easygameButton.setOpaque(false);
        easygameButton.setContentAreaFilled(false);
        easygameButton.setBorderPainted(false);
        add(easygameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(270, 515, 70, 30);
        exitButton.addActionListener(this::actionPerformed);
        exitButton.setActionCommand(GAME_EXIT);
        add(exitButton);
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
            new GameEngine("map.txt");
        } else if (e.getActionCommand().equals(GAME_EXIT)){
            System.out.println("game end !!! ");
            this.dispose();
        }

    }
}
