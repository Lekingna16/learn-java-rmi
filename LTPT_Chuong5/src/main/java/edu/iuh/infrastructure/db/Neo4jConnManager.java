package edu.iuh.infrastructure.db;

import org.neo4j.driver.*;

public class Neo4jConnManager implements AutoCloseable{

    private final Driver driver;
    private  final String dbName;

    public Neo4jConnManager(String url, String userName, String password, String dbName) {
        this.driver = GraphDatabase.driver(url, AuthTokens.basic(userName, password)); // chung thuc thong qua us, pw
        this.dbName = dbName;
    }

    public Session openSession () {
        //return driver.session(); // dbName: neo4j
        return driver.session(SessionConfig.forDatabase(dbName)); // dbName: chi dinh

    }
    @Override
    public void close() throws Exception {
        driver.close();
    }
}
