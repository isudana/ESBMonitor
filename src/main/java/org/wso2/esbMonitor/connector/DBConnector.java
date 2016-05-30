package org.wso2.esbMonitor.connector;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Dinanjana on 22/05/2016.
 */
public class DBConnector {


    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    private String dbName = "wso2FlightRecorder";
    private Connection conn ;
    private Logger logger = Logger.getLogger(DBConnector.class);

    public Connection createDBConnection() {
        Statement s;

        ArrayList<Statement> statements = new ArrayList<Statement>();
        try {
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true");
            System.out.println("Connected to and created database " + dbName);

            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);

            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);

            //creates tables...
            s.execute("create table activePassThroughConnectionsHTTPListner (eventTime TIMESTAMP , activeConnections int)");
            s.execute("create table activePassThroughConnectionsHTTPSender (eventTime TIMESTAMP , activeConnections int)");
            s.execute("create table activePassThroughConnectionsHTTPSListner (eventTime TIMESTAMP , activeConnections int)");
            s.execute("create table activePassThroughConnectionsHTTPSSender (eventTime TIMESTAMP , activeConnections int)");
            ResultSet resultSet=  s.executeQuery("DESCRIBE TABLE activePassThroughConnectionsHTTPListner ");


            //System.out.println("Created table location");

            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("ERROR : ", e);
        }

        return null;
    }

    public void closeDBConnection()  {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if(e.getErrorCode() == 50000){
                logger.info("Closed sucessfully");
            }
            //e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
