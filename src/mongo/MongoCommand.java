package mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Abstract class that all the commands inheirate
 * Created by satyaalmasian on 18/03/2017.
 */
public abstract class MongoCommand {

    MongoMediator mediator;// mediator that does all the work
    MongoCollection<Document> collection;// the collection that the command is being performed upon

    public MongoCommand(MongoMediator mediator, MongoCollection<Document> collection) {
        this.mediator = mediator;
        this.collection = collection;
    }
    public MongoMediator getMediator() {
        return mediator;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

}
