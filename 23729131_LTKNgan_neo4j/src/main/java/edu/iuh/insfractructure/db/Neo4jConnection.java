package edu.iuh.insfractructure.db;

import org.neo4j.driver.*;

public class Neo4jConnection implements AutoCloseable{

    private final Driver driver;
    private final String dbName;

    public Neo4jConnection(String uri, String username, String password, String dbName){
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        this.dbName = dbName;
    }

    public Session openSession(){
//        return driver.session();//dbName: neo4j
        return driver.session(SessionConfig.forDatabase(dbName));//dbName: specific database
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}
