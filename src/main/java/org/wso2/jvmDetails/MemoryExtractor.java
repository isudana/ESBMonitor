package org.wso2.jvmDetails;

import org.wso2.connector.RemoteConnector;

import javax.management.*;
import java.io.IOException;

/**
 * Created by Dinanjana on 30/04/2016.
 */
public class MemoryExtractor {

    private static MBeanInfo memoryInfo;

    public static void getMemoryInfo()  {

        ObjectName bean = null;
        try {
            bean = new ObjectName("java.lang:type=Memory");
            MBeanInfo info = RemoteConnector.getRemote().getMBeanInfo(bean);

        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
