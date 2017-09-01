package mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;

/**
 * Command that queries the database based on the conditions given , it can also project the query in certain fields
 * and limit the number of returning objects 
 * Created by satyaalmasian on 18/03/2017.
 */
public class MongoQueryCommand<T> extends MongoCommand {

    Bson query;
    List<String> projectionFields=null;
    public int limit=-1;

    public static class Builder {

        List<String> projectionFields=new LinkedList<>();
        int limit;

        public Builder() {

        }

        public MongoQueryCommand build(MongoMediator mongoMediator, MongoCollection<Document> collection, Bson query) {
            return new MongoQueryCommand(mongoMediator, collection, query,this);
        }


        public MongoQueryCommand.Builder projectionFields( List<String> fields) {
            this.projectionFields = fields;
            return this;
        }
        public MongoQueryCommand.Builder projectionFieldsAdd( String fields) {
            this.projectionFields.add(fields);
            return this;
        }
        public MongoQueryCommand.Builder limit(int limit) {
            this.limit =limit ;
            return this;
        }


    }
    public MongoQueryCommand(MongoMediator mongoMediator, MongoCollection<Document> collection, Bson query)
    {
        super(mongoMediator,collection);
        this.query=query;
    }

    public MongoQueryCommand(MongoMediator mongoMediator, MongoCollection<Document> collection, Bson query, Builder builder)
    {
        super(mongoMediator,collection);
        this.query=query;
        this.projectionFields=builder.projectionFields;
        this.limit=builder.limit;
    }
    public FindIterable execute() {

        return mediator.select(this);

    }

    public Bson getQuery() {
        return query;
    }

    public List<String> getProjectionFields() {
        return projectionFields;
    }

    public int getLimit() {
        return limit;
    }
}
