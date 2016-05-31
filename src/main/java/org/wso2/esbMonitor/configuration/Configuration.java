package org.wso2.esbMonitor.configuration;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.dumpHandlers.HeapDumper;
import org.wso2.esbMonitor.jvmDetails.CPULoadMonitor;
import org.wso2.esbMonitor.jvmDetails.MemoryMonitor;
import org.wso2.esbMonitor.network.PassThruHTTPSenderAndReciever;
import org.wso2.esbMonitor.tasks.DBTaskRunner;
import org.wso2.esbMonitor.tasks.JVMTaskRunner;
import org.wso2.esbMonitor.tasks.NetworkMonitor;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dinanjana on 31/05/2016.
 * Reads property file
 * to initiate tasks
 */
public class Configuration {
    private static Logger logger = Logger.getLogger(Configuration.class);
    private static final String FILE_NAME = "wso2esbfr.properties";
    private long DB_TASK = 3000;
    private long JVM_TASK = 3000;
    private long NETWORK_TASK = 3000;
    private String HEAP_DUMP_PATH;
    private String EMAIL_ADDRESS;
    private Double MEMORY_USAGE = 0.1;
    private Double CPU_USAGE = 0.05;
    private int HTTP_REQUESTS = 0;
    private int MAX_REQESTQUEUE_SIZE = 0;

    public void initProperties(){
        readPropFile();
        DBTaskRunner.setWaitTime(DB_TASK);
        JVMTaskRunner.setWaitTime(JVM_TASK);
        NetworkMonitor.setWaitTime(NETWORK_TASK);
        HeapDumper.setFileName(HEAP_DUMP_PATH+"/");
        MemoryMonitor.setMEMORY(MEMORY_USAGE);
        CPULoadMonitor.setCpuLoad(CPU_USAGE);
        PassThruHTTPSenderAndReciever.setMaxQueueSize(MAX_REQESTQUEUE_SIZE);
        PassThruHTTPSenderAndReciever.setMaxThreadCount(HTTP_REQUESTS);

    }

    private boolean readPropFile(){
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                return true;
            }
            DB_TASK= Long.parseLong(prop.getProperty("DB_TASK_INTERVAL"));
            logger.info("Added db task interval "+ DB_TASK);
            JVM_TASK= Long.parseLong(prop.getProperty("JVM_TASK_INTERVAL"));
            logger.info("Added jvm task interval "+ JVM_TASK);
            NETWORK_TASK= Long.parseLong(prop.getProperty("NETWORK_TASK_INTERVAL"));
            logger.info("Added network task interval "+ NETWORK_TASK);
            HEAP_DUMP_PATH=prop.getProperty("HEAP_DUMP_PATH");
            logger.info("Added heap dump path "+ HEAP_DUMP_PATH);
            EMAIL_ADDRESS=prop.getProperty("EMAIL_ADDRESS");
            logger.info("Added Email address "+ EMAIL_ADDRESS);
            MEMORY_USAGE=Double.parseDouble(prop.getProperty("MAX_MEMORY_USAGE"));
            logger.info("Added max memory usage "+ MEMORY_USAGE);
            CPU_USAGE=Double.parseDouble(prop.getProperty("MAX_CPU_USAGE"));
            logger.info("Added max CPU usage " + CPU_USAGE);
            MAX_REQESTQUEUE_SIZE=Integer.parseInt(prop.getProperty("MAX_REQUEST_QUEUE_SIZE"));
            logger.info("Added max request queue size "+MAX_REQESTQUEUE_SIZE);
            HTTP_REQUESTS=Integer.parseInt(prop.getProperty("MAX_HTTP_REQUESTS"));
            logger.info("Added max http requests "+ HTTP_REQUESTS);

        }catch (Exception e){
            logger.error("Property file error",e);
        }
        finally {
            return false;
        }
    }
}
