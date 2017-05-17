import javafx.embed.swing.JFXPanel;

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
public class GameStartMenu extends JFrame{
    private final String title = "Start menu";
    private final Dimension userScreenDimension;
    private final int width;
    private final int height;

    private BufferedImage background;

    public GameStartMenu (){
        userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        //width = userScreenDimension.width / 2;
        //height = userScreenDimension.height / 2;
        try {
            background = ImageIO.read(getClass().getResource("poster.png"));
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
        JButton easygameButton = new JButton("Start");
        easygameButton.setBounds(270, 370, 50, 50);
        easygameButton.addActionListener(this::actionPerformed);
        add(easygameButton);

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

    private void init (){
        JLabel welcomeText = new JLabel("Welcome to Game");
        welcomeText.setBounds(10, 10, 70, 70);
        add(welcomeText);

    }

    public void actionPerformed (ActionEvent e){
        System.out.println("Buttom is pressed");
    }
}
