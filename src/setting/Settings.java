package setting;

/**
 * Created by Satya almasian on 27/05/16.
 */
public abstract class Settings {
    protected String rootPath;        // path to the location of the program

    //Default Constructor
    public Settings(String rootPath)
    {
        this.rootPath=rootPath;
    }

    /**
     * Get the name of the path to the program's root directory
     * @return path to root directory of program
     */
    public String getRootPath() {
        return rootPath;
    }


}

