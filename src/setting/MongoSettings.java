package setting;

import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Satya Almasian on 27/05/16.
 */
public class MongoSettings extends Settings {
    private Properties prop;        // properties for storing the program options

    /**
     * Standard constructor
     * @param rootPath path to this program
     * @param filename name of settings file
     */
    public MongoSettings(String rootPath, String filename) {
        super(rootPath);
        prop = new Properties();

        /* try to load program settings. If nothing can be loaded, create an empty
         * Properties object so that default values are used when reading them.
         */
        try {
            prop.load(new FileReader(rootPath + filename));
        } catch (Exception e) {
            String err = "Error: Unable to load settings from file. Using default values.";
            System.out.println(err);
            log.writeToFile(err, e);
        }
    }

    /**
     * Get Log writer
     * @return reference to this programs log writer
     */
    public LogWriter getLogWriter() {
        return log;
    }
    /**
     * Get the name of the database that the files are to be written into
     * @return database name for the files to be written into
     */
    public String getDBName() {

        return prop.getProperty("DBName");
    }


    /**
     * Get the feeds that are in german
     * @return the feeds that are in german
     */
    public String getDeFeeds()
    {
        return prop.getProperty("DeFeeds");
    }
    /**
     * Get the name of the ssh host
     * @return name of the ssh host
     */
    public String getSSHHost() {

        return prop.getProperty("SSHHost");
    }
    /**
     * Get the name of the host
     * @return name of the host
     */
    public String getDBHost() {

        return prop.getProperty("DBHost");
    }
    /**
     * Get the name of LDAP Username
     * @return name of LDAP Username
     */
    public String getLDAPUsername() {

        return prop.getProperty("LDAPUsername");
    }

    /**
     * Get the name of LDAP Password
     * @return name of LDAP Password
     */
    public String getLDAPPassword() {

        return prop.getProperty("LDAPPassword");
    }

    /**
     * Get the MongoDB Port number
     * @return MongoDB Port number
     */
    public int getMongoDBPort() {

        return  Integer.valueOf(prop.getProperty("MongoDBPort"));
    }

    /**
     * Get the Mongo Authentication DB
     * @return Mongo Authentication DB
     */
    public String getMongoAuthenticationDB() {

        return prop.getProperty("MongoAuthenticationDB");
    }
    /**
     * Get the MongoDB Username
     * @return MongoDB Username
     */
    public String getMongoDBUsername() {

        return prop.getProperty("MongoDBUsername");
    }
    /**
     * Get the MongoDB Password
     * @return MongoDB Password
     */
    public char[] getMongoDBPassword() {

        return prop.getProperty("MongoDBPassword").toCharArray();
    }
    /**
     * Get the name of Text Collection for saving the txt content
     * @return the name of the Text Collection
     */
    public String getTextCollectionName() {

        return prop.getProperty("TextCollectionName");
    }

    /**
     * Get the name of HTML Collection for saving the html content
     * @return the name of the HTML Collection
     */
    public String getHTMLCollectionName() {

        return prop.getProperty("HTMLCollectionName");
    }

    /**
     * Get the name of the id Field
     * @return the name of the id Field
     */
    public String getFieldID() {

        return prop.getProperty("Field_Id");
    }
    /**
     * Get the name of the article title Field
     * @return the name of the article title  Field
     */
    public String getFieldArticleTitle() {

        return prop.getProperty("Field_ArticleTitle");
    }
    /**
     * Get the name of the Feed name Field
     * @return the name of the Feed name Field
     */
    public String getFieldFeedName() {

        return prop.getProperty("Field_FeedName");
    }
    /**
     * Get the name of the Category name  Field
     * @return the name of the Category name  Field
     */
    public String getFieldCategoryName() {

        return prop.getProperty("Field_CategoryName");
    }
    /**
     * Get the name of the Feed Url Field
     * @return the name of the Feed Url Field
     */
    public String getFieldFeedUrl() {

        return prop.getProperty("Field_FeedURL");
    }    /**
     * Get the name of the Pulished Date Field
     * @return the name of the Published Date Field
     */
    public String getFieldPublishedDate() {

        return prop.getProperty("Field_PublishedDate");
    }
    /**
     * Get the name of the Retrieved Date Field
     * @return the name of the Retrieved Date Field
     */
    public String getFieldRetrievedDate() {

        return prop.getProperty("Field_RetrievedDate");
    }    /**
     * Get the name of the Page Number Field
     * @return the name of the Page Number Field
     */
    public String getFieldPageNumber() {

        return prop.getProperty("Field_PageNumber");
    }
    /**
     * Get the name of the References Number Field
     * @return the name of the References Number Field
     */
    public String getFieldReferencesNumber() {

        return prop.getProperty("Field_ReferencesNumber");
    }
    /**
     * Get the name of the References Field
     * @return the name of the References Field
     */
    public String getFieldReferences() {

        return prop.getProperty("Field_References");
    }
    /**
     * Get the name of the HTML code Field
     * @return the name of the HTML code Field
     */
    public String getFieldHTMLcode() {

        return prop.getProperty("Field_HTMLcode");
    }
    /**
     * Get the name of the Author Field
     * @return the name of the Author Field
     */
    public String getFieldAuthor() {

        return prop.getProperty("Field_Author");
    }
    /**
     * Get the name of the Summary Field
     * @return the name of the Summary Field
     */
    public String getFieldSummary() {

        return prop.getProperty("Field_Summary");
    }
    /**
     * Get the name of the Content Field
     * @return the name of the Content Field
     */
    public String getFieldContent() {

        return prop.getProperty("Field_Content");
    }
    /**
     * Get the name of the Language Field
     * @return the name of the Language Field
     */
    public String getFieldLanguage() {

        return prop.getProperty("Field_Language");
    }
    /**
     * Get the name of the Comments Counter
     * @return the name of the Comments Counter
     */
    public String getFieldCommentsCounter() {

        return prop.getProperty("Field_CommentsCounter");
    }

}
