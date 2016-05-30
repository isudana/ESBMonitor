package org.wso2.esbMonitor.jvmDetails;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.jvmDetails.transport.PassThruHTTPSenderAndReciever;

/**
 * Created by Dinanjana on 29/05/2016.
 */
public class NetworkMonitor extends Thread {

    private Logger logger = Logger.getLogger(NetworkMonitor.class);
    private PassThruHTTPSenderAndReciever passThruHTTPSender = new PassThruHTTPSenderAndReciever();
    private PassThruHTTPSenderAndReciever passThruHTTPReciever = new PassThruHTTPSenderAndReciever();
    //needs to be initialized from a property file
    private static long WAIT_TIME = 3000;

    public void init(){
        passThruHTTPSender.setBean("org.apache.synapse:Type=Transport,Name=passthru-http-sender");
        passThruHTTPReciever.setBean("org.apache.synapse:Type=Transport,Name=passthru-http-reciever");
    }
    public void run(){

        while (true){
            passThruHTTPSender.getMbeanInfo();
            passThruHTTPReciever.getMbeanInfo();
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                logger.error("Network Thread error" , e);
            }
        }
    }
}
