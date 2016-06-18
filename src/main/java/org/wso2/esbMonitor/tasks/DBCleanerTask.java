package org.wso2.esbMonitor.tasks;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.persistance.PersistenceService;

import java.sql.SQLException;

/**
 * Created by Dinanjana on 18/06/2016.
 */
public class DBCleanerTask extends Thread {
    private static Long WAIT_TIME = 24 * 60 * 60 *1000L ;
    private static final Logger logger = Logger.getLogger(DBCleanerTask.class);

    public void run(){
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            logger.error("ERROR" , e);
        }
        try {
            PersistenceService.cleanDBTables();
        } catch (SQLException e) {
            logger.error("ERROR" , e);
        }

    }

    public static void setWaitTime(Long waitTime) {
        WAIT_TIME = waitTime*60*60*1000;
    }
}
