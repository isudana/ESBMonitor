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

package org.wso2.esbMonitor.tasks;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.network.PassThruHTTPSenderAndReciever;

/**
 * Created by Dinanjana on 29/05/2016.
 */
public class NetworkMonitor extends Thread {

    private Logger logger = Logger.getLogger(NetworkMonitor.class);
    private PassThruHTTPSenderAndReciever passThruHTTPSender = new PassThruHTTPSenderAndReciever();
    private PassThruHTTPSenderAndReciever passThruHTTPReciever = new PassThruHTTPSenderAndReciever();
    private PassThruHTTPSenderAndReciever passThruHTTPSSender = new PassThruHTTPSenderAndReciever();
    private PassThruHTTPSenderAndReciever passThruHTTPSReciever = new PassThruHTTPSenderAndReciever();
    //needs to be initialized from a property file
    private static long WAIT_TIME;

    public void init(){
        PassThruHTTPSenderAndReciever.setBean("org.apache.synapse:Type=Transport,Name=passthru-http-sender");
        PassThruHTTPSenderAndReciever.setBean("org.apache.synapse:Type=Transport,Name=passthru-http-receiver");
        PassThruHTTPSenderAndReciever.setBean("org.apache.synapse:Type=Transport,Name=passthru-https-sender");
        PassThruHTTPSenderAndReciever.setBean("org.apache.synapse:Type=Transport,Name=passthru-https-receiver");

    }
    public void run(){
        init();
        while (true){
            passThruHTTPSender.getMbeanInfo();
            passThruHTTPReciever.getMbeanInfo();
            passThruHTTPSSender.getMbeanInfo();
            passThruHTTPSReciever.getMbeanInfo();
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                logger.error("Network Thread error" , e);
            }
        }
    }

    public static void setWaitTime(long waitTime) {
        WAIT_TIME = waitTime;
    }
}
