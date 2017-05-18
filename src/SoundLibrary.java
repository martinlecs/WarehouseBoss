import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundLibrary implements Constants {
    private HashMap<Integer, File> sound;
    private Clip currClip; 

    public SoundLibrary (){
        sound = new HashMap<>();
        currClip = null;
        try {
            sound.put(SOUND_MOVE, new File(getClass().getResource("audio/test.wav").toURI()));
            sound.put(MAIN_MUSIC, new File(getClass().getResource("audio/menu.wav").toURI()));
            sound.put(SOUND_ATTACK, new File(getClass().getResource("audio/attack.wav").toURI()));
            sound.put(SOUND_BOX_ON_GOAL, new File(getClass().getResource("audio/onGoal.wav").toURI()));
            sound.put(SUB_MUSIC, new File(getClass().getResource("audio/menu1.wav").toURI()));
            
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void makeSound(int key, boolean on, boolean loop) {
    	
    	File toPlay = this.getAudioFile(key);
    	
    	try {
    		Clip c1 = AudioSystem.getClip();
    		c1.open(AudioSystem.getAudioInputStream(toPlay));
    		
    		if(on) {
    		    c1.start();
    		    if(loop) {
    		        c1.loop(c1.LOOP_CONTINUOUSLY);
        		    this.currClip = c1;
    		    }
    		}
    		else { 
    			if(currClip != null) {
    			    currClip.stop();
    			}
    		}
    		
    	} catch (Exception e){
    		System.out.println(e);
    	}
    }
    

    public File getAudioFile(int key){
        return sound.get(key);
    }
}
