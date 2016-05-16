package org.wso2.esbMonitor.reporting.reportWorker;

import org.apache.log4j.Logger;
import org.wso2.esbMonitor.reporting.ReportGenerator;

/**
 * Created by Dinanjana on 14/05/2016.
 */
public class ReportMaker extends Thread {
    private static Logger logger = Logger.getLogger(ReportMaker.class);
    private Object createReport;
    public void run(){
        ReportGenerator.initReportGenerator();
        try {
            createReport.wait();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
