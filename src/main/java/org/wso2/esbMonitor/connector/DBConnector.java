package org.wso2.esbMonitor.connector;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Dinanjana
 * on 22/05/2016.
 * This connector is
 * used to connect to
 * derby embedded db
 */
public class DBConnector {

    private static final String protocol = "jdbc:derby:";
    private static String dbName = "wso2FlightRecorder";
    private static Connection conn ;
    private static Logger logger = Logger.getLogger(DBConnector.class);

    public static void initDBConnection() {
        try {
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true");
            logger.info("Connected to and created database " + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("ERROR : ", e);
            initDBConnection();
        }
    }

    public static void closeDBConnection()  {
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

    public static Connection getConn() {
        return conn;
    }

}
