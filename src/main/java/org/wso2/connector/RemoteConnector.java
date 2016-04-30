package org.wso2.connector;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dinanjana on 30/04/2016.
 */
public class RemoteConnector {

    private static MBeanServerConnection remote = null;
    private static JMXConnector connector = null;

    public static void defaultConnector(){
        try {
            JMXServiceURL target = new JMXServiceURL
                    ("service:jmx:rmi://localhost:11111/jndi/rmi://localhost:9999/jmxrmi");
            //for passing credentials for password
            Map<String, String[]> env = new HashMap<String, String[]>();
            String[] credentials = {"admin", "admin"};
            env.put(JMXConnector.CREDENTIALS, credentials);

            connector = JMXConnectorFactory.connect(target, env);
            remote = connector.getMBeanServerConnection();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MBeanServerConnection getRemote() {
        return remote;
    }

    public static void setRemote(MBeanServerConnection remote) {
        RemoteConnector.remote = remote;
    }

    public static void closeConnection() throws IOException {
        if(connector != null){
            connector.close();
        }
    }
}
