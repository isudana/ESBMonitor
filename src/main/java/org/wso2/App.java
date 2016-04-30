package org.wso2;

import org.wso2.connector.RemoteConnector;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Hello world!
 *
 */
public class App 
{
    private static TreeSet<ObjectName> mbeansNames = null;
    public static void main( String[] args )
    {
            RemoteConnector.defaultConnector();
            MBeanServerConnection remote = RemoteConnector.getRemote();
            //Find this from jConsole

        try {


            ObjectName bean = new ObjectName("org.apache.synapse:Type=Transport,Name=passthru-http-receiver");

            MBeanInfo info = remote.getMBeanInfo(bean);
            MBeanAttributeInfo[] attributes = info.getAttributes();
            for (MBeanAttributeInfo attr : attributes) {
                System.out.println(attr.getName() + " " + remote.getAttribute(bean, attr.getName()));
            }
            System.out.println("Getting Mbean Names from the MBean server ");
            mbeansNames = new TreeSet<ObjectName>(remote.queryNames(null, null));
            for (ObjectName name : mbeansNames) {
                System.out.println("\tObjectName = " + name.getCanonicalName());
            }
        }catch (Exception e){

        }

    }
}
