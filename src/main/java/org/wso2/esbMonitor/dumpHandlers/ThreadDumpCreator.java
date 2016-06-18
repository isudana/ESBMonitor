package org.wso2.esbMonitor.dumpHandlers;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.connector.RemoteConnector;
import org.wso2.esbMonitor.utils.FileWriter;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Date;

/**
 * Created by Dinanjana
 * on 02/05/2016.
 */
public class ThreadDumpCreator {
    final static Logger logger = Logger.getLogger(ThreadDumpCreator.class);
    private static MBeanInfo memoryInfo;
    private static ObjectName bean = null;
    private static boolean THREAD_DUMP_IN_PROGRESS=false;
    private static String filePath="ThreadDumps//";
    private static String THREAD_DUMP_BEAN_NAME = "java.lang:type=Threading";

    /**
     * For testing purposes only
     */
    public static String generateThreadDump() {
        final StringBuilder dump = new StringBuilder();
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
        for (ThreadInfo threadInfo : threadInfos) {
            dump.append('"');
            dump.append(threadInfo.getThreadName());
            dump.append("\" ");
            final Thread.State state = threadInfo.getThreadState();
            dump.append("\n   java.lang.Thread.State: ");
            dump.append(state);
            final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
            for (final StackTraceElement stackTraceElement : stackTraceElements) {
                dump.append("\n        at ");
                dump.append(stackTraceElement);
            }
            dump.append("\n\n");
        }
        return dump.toString();
    }

    /**
     * Getting tread dump from
     * remote JVM
     * */

    public synchronized static void getMbeanInfo() {
        THREAD_DUMP_IN_PROGRESS = true;
        StringBuilder dump = new StringBuilder();
        try {
            bean = new ObjectName(THREAD_DUMP_BEAN_NAME);
            memoryInfo = RemoteConnector.getRemote().getMBeanInfo(bean);
            RemoteConnector.getRemote().getObjectInstance(bean);
            MBeanOperationInfo[] mBeanAttributeInfos = memoryInfo.getOperations();
//            for (MBeanOperationInfo mBeanAttributeInfo : mBeanAttributeInfos) {
//                System.out.println(mBeanAttributeInfo.getName());
//            }
            long[] allThreadIds = (long[]) RemoteConnector.getRemote().getAttribute(bean, "AllThreadIds");
            Object[] params = new Object[2];
            int maxDepth = 100;
            params[0] = allThreadIds;
            params[1] = maxDepth;
            String[] opSigs = {allThreadIds.getClass().getName(), int.class.getName()};
            CompositeData[] threadInfos = (CompositeData[]) RemoteConnector.getRemote().invoke(bean, "getThreadInfo", params, opSigs);
            for (CompositeData threadInfo : threadInfos) {
                dump.append('"');
                dump.append(threadInfo.get("threadName").toString());
                dump.append("\" ");
                final String state = (String) threadInfo.get("threadState");
                dump.append("\n   java.lang.Thread.State: ");
                dump.append(state);
                final CompositeData[] stackTraceElements = (CompositeData[]) threadInfo.get("stackTrace");
                for (CompositeData stackTraceElement : stackTraceElements) {
                    dump.append("\n        at ");
                    // String declaringClass= (String) stackTraceElement.get("declaringClass");
                    String methodName = (String) stackTraceElement.get("methodName");
                    String fileName = (String) stackTraceElement.get("fileName");
                    int lineNumber = (Integer) stackTraceElement.get("lineNumber");
                    dump.append(/*declaringClass + */"." + methodName +
                            ((lineNumber == -2) ? "(Native Method)" :
                                    (fileName != null && lineNumber >= 0 ?
                                            "(" + fileName + ":" + lineNumber + ")" :
                                            (fileName != null ? "(" + fileName + ")" : "(Unknown Source)"))));
                }
                dump.append("\n\n");
                byte [] data = dump.toString().getBytes();
                FileWriter.writeFile(filePath+"ThreadDump"+new Date().toString().replaceAll(":","")+".txt",data);
            }

        } catch (MalformedObjectNameException e) {
            logger.error(e.getMessage());
        } catch (InstanceNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IntrospectionException e) {
            logger.error(e.getMessage());
        } catch (ReflectionException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage()+" "+e.getStackTrace());
        } catch (MBeanException e) {
            logger.error(e.getMessage());
        } catch (AttributeNotFoundException e) {
            logger.error(e.getMessage());
        }
        finally {
            THREAD_DUMP_IN_PROGRESS = false;
        }
    }

    public static boolean isThreadDumpInProgress() {
        return THREAD_DUMP_IN_PROGRESS;
    }
}
