import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PauseMenu extends JFrame implements Constants{
    
    private final String title = "Pause Menu";
    private final Dimension userScreenDimension;
    private final int width;
    private final int height;
    private SoundLibrary soundPlayer;

    private BufferedImage background;

    private final String EASY_GAME_START = "easy game start";
    private final String GAME_EXIT = "game end";
    
    private boolean resume;

    private enum Actions {
        EASY_GAME_START,
        HARD_GAME_START,
        LOAD_GAME,
        EXIT
    }
    
    public PauseMenu (GameMap map, GameEngine ge){
        userScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        
        try {
            background = ImageIO.read(getClass().getResource("source/pauseMenu.png"));
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
        
        soundPlayer = new SoundLibrary();
        soundPlayer.makeSound(SUB_MUSIC, true, true);
        
        resume = false;
    }

    private void setButton (){
        JButton easygameButton = new JButton("Resume");
        easygameButton.setBounds(227, 120, 145, 80);
        easygameButton.addActionListener(this::actionPerformed);
        easygameButton.setActionCommand(EASY_GAME_START);
        add(easygameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(227 , 450, 145, 80);
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

    private void init (){
        JLabel welcomeText = new JLabel("Welcome to Game");
        welcomeText.setBounds(10, 10, 70, 70);
        add(welcomeText);

    }

    public void actionPerformed (ActionEvent e){
    	// turn off music
        soundPlayer.makeSound(SUB_MUSIC, false, false);
    	
        if (e.getActionCommand().equals(EASY_GAME_START)) {
            this.dispose();
            this.resume = true;
        } else if (e.getActionCommand().equals(GAME_EXIT)) {
            System.out.println("game end !!! ");
            this.dispose();
            new GameStartMenu();
        }

    }
    
    public boolean getResume() {
    	if(resume) {
    		return true;
    	}
    	
    	return false;
    }
    
    
}