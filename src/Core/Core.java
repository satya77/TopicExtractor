package Core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import setting.Controller;
import setting.MongoSettings;
import setting.RuleSettings;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Program to Extract all the topics and related words from a newspaper website
 * Created by satyaalmasian on 01.09.17.
 */
public class Core
{
    private static final String RULE_PATH = "settings/Rules.properties";                          // name of file with RSS feed information
    private static final String DBSETTING_PATH = "settings/DBsettings.properties";                          // name of file with RSS feed information
    /**
     * Main method, run this to start the program
     * @param args Unused.
     */
    public static void main(String[] args) {
        System.out.println("Topic Extractor from Newspapers : ");
        ZonedDateTime d = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss zzz yyyy");
        System.out.println(formatter.format(d));
        /*
         * Try to locate the root directory where the file with this code is located
         * to calculate relavite paths.
         */
        String rootPath = "";
        try {                   // try to obtain the path of the directory this program was started from
            URI uri = Core.class.getProtectionDomain().getCodeSource().getLocation().toURI();     // get URI of jar file
            String jarPath = new File(uri).getAbsolutePath();                                     // extract path
            rootPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);                // and convert to string
            rootPath = rootPath.replace("out/production/", "");
        } catch (Exception e) {
            // if the path cannot be obtained the current JVM working directory will be used
            System.out.println("Unable to obtain path of jar file. Using default working directory.");
            e.printStackTrace();
        }
        System.out.println("Reading Settings from files ....");
        MongoSettings mongoSettings = new MongoSettings(rootPath, DBSETTING_PATH);
        RuleSettings ruleSettings = new RuleSettings(rootPath, RULE_PATH);
        System.out.println("***Finished****");


        //---------------------------------Read the main page ---------------------------------
        Document mainPage=null;
        try {
            mainPage = Jsoup.connect(ruleSettings.getTopicsPageUrl()).timeout(6000).get();
        } catch (IOException e) {
            System.out.println("the main url page can not be extracted : "+ruleSettings.getTopicsPageUrl() +": "+e.getMessage() );
        }

        //---------------------------------Get the types and their links  ---------------------------------
        Elements types=mainPage.select(ruleSettings.getTypeLinkSelector());
        types.forEach(type->
        {
            LinkedList<Document> htmlPages=new LinkedList<>();//stores all the pages to be parsed

            System.out.println("Extracting : "+type.text()+" .... ");
            String link_to_all=type.attr("href");
            if(!link_to_all.startsWith("http")&&!link_to_all.startsWith("www"))
            {
                link_to_all= Controller. absURL(ruleSettings.getTopicsPageUrl(),link_to_all);
            }
            //---------------------------------Read the page for each type ---------------------------------

            Document all_enity_page=null;
            try {
                all_enity_page = Jsoup.connect(link_to_all).timeout(6000).get();
            } catch (IOException e) {
                System.out.println("the type page can not be extracted : "+link_to_all +": "+e.getMessage() );
            }
            //---------------------------------go through all the pages related to that type and save them   ---------------------------------
            htmlPages.add(all_enity_page);
            Element nextPage=all_enity_page.select(ruleSettings.getNextPageSelector()).first();
            while(nextPage!=null)//while there is a next page go there
            {
                String nextPageLink=nextPage.attr("href");
                if(!nextPageLink.startsWith("http")&&!link_to_all.startsWith("www"))
                {
                    nextPageLink= Controller. absURL(ruleSettings.getTopicsPageUrl(),nextPageLink);
                }
                try {
                    all_enity_page = Jsoup.connect(nextPageLink).get();
                } catch (IOException e) {
                    System.out.println("the next page of a type can not be extracted : "+link_to_all +": "+e.getMessage() );
                }
                nextPage=all_enity_page.select(ruleSettings.getNextPageSelector()).first();
                htmlPages.add(all_enity_page);//add it to the list to be parsed later
            }
            //---------------------------------find the list of names in all the stored pages  ---------------------------------
            for(Document doc:htmlPages)
            {
            Elements names=doc.select(ruleSettings.getListOfNamesSelector());
                //--------------------------------- for each name ---------------------------------
                for(Element name:names){
                    Entity entity=new Entity(name.text(),type.text());

                    String link_name=name.attr("href");
                    if(!link_name.startsWith("http")&&!link_to_all.startsWith("www"))
                    {
                        link_name= Controller. absURL(ruleSettings.getTopicsPageUrl(),link_name);
                    }
                    Document enity_page=null;
                    try {
                        //--------------------------------- go to the page that has the articles for that page ---------------------------------
                        enity_page = Jsoup.connect(link_name).timeout(6000).get();
                    } catch (IOException e) {
                        System.out.println("the name page can not be extracted : "+link_name +": "+e.getMessage() );
                    }
                    //Extract the the main thema
                    entity.setMainTopic(enity_page.select(ruleSettings.getMainThemaSelector()).first().text());
                    LinkedList<String> relatedTopics=new LinkedList<>();
                    //--------------------------------- get all the related topics  ---------------------------------
                    Elements relateds=enity_page.select(ruleSettings.getRelatedTopicsSelector());
                    relateds.forEach(r->
                            {
                                entity.addRelatedTopics(r.text());
                            }
                    );
                    //--------------------------------- get the topic proportions  ---------------------------------

                    HashMap<String,Integer> topic_proportions=new HashMap<>();//hash map that has all the topics
                    //that this entity was mentioned in along with the counts
                        Elements aricles_topics = enity_page.select(ruleSettings.getArticleTopicSelector());
                        aricles_topics.forEach(t -> {
                            String topic_text = t.ownText().replace(ruleSettings.getArticleTopicRemove(), "");
                            entity.addTopic_proportions(topic_text);
                        });

                    //--------------------------------- go through all the pages related to an entity  ---------------------------------

                    Elements nextPages=enity_page.select(ruleSettings.getAllArticleNextPageSelector());
                    while(!nextPages.isEmpty())
                    {
                        String nextPageLink=nextPages.first().attr("href");
                        if(!nextPageLink.startsWith("http")&&!link_to_all.startsWith("www"))
                        {
                            nextPageLink= Controller. absURL(ruleSettings.getTopicsPageUrl(),nextPageLink);
                        }
                        try {
                            enity_page = Jsoup.connect(nextPageLink).timeout(6000).get();
                        } catch (IOException e) {
                            System.out.println("the next page for articles can not be extracted : "+link_to_all +": "+e.getMessage() );
                        }
                        nextPages=enity_page.select(ruleSettings.getAllArticleNextPageSelector());
                        //--------------------------------- add topic proportions  ---------------------------------
                        aricles_topics = enity_page.select(ruleSettings.getArticleTopicSelector());
                        aricles_topics.forEach(t -> {
                            String topic_text = t.ownText().replace(ruleSettings.getArticleTopicRemove(), "");
                            entity.addTopic_proportions(topic_text);
                        });
                    }
                    //--------------------------------- take what you have for that entity so far and save it  ---------------------------------

                }

            }
        });

        System.out.println("All Topics are extracted and saved.");
        d=ZonedDateTime.now();
        formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss zzz yyyy");
        System.out.println(formatter.format(d));
        System.out.println("Finished ! ");
        System.exit(0);


    }

    private static void saveEntity(Entity entity)
    {

    }

}
