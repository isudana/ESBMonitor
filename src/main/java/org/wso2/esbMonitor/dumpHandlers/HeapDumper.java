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

package org.wso2.esbMonitor.dumpHandlers;

/**
 * Created by Dinanjana
 * on 06/05/2016.
 */

import com.sun.management.HotSpotDiagnosticMXBean;
import org.wso2.esbMonitor.connector.RemoteConnector;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * This class creates heap dumps
 * on the monitored JVM instance
 * For an example the heap dump will
 * be created on HOME path of the
 * wso2esb
 *
 * Works only with HotSpotJVM
 *
 * */
public class HeapDumper extends Thread {

    private static final String HOTSPOT_BEAN_NAME =
            "com.sun.management:type=HotSpotDiagnostic";
    private static volatile HotSpotDiagnosticMXBean hotspotMBean;
    private static final Object HEAP_DUMP_IN_PROGRESS = new Object();
    private static String fileName = "heap//";

    static void dumpHeap(String fileName, boolean live) {
         synchronized (HEAP_DUMP_IN_PROGRESS){
            initHotspotMBean();
            try {
                hotspotMBean.dumpHeap(fileName, live);
            } catch (RuntimeException re) {
                throw re;
            } catch (Exception exp) {
                throw new RuntimeException(exp);
            }
        }

    }

    private static synchronized void initHotspotMBean() {
        if (hotspotMBean == null ) {
            synchronized (HeapDumper.class) {
                if (hotspotMBean == null) {
                    hotspotMBean = getHotspotMBean();
                }
            }
        }
    }


    private static HotSpotDiagnosticMXBean getHotspotMBean() {
        try {
            MBeanServerConnection remote = RemoteConnector.getRemote();
            HotSpotDiagnosticMXBean bean = JMX.newMBeanProxy(remote,
                    new ObjectName(HOTSPOT_BEAN_NAME),
                    HotSpotDiagnosticMXBean.class);
            return bean;
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    public void run(){
        // default heap dump file name
        Date date = new Date();
        String name = "HeapDump "+date.toString().replaceAll(":","")+".bin";
        // by default dump only the live objects
        boolean live = true;
        dumpHeap(name,live);
    }

    public static void setFileName(String fileName) {
        HeapDumper.fileName = fileName;
    }
}