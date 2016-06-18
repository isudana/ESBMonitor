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
