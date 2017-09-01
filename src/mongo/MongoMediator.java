package mongo;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.List;
import java.util.Properties;

/**
 * responsible for taking a command and performing it on a collection.
 * Created by satyaalmasian on 18/03/2017.
 */
public class MongoMediator {
    private static MongoMediator mongoMediator=null;
    Session ssh = null;
    private MongoMediator() {

    }
    /**
     * retrun a instance of mongowriter and calls the constructor
     * @return MongoHelper
     */

    public static MongoMediator getInstance(){

        if(mongoMediator==null) {
            mongoMediator=new MongoMediator();
        }
        return mongoMediator;
    }

    /**
     * Open connection to a Server that has mongodb on it and return the mongoClient
     * @param mongoConnection connection settings
     */
    public MongoClient openConnection(MongoConnection mongoConnection) throws JSchException {
        if(mongoConnection.getSshHost()==null) {//if no ssh host is defined they use the normal connection
            return new MongoClient(new ServerAddress(mongoConnection.getDbHost(), mongoConnection.getMongoDBPort()));

        }
        else
        {
                //if a ssh connection is needed make it
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                JSch jsch = new JSch();
                ssh = jsch.getSession(mongoConnection.getLDAPUsername(), mongoConnection.getSshHost(), 22);
                ssh.setPassword(mongoConnection.getLDAPPassword());
                ssh.setConfig(config);
                ssh.connect();
                ssh.setPortForwardingL(8989, mongoConnection.getDbHost(), mongoConnection.getMongoDBPort());
                 MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 8989));
                mongoClient.setReadPreference(ReadPreference.nearest());
                return mongoClient;


        }
    }
    /**
     * Get the Database by name that is defined in the connection
     * @param mongoClient the mongoclient to connect to
     * @param DbName name of the database */

    public MongoDatabase getDB(MongoClient mongoClient, String DbName)
    {
        // load the database. Loading a database that does not exist creates this database
       return mongoClient.getDatabase(DbName);
    }
    /**
     * Get the Collection to work with by name from a database
     * @param Db the database to get the collection from
     * @param CollectionName  name of the collection
     * */

    public MongoCollection<Document> getCollection(MongoDatabase Db, String CollectionName)
    {
        // load the collection from the database. Loading a collection that does not exist creates this collection
        return Db.getCollection(CollectionName);
    }
    /**
     * Get the Collection of a Database to work with by giving the name of the collection and the database
     * @param DbName name of the database
     * @param CollectionName  name of the collection
     * @param mongoClient the mongoclient to connect to
     * */
    public MongoCollection<Document> getCollection(MongoClient mongoClient, String DbName, String CollectionName)
    {
        // load the database. Loading a database that does not exist creates this database
        MongoDatabase db =  mongoClient.getDatabase(DbName);
        // load the collection from the database. Loading a collection that does not exist creates this collection
        return db.getCollection(CollectionName);
    }

    /**
     * Closes the connection
     * @param mongoClient the connection to be closed
     */

    public void connectionClose(MongoClient mongoClient)
    {
        mongoClient.close();

        if(ssh!=null)
        {
            ssh.disconnect();
        }
    }

    /**
     *  Inserts one object into the Collection given and replace if existing based on the _id column
     * @param cmd instanfce of mongoInsertOneCommand
     *
     */
    public  synchronized void insertAndReplace(MongoInsertOneCommand cmd)  {
        /**
         * Insert Document to collection
         */

        // create a new Document
        List<Field> itemFields=cmd.getItem().getItem();
        Document txt= new Document() ;
        for(Field fields: itemFields)
        {
          txt.append(fields.getFieldName(), fields.getValue());
        }
        try{
            // insert Document into collection
            cmd.getCollection().insertOne(txt);
        }
        catch (MongoWriteException e){
            //replace existing
            cmd.getCollection().findOneAndReplace(new BasicDBObject("_id", cmd.getItem().getQuery("_id")),txt);

        }
    }
    /**
     *  Inserts one object into the Collection given and throws and error if already exists
     * @param cmd instanfce of mongoInsertOneCommand
     */
    public  synchronized void insertWithoutReplace(MongoInsertOneCommand cmd)  {
        /**
         * Insert Document to collection
         */

        // create a new Document
        List<Field> itemFields=cmd.getItem().getItem();
        Document txt= new Document() ;
        for(Field fields: itemFields)
        {
            txt.append(fields.getFieldName(), fields.getValue());
        }
            // insert Document into collection
            cmd.getCollection().insertOne(txt);
    }

    /**
     * finds specific records
     * @param cmd instance of the mongoQueryCommand
     */
    public FindIterable<Document> select(MongoQueryCommand cmd)
    {
        if(cmd.getProjectionFields()!=null)//if thre is some projections defined then project the data
        {
            if(cmd.limit!=-1)//if there is some limit defined then limit the data
            {
                return cmd.getCollection().find(cmd.getQuery()).projection(Projections.include(cmd.getProjectionFields())).limit(cmd.getLimit());
            }
            return cmd.getCollection().find(cmd.getQuery()).projection(Projections.include(cmd.getProjectionFields()));
        }
        else if(cmd.limit!=-1)//if only a limit is defined and no projections
        {
            return cmd.getCollection().find(cmd.getQuery()).limit(cmd.getLimit());

        }
        return cmd.getCollection().find(cmd.getQuery());
    }
    /**
     *  updates one document of the Collection and replaces it with the new document fields
     * @param cmd instance of the mongoUpdateOneCommand
     * @param updateType indicates which type of update we have
     *
     */
    public  synchronized void updateOne(MongoUpdateOneCommand cmd, String updateType) {
        /**
         * update Document to collection
         */

        // create a new Document for the perivous to do the query on
        List<Field> itemFields=cmd.getPrevious().getItem();
        Document perviousDoc= new Document() ;
        for(Field fields: itemFields)
        {
            perviousDoc.append(fields.getFieldName(), fields.getValue());
        }

        // create a new Document
        itemFields=cmd.getNewDoc().getItem();
        Document newDocument= new Document() ;
        for(Field fields: itemFields)
        {
            newDocument.append(fields.getFieldName(), fields.getValue());
        }

        Document updateQuery=new Document();
        updateQuery.append(updateType,newDocument);

            // update the document
        cmd.getCollection().updateOne(perviousDoc,updateQuery);

    }
    /**
     *  returns the counts of documents for a certian query
     * @param cmd instance of the MongoCountCommand
     *
     */
    public long count(MongoCountCommand cmd) {
        return cmd.getCollection().count(cmd.getQuery());
    }
    /**
     * drops a database
     * @param cmd instance of the mongoDestroyCommand
     */

    public void dropCollection(MongoDestroy cmd) {
        cmd.getCollection().drop();
    }


}
