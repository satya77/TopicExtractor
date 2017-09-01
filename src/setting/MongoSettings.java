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
        }
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
     * Get the name of  Collection for saving the  content
     * @return the name of the Collection
     */
    public String getCollectionName() {

        return prop.getProperty("CollectionName");
    }


    /**
     * Get the name of the id Field
     * @return the name of the id Field
     */
    public String getFieldID() {

        return prop.getProperty("Field_Id");
    }
    /**
     * Get the name of the entity name
     * @return the name of enitiy
     */
    public String getField_EntityName() {

        return prop.getProperty("Field_EntityName");
    }
    /**
     * Get the name of the field for main topic
     * @return the name of main topic
     */
    public String getField_MainTopic() {

        return prop.getProperty("Field_MainTopic");
    }

    /**
     * Get the name of the field for type of entity
     * @return the name of type
     */
    public String getField_Type() {

        return prop.getProperty("Field_Type");
    }
    /**
     * Get the name of the field for related topics
     * @return the name of related topics
     */
    public String getField_RelatedTopicsr() {

        return prop.getProperty("Field_RelatedTopics");
    }
}
