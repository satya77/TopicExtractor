package mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Updates one record of the database base on the update type
 * Created by satyaalmasian on 13.05.17.
 */
public class MongoUpdateOneCommand extends MongoCommand {
        DocumentItem previous;
        DocumentItem newDoc;



    UpdateType updateType;

    public MongoUpdateOneCommand(MongoMediator mediator, MongoCollection<Document> collection, DocumentItem previous, DocumentItem newDoc, UpdateType updateType) {
        super(mediator, collection);
        this.previous = previous;
        this.newDoc = newDoc;
        this.updateType = updateType;
    }
    public void execute() {
        switch(updateType) {
            case INC:
                mediator.updateOne(this,"$inc");
                break;
            case SET:
                mediator.updateOne(this,"$set");
                break;
            case UNSET:
                mediator.updateOne(this,"$unset");

        }
    }
    public DocumentItem getPrevious() {
        return previous;
    }

    public DocumentItem getNewDoc() {
        return newDoc;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }
}
