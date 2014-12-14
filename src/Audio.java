import java.io.*;
import sun.audio.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Christopher
 */
public class Audio {
    //The name of the file
    private String file;
    //Other stuff 
    InputStream inStream;
    AudioStream audioStream;
    
    /**
     * Constructor for the audio clip
     * @param The name of the file
     */
    public Audio(String fileName){
        //Sets the file for the class
        
        String location;
        try {
            String fileLocation = Main.class.getResource("Main.class").getPath();
            location = fileLocation.substring(6, fileLocation.indexOf("Main.class")) + "resources/sounds/";
        } catch(Exception ex){
            ex.printStackTrace();
            location = "src/resources/sounds/";
        }
        
        file = location + fileName + ".wav";
    }
    /**
     * Plays the object's assigned audio file
     */
    public void playSound(){
        System.out.println(file);
        
        try {
            inStream = new FileInputStream(file);
            audioStream = new AudioStream(inStream);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //Stops the track
    public void stopSound(){
        AudioPlayer.player.stop(audioStream);
    }
    
    /**
     * Returns a string of data regarding the audio
     * @return The toString for the object
     */
    @Override
    public String toString(){
        return "Filename: " + file;
    }
}
