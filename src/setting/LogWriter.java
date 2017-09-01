package setting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author (c) 2014, Andreas Spitz, spitz@stud.uni-heidelberg.de
 * Class for writing a log file of retrieved articles and errors.
 * Synchronized for interaction with multiple threads.
 */
public class LogWriter {
    private BufferedWriter logwriter;
    private StringBuffer stringlog;
    private  DateTimeFormatter dateFormat;
    private String df = "yyyy/MM/dd HH:mm:ss";
    private static LogWriter logWriterInstance;
    
    /**
     * Standard constructor
     * @param file output file for the log
     * @param verbose print full stack trace to console?
     */
    private LogWriter(String file, boolean verbose) {
        dateFormat = DateTimeFormatter.ofPattern(df);
        stringlog = new StringBuffer();
        try {
            // create new buffered data.writer for logfile and set to append
            logwriter = new BufferedWriter(new FileWriter(file, true));
            LocalDateTime startTime=LocalDateTime.now();
            logwriter.append(dateFormat.format(startTime) + " >>> Start session <<<\n");
            logwriter.flush();
        } catch (Exception e) {
            System.out.println("Error: Unable to write to logfile.");
            if (verbose) {
                e.printStackTrace();
            }
        }
    }
    /**
     * return the single instance of the logwriter from the private constructor so only one instance is created
     * @param file output file for the log
     * @param verbose print full stack trace to console?
     */
    public static synchronized LogWriter getInstance(String file, boolean verbose)
    {
        if(logWriterInstance==null)
        {
        logWriterInstance= new LogWriter(file,verbose);

            }
        return logWriterInstance;
    }
    /**
     * return the single instance of the logwriter from the private constructor so only one instance is created
     * @param file output file for the log
     * @param verbose print full stack trace to console?
     */
    public static synchronized LogWriter Initilize(String file, boolean verbose)
    {
        if(logWriterInstance==null)
        {
            logWriterInstance= new LogWriter(file,verbose);

        }
        return logWriterInstance;
    }
    /**
     * Incase it has be initilzed then instance can be reached from here
     */
    public static synchronized LogWriter getInstance() throws LogWriterNotInitialized {
        if(logWriterInstance==null)
        {
            throw new LogWriterNotInitialized("Log Writer is being access without being Initilized.");
        }

        return logWriterInstance;
    }
    
    /**
     * Close the current session of the logwriter
     */
    public synchronized void closeFile() {
        try {
            LocalDateTime endTime=LocalDateTime.now();
            logwriter.append(dateFormat.format(endTime) + " >>> End session <<<\n\n");
            logwriter.close();
        } catch (Exception e) {
            System.out.println("LOG WRITER CAN NOT CLOSE FILE ! ");
        }
    }
    
    /**
     * Write a string to logfile
     * @param log String to write to file
     * @param t optional exception to be written to file
     */
    public synchronized void writeToFile(String log, Throwable t) {
        try {
            // if an exception was passed as argument, extract the stack trace
            if (t != null) {
                for (StackTraceElement element : t.getStackTrace()) {
                    log += "\n" + element.toString();
                }
            }
            
            // add a time stamp and print everything to file
            LocalDateTime time=LocalDateTime.now();
            stringlog.append(dateFormat.format(time) + " " + log +" \n");
            logwriter.append(dateFormat.format(time) + " " + log +" \n");
            logwriter.flush();
        } catch (Exception e) {
            System.out.println("LOG WRITER CAN NOT WRITE TO FILE ! ");
        }
    }
    
    /**
     * Retrieve everything that was written to logfile since the last call of this function
     * @return contents of log buffer
     */
    public synchronized String getRecentLogs() {
        String str = stringlog.toString();
        str=getSummary(str);
        stringlog = new StringBuffer();
        return str;
    }

    /**
     * returns the summary for the mail
     * @return contents of log buffer
     */
    public synchronized String getSummary(String logContent) {

        List<String> list = new ArrayList<String>(Arrays.asList(logContent.split("\\n")));
        StringJoiner js = new StringJoiner("<br>");
        //remove the uncessary stuff
        Predicate<String> predStartStop = p -> p.contains("Start session") || p.contains("Starting retrieval")
                || p.contains("Startup complete") || p.contains("RSS feeds as input") || p.contains("End session") || p.contains("Failed attempt ");
        list.removeIf(predStartStop);

        //number of successfully retrived
        Predicate<String> predSuccessful = p -> p.contains("Successfully retrieved article");
        //remove them from the list
        list.removeIf(predSuccessful);
        //remove the name of the feeds
        Predicate<String> predFeedItem = p -> p.matches("[\\x20-\\x7E]+\\s+<http[\\x20-\\x7E]+>") || p.matches("[\\x20-\\x7E]+\\s+<http[\\x20-\\x7E]+> ");
        list.removeIf(predFeedItem);

        LocalDateTime time = LocalDateTime.now();
        js.add("This is the Summary of RSS Reader on : " + dateFormat.format(time));

        //Unknown hosts
        Predicate<String> predUnknownHost = p -> p.contains("HttpStatusException") || p.contains("UnknownHostException");
        long unknownCount = list.stream().filter(predUnknownHost).count();
        js.add("Number of Unknown Hosts :" + unknownCount);
        list.removeIf(predUnknownHost);

        //Files that were given up after 10 attemps
        Predicate<String> predGivingUp = p -> p.contains("Giving up after 10 failed attempts");
        if (list.stream().filter(predGivingUp).count() > 0) {
            js.add("The URL that were given up after 10 failed attempts : ");
            list.stream()
                    .filter(predGivingUp)
                    .forEach(p -> {
                        p = p.replace("Giving up after 10 failed attempts to retrieve", " ");
                        js.add(p);
                    });
        }
        list.removeIf(predGivingUp);

        //The unable to retieved feeds
        Predicate<String> predUnable = p -> p.contains("Unable to retrieve feed");
        if (list.stream().filter(predUnable).count() > 0) {
            js.add("Unable to retrieve feeds : ");
            String names = "";
            Iterator<String> it = list.stream().filter(predUnable).iterator();
            while (it.hasNext()) {
                String p = it.next();
                int i = p.indexOf("feed");
                p = p.substring(i + 4);
                if (!names.contains(p)) {
                    names += p + " ";
                    js.add("<b style=\"color:#DB7093;\">" + p + "</b>");
                }
            }
        }
            list.removeIf(predUnable);
            js.add(" --------------------------------------------------------------");
            //add the remainig to log
            list.stream().forEach(p -> js.add(p));

            return js.toString();
        }
    }
