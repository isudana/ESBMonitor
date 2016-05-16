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

    public static void defaultConnector() {
        try {
            JMXServiceURL target = new JMXServiceURL
                    ("service:jmx:rmi://localhost:11111/jndi/rmi://localhost:9999/jmxrmi");
            //for passing credentials for password
            Map<String, String[]> env = new HashMap<String, String[]>();
            String[] credentials = {"admin", "admin"};
            env.put(JMXConnector.CREDENTIALS, credentials);
            connector = JMXConnectorFactory.connect(target, env);
            remote = connector.getMBeanServerConnection();
            logger.info("MbeanServer connection obtained");



        } catch (MalformedURLException e) {
            logger.error(e.getStackTrace());
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
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
}
