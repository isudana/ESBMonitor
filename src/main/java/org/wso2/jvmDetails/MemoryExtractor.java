package org.wso2.jvmDetails;

import org.apache.log4j.Logger;
import org.wso2.connector.RemoteConnector;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.io.IOException;


/**
 * Created by Dinanjana on 30/04/2016.
 */
public class MemoryExtractor {

    private static MBeanInfo memoryInfo;
    private static ObjectName bean = null;
    private static double MEMORY = 0.05;
    final static Logger logger = Logger.getLogger(MemoryExtractor.class);

    public static void getMemoryInfo()  {
        try {
            bean = new ObjectName("java.lang:type=Memory");
            memoryInfo = RemoteConnector.getRemote().getMBeanInfo(bean);
            checkWarningUsage();

        } catch (MalformedObjectNameException e) {
            logger.error("MemoryExtractor.java:25 " + e.getMessage());
        } catch (InstanceNotFoundException e) {
            logger.error("MemoryExtractor.java:27 " + e.getMessage());
        } catch (IntrospectionException e) {
            logger.error("MemoryExtractor.java:29 " + e.getMessage());
        } catch (ReflectionException e) {
            logger.error("MemoryExtractor.java:31 " + e.getMessage());
        } catch (IOException e) {
            logger.error("MemoryExtractor.java:33 " + e.getMessage());
        }
    }

    public static boolean checkWarningUsage(){
        if(memoryInfo!= null){
            MBeanAttributeInfo [] mBeanAttributeInfos = memoryInfo.getAttributes();
//            for(MBeanAttributeInfo mBeanAttributeInfo : mBeanAttributeInfos){
//                System.out.println(mBeanAttributeInfo.getName());
//            }
            try {
                logger.info("MemoryExtractor.java:46 Acessing memory details");
                CompositeData memoryUsage = (CompositeData) RemoteConnector
                        .getRemote()
                        .getAttribute(bean, "HeapMemoryUsage");

                long maxMemory =  (Long)memoryUsage.get("max");
                long usedMemory = (Long)memoryUsage.get("used");

                if((double)usedMemory/maxMemory > MEMORY){
                    logger.info("MemoryExtractor.java:53 High memory usage");
                    return true;
                }
                else{
                    logger.info("MemoryExtractor.java:56 Memory usage is normal " + (double)usedMemory/maxMemory);
                }
            } catch (MBeanException e) {
                logger.error("MemoryExtractor.java:50 " + e.getMessage());
            } catch (AttributeNotFoundException e) {
                logger.error("MemoryExtractor.java:52 "+e.getMessage());
            } catch (InstanceNotFoundException e) {
                logger.error("MemoryExtractor.java:54 " + e.getMessage());
            } catch (ReflectionException e) {
                logger.error("MemoryExtractor.java:56 " + e.getMessage());
            } catch (IOException e) {
                logger.error("MemoryExtractor.java:58 "+e.getMessage());
            }

        }
        return false;
    }
}
