package mongo;

/**
 * Created by Satya Almasian on 24/05/16.
 * holds the information for connecting to MongoDB
 */
public class MongoConnection {

    public static class Builder {

        //get ssh connection settings
        private String sshHost=null;
        private String LDAPUsername;
        private String LDAPPassword;

        //get mongodb settings
        private String dbHost;
        private int MongoDBPort;
        private String MongoDBUsername;
        private  char[] MongoDBPassword;

        public Builder() {

        }

        public MongoConnection build() {
            return new MongoConnection(this);
        }


        public Builder sshHost(String sshHost) {
            this.sshHost = sshHost;
            return this;
        }
        public Builder LDAPUsername(String LDAPUsername) {
            this.LDAPUsername =LDAPUsername ;
            return this;
        }
        public Builder LDAPPassword(String LDAPPassword) {
            this.LDAPPassword = LDAPPassword;
            return this;
        }
        public Builder dbHost(String dbHost) {
            this.dbHost =dbHost ;
            return this;
        }
        public Builder MongoDBPort(int MongoDBPort) {
            this.MongoDBPort =MongoDBPort;
            return this;
        }
        public Builder MongoDBUsername(String MongoDBUsername) {
            this.MongoDBUsername = MongoDBUsername;
            return this;
        }
        public Builder MongoDBPassword(char[] MongoDBPassword) {
            this.MongoDBPassword = MongoDBPassword;
            return this;
        }

    }

    //get ssh connection settings
    private final String sshHost;
    private final String LDAPUsername;
    private final String LDAPPassword;
    private final String dbHost;

    //get mongodb settings
    private final int MongoDBPort;
    private final String MongoDBUsername;
    private final char[] MongoDBPassword;


    private MongoConnection(Builder builder)
    {

        //get ssh connection settings
        sshHost= builder.sshHost;
        LDAPUsername= builder.LDAPUsername;
        LDAPPassword= builder.LDAPPassword;
        dbHost= builder.dbHost;

        //get mongodb settings
        MongoDBPort=builder.MongoDBPort;
        MongoDBUsername= builder.MongoDBUsername;
        MongoDBPassword= builder.MongoDBPassword;
    }




    public String getSshHost() {
        return sshHost;
    }


    public String getLDAPUsername() {
        return LDAPUsername;
    }


    public String getLDAPPassword() {
        return LDAPPassword;
    }


    public String getDbHost() {
        return dbHost;
    }


    public int getMongoDBPort() {
        return MongoDBPort;
    }


    public String getMongoDBUsername() {
        return MongoDBUsername;
    }


    public char[] getMongoDBPassword() {
        return MongoDBPassword;
    }


}
