package org.wso2.esbMonitor.network;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.persistance.PersistanceImpl;
import javax.management.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Dinanjana on 29/05/2016.
 * This class checks network traffic
 * for HTTP/S
 */
public class PassThruHTTPSenderAndReciever {

    final static Logger logger = Logger.getLogger(PassThruHTTPSenderAndReciever.class);
    private static String bean = "org.apache.synapse:Type=Transport,Name=passthru-http-sender";

    // needs to be initialized by a property file
    private static int maxThreadCount;
    private static int maxQueueSize;

    public void getMbeanInfo() {
        if(checkWarningUsage(bean)){
            logger.info("generate report!");
        }
    }

    /**
     * */
    private boolean checkWarningUsage(String mbeanName) {
        PassThruHTTPBean passThruHTTPBean = new PassThruHTTPBean();

        try {
            logger.info(":Accessing HTTP transport details ");
            passThruHTTPBean.setActiveThreadCount((int) RemoteConnector.getMbeanAttribute(mbeanName,"ActiveThreadCount"));
            passThruHTTPBean.setAvgSizeRecieved((Double) RemoteConnector.getMbeanAttribute(mbeanName,"AvgSizeReceived"));
            passThruHTTPBean.setAvgSizeSent((Double) RemoteConnector.getMbeanAttribute(mbeanName,"AvgSizeSent"));
            passThruHTTPBean.setFaultSending((Long) RemoteConnector.getMbeanAttribute(mbeanName,"FaultsSending"));
            passThruHTTPBean.setFaultsRecieving((Long) RemoteConnector.getMbeanAttribute(mbeanName,"FaultsReceiving"));
            passThruHTTPBean.setQueueSize((Integer) RemoteConnector.getMbeanAttribute(mbeanName,"QueueSize"));
            passThruHTTPBean.setMessageSent((Long) RemoteConnector.getMbeanAttribute(mbeanName,"MessagesSent"));
            passThruHTTPBean.setMessagesRecieved((Long) RemoteConnector.getMbeanAttribute(mbeanName, "MessagesReceived"));
            passThruHTTPBean.setDate(new Date());

            logger.info(passThruHTTPBean.getMessageSent() +" "+ passThruHTTPBean.getActiveThreadCount());
            if (passThruHTTPBean.getActiveThreadCount() > maxThreadCount || passThruHTTPBean.getQueueSize() > maxQueueSize) {
                logger.info(":High HTTP loads");
                return true;
            } else {
                logger.info("HTTP network load is normal");
            }
            logger.info("Adding network event to scheduledList");
            PersistanceImpl.addNetworkEvent(passThruHTTPBean);
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

    public static void setBean(String bean) {
        PassThruHTTPSenderAndReciever.bean = bean;
    }

    public static void setMaxThreadCount(int maxThreadCount) {
        PassThruHTTPSenderAndReciever.maxThreadCount = maxThreadCount;
    }

    public static void setMaxQueueSize(int maxQueueSize) {
        PassThruHTTPSenderAndReciever.maxQueueSize = maxQueueSize;
    }
}
