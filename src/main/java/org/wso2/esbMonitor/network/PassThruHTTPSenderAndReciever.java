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

package org.wso2.esbMonitor.network;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.dumpHandlers.ThreadDumpCreator;
import org.wso2.esbMonitor.persistance.PersistenceService;

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
    private static String bean;

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
            switch (bean){
                case "org.apache.synapse:Type=Transport,Name=passthru-http-sender":
                    passThruHTTPBean.setType(RequestType.HTTP_SENDER);
                    break;
                case "org.apache.synapse:Type=Transport,Name=passthru-http-receiver":
                    passThruHTTPBean.setType(RequestType.HTTP_RECEIVER);
                    break;
                case "org.apache.synapse:Type=Transport,Name=passthru-https-receiver":
                    passThruHTTPBean.setType(RequestType.HTTPS_RECEIVER);
                    break;
                case "org.apache.synapse:Type=Transport,Name=passthru-https-sender":
                    passThruHTTPBean.setType(RequestType.HTTP_SENDER);
                    break;
            }

            logger.info(passThruHTTPBean.getMessageSent() +" "+ passThruHTTPBean.getActiveThreadCount());

            if(passThruHTTPBean.getActiveThreadCount() > maxThreadCount || passThruHTTPBean.getQueueSize() > maxQueueSize) {
                logger.info(":High HTTP loads");

                if(!ThreadDumpCreator.isThreadDumpInProgress()){
                    ThreadDumpCreator.generateThreadDump();
                }
                return true;

            } else {
                logger.info("HTTP network load is normal");
            }
            logger.info("Adding network event to scheduledList");
            PersistenceService.addNetworkEvent(passThruHTTPBean);

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
