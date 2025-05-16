package se.kth.iv1350.amazingpos.logapi;
import java.io.FileWriter;
import java.io.IOException; 
import java.io.PrintWriter;

/**
 * Prints log messages to a file. 
 */
public class FileLogger {
    private PrintWriter logStream;

    /**
     * Creates a new instance of FileLogger and a new log file.
     */
    public FileLogger(){
        try {
            logStream = new PrintWriter(new FileWriter("log.txt"), true);
        } catch(IOException ioe) {
            System.out.println("Can not log!");
            ioe.printStackTrace();
        }
    }

    public FileLogger(String logFile){
        try {
            logStream = new PrintWriter(new FileWriter(logFile), true);
        } catch(IOException ioe) {
            System.out.println("Can not log!");
            ioe.printStackTrace();
        }

    }

    

    /**
     * Prints the specified message to the log file.
     * @param message The messege to be logged.
     */
    public void log(String message){
        logStream.println(message);
    }

}
