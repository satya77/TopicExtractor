package mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;


/**
 * Insert one document at a time based on an insert type
 * Created by satyaalmasian on 18/03/2017.
 */
public class MongoInsertOneCommand<T> extends MongoCommand {

    InsertType insertType;
    DocumentItem item;

    public MongoInsertOneCommand(MongoMediator mongoMediator, InsertType insertType, MongoCollection<Document> collection, DocumentItem item)
    {
        super(mongoMediator,collection);
        this.insertType=insertType;
        this.item=item;
    }
    public void execute() {
        switch (insertType){
            case INSERT_AND_REPLACE:
                mediator.insertAndReplace(this);
                break;
            case INSERT_WITHOUT_REPLACE:
                mediator.insertWithoutReplace(this);
                break;
        }
    }
    public InsertType getInsertType() {
        return insertType;
    }

    public DocumentItem getItem() {
        return item;
    }

}
