/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relaytimer;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author Zhdanova PR 01
 */
public class RelayTimer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String COMPILEDATE = "20120104";
        String REVISION = "5";
        double intI = 0, intInc = 0;
        
        // TODO code application logic here
        if (args.length != 4) {
            System.out.println("\njava -jar RelayTimer.jar [path\\soundfile.wav] [min. interval] [starting #] [increment]");
            System.out.println("Version: 1.3." + "b" + "0");
            System.out.println("# of Compiles Since Last Revision" + REVISION);
            System.out.println("Compiled On: " + COMPILEDATE);
            System.out.println("By Neil A. Patel");
            
        } else {
            long intervalInMiliSec = Math.round(Float.valueOf(args[1])*60.0*1000.0);
            System.out.println("Timer App Started.");
            System.out.println("Arguments:  " + args[0] + "  " + args[1] + " " + args[2]);
            intI = Double.valueOf(args[2]);
            intInc = Double.valueOf(args[3]);
            
            try {
                File soundFile = new File(args[0]);
                AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);

                while (true) {
                    // load the sound into memory (a Clip)
                    try {
                        // play the sound clip
                        System.out.println("sound!    " + intI);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        Thread.sleep(1000);
                        clip.stop();
                        System.out.print("Sleep...");
                        Thread.sleep(intervalInMiliSec);
                        System.out.println("End");
                        intI += intInc;
                    } catch (Exception e) {
                        System.out.println("Some Problem in loop... " + e);
                    }
                }
            } catch (Exception e) {
                System.out.println("Some problem..." + e);
            }
         }
    }
}
