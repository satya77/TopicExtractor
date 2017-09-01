package Core;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by satyaalmasian on 01.09.17.
 */
public class Entity {
    String name;
    String type;
    String mainTopic;
    LinkedList<String> relatedTopics;
    HashMap<String,Integer> topic_proportions;

    public Entity(String name, String type) {
        this.name = name;
        this.type = type;
        relatedTopics=new LinkedList<>();
        topic_proportions=new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public LinkedList<String> getRelatedTopics() {
        return relatedTopics;
    }

    public void addRelatedTopics(String relatedTopic) {
        this.relatedTopics.add(relatedTopic);
    }

    public HashMap<String, Integer> getTopic_proportions() {
        return topic_proportions;
    }

    public void addTopic_proportions(String topic_text) {
        if (!topic_proportions.containsKey(topic_text)) {
            topic_proportions.put(topic_text, 0);
        } else {
            int i = topic_proportions.get(topic_text);
            i++;
            topic_proportions.put(topic_text, i);
        }    }
}
