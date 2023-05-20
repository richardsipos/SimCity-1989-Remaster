package KukaPest.Model.Helper;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    Clip clip;
    String[] soundsname = new String[1];


    public Sound(){
        soundsname[0] = "src/main/java/KukaPest/Assets/nope.wav";
    }

    public void setFile(int i){
        try{
            File file = new File(soundsname[i]);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        }catch(Exception e){
            System.out.println("rossz");

        }

    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}

