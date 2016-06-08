package org.wso2.esbMonitor.connector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Dinanjana
 * on 30/04/2016.
 */
public class RemoteConnector {

    private static MBeanServerConnection remote = null;
    private static JMXConnector connector = null;
    private static final Logger logger= LogManager.getLogger(RemoteConnector.class);
    private static String JMXURL;
    private static String USERNAME;
    private static String PASSWORD;

    public static void defaultConnector() {
        try {
            connect();
        } catch (MalformedURLException e) {
            logger.error(e.getStackTrace());
        } catch (IOException e) {
            logger.error("IO error in connecting",e);
            try {
                logger.info("Trying to reconnect in 3 seconds");
                Thread.sleep(3000);
                defaultConnector();
            } catch (InterruptedException e1) {
                logger.error("Thread interrupted",e);
            }

        }
    }

    private static void connect() throws IOException {
        JMXServiceURL target = new JMXServiceURL
                (JMXURL);
        //for passing credentials for password
        Map<String, String[]> env = new HashMap<String, String[]>();
        String[] credentials = {USERNAME,PASSWORD};
        env.put(JMXConnector.CREDENTIALS, credentials);
        connector = JMXConnectorFactory.connect(target, env);
        remote = connector.getMBeanServerConnection();
        logger.info("MbeanServer connection obtained");



    }

    public static MBeanServerConnection getRemote() {
        return remote;
    }

    public static synchronized Object getMbeanAttribute(String objectName,String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException {
        try {
            ObjectName bean = new ObjectName(objectName);
            return remote.getAttribute(bean,attribute);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection() throws IOException {
        if (connector != null) {
            connector.close();
        }
    }

    public static void setJMXURL(String JMXURL) {
        RemoteConnector.JMXURL = JMXURL;
    }

    public static void setUSERNAME(String USERNAME) {
        RemoteConnector.USERNAME = USERNAME;
    }

    public static void setPASSWORD(String PASSWORD) {
        RemoteConnector.PASSWORD = PASSWORD;
    }
}
