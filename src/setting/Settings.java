package setting;

/**
 * Created by Satya almasian on 27/05/16.
 */
public abstract class Settings {
    static protected LogWriter log;          // logfile data.writer
    protected String rootPath;        // path to the location of the program

    //Default Constructor
    public Settings(String rootPath)
    {
        try {
            this.log = LogWriter.getInstance();
        } catch (LogWriterNotInitialized logWriterNotInitialized) {
            System.out.println(logWriterNotInitialized.getMessage());
        }        this.rootPath=rootPath;
    }

    /**
     * Get Log data.writer
     * @return reference to this programs log data.writer
     */
    public LogWriter getLogWriter() {
        return log;
    }

    /**
     * Get the name of the path to the program's root directory
     * @return path to root directory of program
     */
    public String getRootPath() {
        return rootPath;
    }


}

