package mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Command that returns the count based on a certain ceriteria
 * Created by satyaalmasian on 27.05.17.
 */
public class MongoCountCommand<T> extends MongoCommand  {
    Bson query;

    public MongoCountCommand(MongoMediator mongoMediator, MongoCollection<Document> collection, Bson query)
    {
        super(mongoMediator,collection);
        this.query=query;
    }
    public long execute() {

        return mediator.count(this);

    }

    public Bson getQuery() {
        return query;
    }


}
