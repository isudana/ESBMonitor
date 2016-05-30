package org.wso2.esbMonitor.jvmDetails;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.dumpHandlers.ThreadDumpCreator;
import javax.management.*;
import java.lang.management.OperatingSystemMXBean;

/**
 * Created by Dinanjana on 30/05/2016.
 * Finds out cpu load
 */
public class CPULoadExtractor {
    final static Logger logger = Logger.getLogger(CPULoadExtractor.class);
    private static ObjectName bean = null;

    //needs to be initialized from a property file
    private static double CPU_LOAD = 0.05;
    private static long TIMEOUT = 3000;

    public static void getMemoryInfo() {
        try {
            bean = new ObjectName("java.lang:type=OperatingSystem");
            //checkWarningUsage(bean);

        } catch (MalformedObjectNameException e) {
            logger.error("CPULoadExtractor " + e.getMessage());
        }
    }

    private static boolean checkWarningUsage(ObjectName mbean) {

        OperatingSystemMXBean operatingSystemMXBean=
                JMX.newMBeanProxy(RemoteConnector.getRemote(), mbean, OperatingSystemMXBean.class);
        double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();

        if (cpuLoad > CPU_LOAD) {
            logger.info(":High CPU load");
            return true;
        } else {
            logger.info("cpu load is normal ");
        }
        return false;
    }

    public void run(){
        while (true){
            getMemoryInfo();
            if(checkWarningUsage(bean)){
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
