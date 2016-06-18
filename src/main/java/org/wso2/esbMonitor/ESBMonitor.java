package org.wso2.esbMonitor;

import org.wso2.esbMonitor.configuration.Configuration;
import org.wso2.esbMonitor.connector.DBConnector;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.persistance.PersistenceService;
import org.wso2.esbMonitor.tasks.DBCleanerTask;
import org.wso2.esbMonitor.tasks.DBTaskRunner;
import org.wso2.esbMonitor.tasks.JVMTaskRunner;
import org.wso2.esbMonitor.tasks.NetworkMonitor;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.TreeSet;

/**
 *
 */
public class ESBMonitor {
    private static TreeSet<ObjectName> mbeansNames = null;


    public static void main(String[] args) throws IOException {

        new Configuration().initProperties();
        DBConnector.initDBConnection();
        PersistenceService.setConn(DBConnector.getConn());
        RemoteConnector.defaultConnector();
        MBeanServerConnection remote = RemoteConnector.getRemote();

        /**
         * Tasks start here
         *  1)JVM monitor
         *  2)Network traffic monitor
         *  3)Persistence service
         *
         *  */
        new JVMTaskRunner().start();
        new NetworkMonitor().start();
        new DBTaskRunner().start();
        new DBCleanerTask().start();
//        try {
//            ObjectName bean = new ObjectName("org.apache.synapse:Type=Transport,Name=passthru-http-receiver");
//            MBeanInfo info = remote.getMBeanInfo(bean);
//            MBeanAttributeInfo[] attributes = info.getAttributes();
//            for (MBeanAttributeInfo attr : attributes) {
//                System.out.println(attr.getName() + " " + remote.getAttribute(bean, attr.getName()));
//            }
//            System.out.println("Getting Mbean Names from the MBean server ");
//            mbeansNames = new TreeSet<ObjectName>(remote.queryNames(null, null));
//            for (ObjectName name : mbeansNames) {
//                System.out.println("\tObjectName = " + name.getCanonicalName());
//            }
//        } catch (Exception e) {
//
//        }


    }
}
