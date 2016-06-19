/*
 *
 *  * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

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
            if (!ThreadDumpCreator.isThreadDumpInProgress())
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
