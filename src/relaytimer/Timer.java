/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package relaytimer;

import java.io.File;
import javafx.scene.control.TextArea;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author neilp_000
 */
public class Timer extends Thread {
    protected File soundFile;
    protected double iCount, iIncrement;
    protected long intervalInMiliSec;
    protected boolean exit;
    
    public Timer(File f, double i, double inc, float minInterval) {
        soundFile = f;
        iCount = i;
        iIncrement = inc;
        intervalInMiliSec=Math.round(minInterval*60.0*1000.0);
        exit = false;
    }
    
    @Override
    public void run() {
          try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);

                while (!exit) {
                    // load the sound into memory (a Clip)
                    try {
                        // play the sound clip
                        System.out.println("sound!    " + iCount + " Sleep...");
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        Thread.sleep(1000);
                        clip.stop();
                        Thread.sleep(intervalInMiliSec);
                        System.out.println("End");
                        iCount += iIncrement;
                    } catch (Exception e) {
                        System.out.println("Some Problem in loop... " + e);
                    }
                }
            } catch (Exception e) {
                System.out.println("Some problem..." + e);
            }
    }
    
    public void exit(){
        exit = true;
    }
}
