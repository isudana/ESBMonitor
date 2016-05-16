package org.wso2.esbMonitor.jvmDetails;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.dumpHandlers.ThreadDumpCreator;
import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.io.IOException;


/**
 * Created by Dinanjana
 * on 30/04/2016.
 */
public class MemoryExtractor extends Thread{

    final static Logger logger = Logger.getLogger(MemoryExtractor.class);
    private static ObjectName bean = null;
    private static double MEMORY = 0.05;
    private static long TIMEOUT = 3000;

    public static void getMemoryInfo() {
        try {
            bean = new ObjectName("java.lang:type=Memory");
            checkWarningUsage();

        } catch (MalformedObjectNameException e) {
            logger.error("MemoryExtractor.java:25 " + e.getMessage());
        }
    }

    private static boolean checkWarningUsage() {
            try {
                logger.info(":Acessing memory details");
                CompositeData memoryUsage = (CompositeData) RemoteConnector.getMbeanAttribute("java.lang:type=Memory","HeapMemoryUsage");
                long maxMemory = (Long) memoryUsage.get("max");
                long usedMemory = (Long) memoryUsage.get("used");

                if ((double) usedMemory / maxMemory > MEMORY) {
                    logger.info(":High memory usage");
                    return true;
                } else {
                    logger.info("Memory usage is normal " + (double) usedMemory / maxMemory);
                }
            } catch (MBeanException e) {
                logger.error(e.getMessage());
            } catch (AttributeNotFoundException e) {
                logger.error(e.getMessage());
            } catch (InstanceNotFoundException e) {
                logger.error(e.getMessage());
            } catch (ReflectionException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        return false;
    }

    public void run(){
        while (true){
            getMemoryInfo();
            if(checkWarningUsage()){
                ThreadDumpCreator.getMbeanInfo();
            }
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
