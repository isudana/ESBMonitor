package org.wso2.esbMonitor.configuration;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
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
    private String JMXURL="service:jmx:rmi://localhost:11111/jndi/rmi://localhost:9999/jmxrmi";
    private String USERNAME="admin";
    private String PASSWORD="admin";
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
        RemoteConnector.setJMXURL(JMXURL);
        RemoteConnector.setUSERNAME(USERNAME);
        RemoteConnector.setPASSWORD(PASSWORD);

    }

    private void readPropFile(){
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                return;
            }

            if(prop.getProperty("DB_TASK_INTERVAL") != null){
                DB_TASK= Long.parseLong(prop.getProperty("DB_TASK_INTERVAL"));
                logger.info("Added db task interval "+ DB_TASK);
            }

            if(prop.getProperty("JVM_TASK_INTERVAL")!= null){
                JVM_TASK= Long.parseLong(prop.getProperty("JVM_TASK_INTERVAL"));
                logger.info("Added jvm task interval "+ JVM_TASK);
            }

            if(prop.getProperty("NETWORK_TASK_INTERVAL") != null){
                NETWORK_TASK= Long.parseLong(prop.getProperty("NETWORK_TASK_INTERVAL"));
                logger.info("Added network task interval "+ NETWORK_TASK);
            }

            if(prop.getProperty("HEAP_DUMP_PATH") != null){
                HEAP_DUMP_PATH=prop.getProperty("HEAP_DUMP_PATH");
                logger.info("Added heap dump path "+ HEAP_DUMP_PATH);
            }

            if(prop.getProperty("EMAIL_ADDRESS")!= null){
                EMAIL_ADDRESS=prop.getProperty("EMAIL_ADDRESS");
                logger.info("Added Email address "+ EMAIL_ADDRESS);
            }

            if(prop.getProperty("MAX_MEMORY_USAGE")!= null){
                MEMORY_USAGE=Double.parseDouble(prop.getProperty("MAX_MEMORY_USAGE"));
                logger.info("Added max memory usage "+ MEMORY_USAGE);
            }

            if(prop.getProperty("MAX_CPU_USAGE")!=null){
                CPU_USAGE=Double.parseDouble(prop.getProperty("MAX_CPU_USAGE"));
                logger.info("Added max CPU usage " + CPU_USAGE);
            }

            if(prop.getProperty("MAX_REQUEST_QUEUE_SIZE") != null){
                MAX_REQESTQUEUE_SIZE=Integer.parseInt(prop.getProperty("MAX_REQUEST_QUEUE_SIZE"));
                logger.info("Added max request queue size "+MAX_REQESTQUEUE_SIZE);
            }

            if(prop.getProperty("MAX_HTTP_REQUESTS") != null){
                HTTP_REQUESTS=Integer.parseInt(prop.getProperty("MAX_HTTP_REQUESTS"));
                logger.info("Added max http requests "+ HTTP_REQUESTS);
            }

            if(prop.getProperty("JMX_SERVICE_URL") != null){
                JMXURL=prop.getProperty("JMX_SERVICE_URL");
                logger.info("Added JMX URL" + JMXURL);
            }

            if(prop.getProperty("JMX_USER") != null){
                USERNAME=prop.getProperty("JMX_USER");
                logger.info("Added JMX user "+USERNAME);
            }

            if(prop.getProperty("JMX_USER_PASSWORD") != null){
                PASSWORD=prop.getProperty("JMX_USER_PASSWORD");
                logger.info("Added JMX user password"+PASSWORD);
            }



        }catch (Exception e){
            logger.error("Property file error",e);
        }

    }
}
