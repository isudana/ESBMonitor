package org.wso2.esbMonitor.jvmDetails;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.dumpHandlers.HeapDumper;
import org.wso2.esbMonitor.dumpHandlers.ThreadDumpCreator;
import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.io.IOException;


/**
 * Created by Dinanjana
 * on 30/04/2016.
 */
public class MemoryMonitor{

    final static Logger logger = Logger.getLogger(MemoryMonitor.class);
    private static ObjectName bean = null;
    private static double MEMORY;
    private static final String OBJECT_NAME ="java.lang:type=Memory";

    public void getMbeanInfo() {
        try {
            bean = new ObjectName(OBJECT_NAME);
            checkWarningUsage();

        } catch (MalformedObjectNameException e) {
            logger.error("MemoryMonitor.java:25 " + e.getMessage());
        }
    }

    private boolean checkWarningUsage() {
            try {
                logger.info(":Acessing memory details");
                CompositeData memoryUsage = (CompositeData) RemoteConnector.getMbeanAttribute(OBJECT_NAME,"HeapMemoryUsage");
                long maxMemory = (Long) memoryUsage.get("max");
                long usedMemory = (Long) memoryUsage.get("used");

                if ((double) usedMemory / maxMemory > MEMORY) {
                    logger.info(":High memory usage");

                    //ToDo Send to report module

                    ThreadDumpCreator.getMbeanInfo();
                    HeapDumper heapDumper = new HeapDumper();
                    heapDumper.start();
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

    public static void setMEMORY(double MEMORY) {
        MemoryMonitor.MEMORY = MEMORY;
    }
}
