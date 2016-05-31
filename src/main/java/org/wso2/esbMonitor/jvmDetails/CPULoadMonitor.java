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
public class CPULoadMonitor {
    final static Logger logger = Logger.getLogger(CPULoadMonitor.class);
    private static ObjectName bean = null;

    //needs to be initialized from a property file
    private static double CPU_LOAD;

    public void getMbeanInfo() {
        try {
            bean = new ObjectName("java.lang:type=OperatingSystem");
            checkWarningUsage(bean);

        } catch (MalformedObjectNameException e) {
            logger.error("CPULoadMonitor " + e.getMessage());
        }
    }

    private static boolean checkWarningUsage(ObjectName mbean) {

        OperatingSystemMXBean operatingSystemMXBean=
                JMX.newMBeanProxy(RemoteConnector.getRemote(), mbean, OperatingSystemMXBean.class);
        double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();

        if (cpuLoad > CPU_LOAD) {
            logger.info(":High CPU load");
            ThreadDumpCreator.generateThreadDump();
            return true;
        } else {
            logger.info("cpu load is normal: " + cpuLoad);
        }
        return false;
    }

    public static void setCpuLoad(double cpuLoad) {
        CPU_LOAD = cpuLoad;
    }
}
