/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relaytimer;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.WindowEvent;


/**
 *
 * @author Zhdanova PR 01
 */
public class RelayTimer extends Application {
    protected static String programVersion = "2.0." + "b" + "0";
    protected static File soundFile;
    protected static double iCount, iIncrement;
    protected static float intervalInMin;
    protected Button btnStartTimer;
    protected Label lblStartPage;
    protected TextField txtStartPage;
    protected Label lblIncrementPage;
    protected TextField txtIncrementPage;
    protected Label lblTime;
    protected TextField txtTime;
    protected Label lblSoundFile;
    protected TextField txtSoundFile;
    protected static TextArea txtOutput;
    protected static Timer itsTime;
    
    @Override
    public void start (Stage primaryStage) {
        primaryStage.setTitle("RelayTimer v. " + programVersion);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        Scene sc = new Scene(grid, 300, 275);
        primaryStage.setScene(sc);
        
        //build interface
        btnStartTimer = new Button("Start Relay Timer");
        lblStartPage = new Label("Starting Page:");
        txtStartPage = new TextField();
        lblIncrementPage = new Label("Increase Page # By:");
        txtIncrementPage = new TextField();
        lblTime = new Label("Time b/t Increment:");
        txtTime = new TextField();
        lblSoundFile = new Label("Sound File:");
        txtSoundFile = new TextField("beep-1.wav");
        txtOutput = new TextArea();
        
        grid.add(lblSoundFile, 0, 0);
        grid.add(txtSoundFile, 1, 0);
        grid.add(lblTime, 0, 1);
        grid.add(txtTime, 1, 1);
        grid.add(lblStartPage, 0, 2);
        grid.add(txtStartPage, 1, 2);
        grid.add(lblIncrementPage, 0, 3);
        grid.add(txtIncrementPage, 1, 3);
        btnStartTimer.setAlignment(Pos.CENTER);
        grid.add(btnStartTimer, 0, 4, 2, 1);
        grid.add(txtOutput,0,5,2,1);
        
        //grid.setGridLinesVisible(true);
        
        btnStartTimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                checkAndStart(txtTime.getText(), new File(txtSoundFile.getText()), 
                        txtIncrementPage.getText(), txtStartPage.getText());
            }
        });
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (itsTime != null) {
                    itsTime.exit();
                    itsTime.interrupt();
                }
            }
        });
        
        primaryStage.show();
    }
    
    public static void checkAndStart(String sTime, File sFile, String sIncrementPage, String sStartPage) {
        try {
            
            if(isNumeric(sTime) && isNumeric(sIncrementPage) && isNumeric(sStartPage)) { 
                intervalInMin = Float.valueOf(sTime);
                iIncrement = Double.valueOf(sIncrementPage);
                iCount = Double.valueOf(sStartPage);
                soundFile = sFile;
                
                itsTime = new Timer(sFile, iCount, iIncrement, intervalInMin);
                itsTime.start();
            } else throw new Exception("Inproperly formated Input");
        }  catch (Exception e) {
                output("Some problem..." + e);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String COMPILEDATE = "20150406";
        String REVISION = "10";
        
        // TODO code application logic here
        if (args.length != 4) {
            System.out.println("\njava -jar RelayTimer.jar [path\\soundfile.wav] [min. interval] [starting #] [increment]");
            System.out.println("Version: " + programVersion);
            System.out.println("# of Compiles Since Last Revision" + REVISION);
            System.out.println("Compiled On: " + COMPILEDATE);
            System.out.println("By Neil A. Patel");
            launch(args);
        } else {
            System.out.println("Timer App Started.");
            System.out.println("Arguments:  " + args[0] + "  " + args[1] + " " + args[2]);
            checkAndStart(args[1], new File(args[0]), args[3], args[2]);
         }
    }
    
    public static boolean isNumeric(String str) {  
        try  {  
          double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  {  
          return false;  
        }  
        return true;  
    }
    
    public static void output(String s) {
        if (txtOutput != null) {
            txtOutput.setText(txtOutput.getText() + "\n" + s);
        } else System.out.println(s);
    }
}
