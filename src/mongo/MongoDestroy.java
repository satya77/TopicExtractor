package mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Command to destroy a certain collecetion
 * Created by satyaalmasian on 08.05.17.
 */
public class MongoDestroy extends MongoCommand {

    public MongoDestroy(MongoMediator mediator, MongoCollection<Document> collection) {
        super(mediator, collection);
    }
    public void execute() {
            mediator.dropCollection(this);
    }
}
