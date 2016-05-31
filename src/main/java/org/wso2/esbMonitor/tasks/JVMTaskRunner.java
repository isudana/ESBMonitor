package org.wso2.esbMonitor.tasks;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.jvmDetails.CPULoadMonitor;
import org.wso2.esbMonitor.jvmDetails.MemoryMonitor;

/**
 * Created by Dinanjana on 30/05/2016.
 */
public class JVMTaskRunner extends Thread{

    private static long WAIT_TIME;
    private static Logger logger = Logger.getLogger(JVMTaskRunner.class);
    private MemoryMonitor memoryMonitor = new MemoryMonitor();
    private CPULoadMonitor cpuLoadMonitor = new CPULoadMonitor();


    public void run(){
        while (true){
            try {
                memoryMonitor.getMbeanInfo();
                cpuLoadMonitor.getMbeanInfo();

                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                logger.error("Thread wait Exception",e);
            }
        }
    }

    public static void setWaitTime(long waitTime) {
        WAIT_TIME = waitTime;
    }
}
