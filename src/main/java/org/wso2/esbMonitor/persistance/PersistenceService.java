package org.wso2.esbMonitor.persistance;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.network.PassThruHTTPBean;
import org.wso2.esbMonitor.network.RequestType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Dinanjana on 14/06/2016.
 */
public class PersistenceService {

    private static Logger logger= Logger.getLogger(PersistenceService.class);
    private static Connection conn;
    private static ArrayList<PassThruHTTPBean> scheduledList
            = new ArrayList<>();

    public synchronized static void addNetworkEvent(PassThruHTTPBean passThruHTTPBean){
        logger.info("Adding event :{}"+ passThruHTTPBean.getActiveThreadCount() +"Size :{}" +scheduledList.size());
        scheduledList.add(passThruHTTPBean);
    }

    public static synchronized void addEventToDB() throws SQLException {
        Statement stmt = conn.createStatement();
        for(PassThruHTTPBean passThruHTTPBean:scheduledList){
            stmt.executeUpdate("INSERT INTO HTTP_LOG VALUES (" + passThruHTTPBean.getActiveThreadCount() + ","
                    + passThruHTTPBean.getAvgSizeRecieved() + "," +
                    passThruHTTPBean.getAvgSizeSent() + "," +
                    passThruHTTPBean.getFaultsRecieving() + "," +
                    passThruHTTPBean.getFaultSending() + "," +
                    passThruHTTPBean.getMessagesRecieved() + "," +
                    passThruHTTPBean.getMessageSent() + "," +
                    passThruHTTPBean.getQueueSize() + ",'" +
                    new Timestamp(passThruHTTPBean.getDate().getTime()).toString() + "'," +
                    RequestType.getId(passThruHTTPBean.getType()) +
                    " ) ");
        }
        scheduledList.clear();
    }

    public static synchronized void cleanDBTables() throws SQLException {
        String tableName = "HTTP_LOG";
        Statement stmt = conn.createStatement();
        stmt.execute("TRUNCATE TABLE "+ tableName);
    }

    public static void setConn(Connection conn) {
        PersistenceService.conn = conn;
    }
}
