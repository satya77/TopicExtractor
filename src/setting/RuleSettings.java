package setting;

import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Satya Almasian  on 14/10/16.
 */
public class RuleSettings extends Settings{
    private Properties prop;        // properties for storing the program options

    /**
     * Standard constructor
     * @param rootPath path to this program
     * @param filename name of settings file
     */
    public RuleSettings(String rootPath, String filename) {
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
     * Get the URLs of the page that has all the topics and futhur rule selection will be applied on it .
     * @return URLs of the page for topics
     */
    public String getTopicsPageUrl() {

        return prop.getProperty("TopicsPageUrl");

    }
    /**
     * Get the rule that extracts the type of from the main page, also the link that goes to the next page that has all
     * the entities of this type
     * @return rule extracting the type
     */
    public String getTypeLinkSelector() {

        return prop.getProperty("TypeLinkSelector");
    }

    /**
     * Get the rule that extracts the list of names under a specific type these names contain a link to a page that has their topic
     * @return rule extracting the list of names
     */
    public String getListOfNamesSelector() {

        return prop.getProperty("ListOfNamesSelector");
    }

    /**
     * Get the rule that extracts the link for the next page inside the page that has the list of names
     * @return rule extracting the next page selector
     */
    public String getNextPageSelector() {

        return prop.getProperty("NextPageSelector");
    }

    /**
     * Get the rule that extracts the Main thema
     * @return rule extracting the Main Thema
     */
    public String getMainThemaSelector() {

        return prop.getProperty("MainThemaSelector");
    }

    /**
     * Get the rule that extracts the link for the next page of the page that has all the articles with a mention of an entity
     * @return rule extracting the next page selector
     */
    public String getAllArticleNextPageSelector() {

        return prop.getProperty("AllArticleNextPageSelector");
    }
    /**
     * Get the rule that extracts the related topics to an entity
     * @return rule extracting the related topics
     */
    public String getRelatedTopicsSelector() {

        return prop.getProperty("RelatedTopicsSelector");
    }

    /**
     * Get the rule that extracts the topic from each article that was the entity mentioned in
     * @return rule extracting the topic of each article
     */
    public String getArticleTopicSelector() {

        return prop.getProperty("ArticleTopicSelector");
    }

    /**
     * Get what needs to be removed from the article selector
     * @return what needs to be removed
     */
    public String getArticleTopicRemove() {

        return prop.getProperty("ArticleTopicRemove");
    }
}
