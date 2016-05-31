package org.wso2.esbMonitor.tasks;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.persistance.PersistanceImpl;

/**
 * Created by Dinanjana on 30/05/2016.
 */
public class DBTaskRunner extends Thread {
    private static long WAIT_TIME;
    private static Logger logger = Logger.getLogger(DBTaskRunner.class);

    public void run(){
        while (true){

            try {
                PersistanceImpl.addNetworkTrafficDetailsToDB();
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                logger.error("Wait error" , e);
            }
        }
    }

    public static void setWaitTime(long waitTime) {
        WAIT_TIME = waitTime;
    }
}
