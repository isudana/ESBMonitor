package org.wso2.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.util.ReportCreator;
import org.apache.log4j.Logger;
import org.bouncycastle.util.test.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

/**
 * Created by Dinanjana on 14/05/2016.
 */
public class ReportGenerator {
    private static Logger logger = Logger.getLogger(ReportGenerator.class);
    private static DRDataSource drDataSource = new DRDataSource("date", "reason", "dumpLocation");
    private static String reportLocation ="E:/Project/esbMonitor/report.pdf";
    private static TextColumnBuilder<Date> dateColumn;
    private static TextColumnBuilder<String> reasonColumn;
    private static TextColumnBuilder<String> dumpLocation;


    public static void initReportGenerator() {
         dateColumn= col.column("Date", "date", type.dateType());
         reasonColumn= col.column("Reason", "reason", type.stringType());
         dumpLocation= col.column("Dump Location", "dumpLocation", type.stringType());
    }
    public static void createReport(){
        try {
            report()
                    .setTemplate(Templates.reportTemplate)
                    .columns(
                            dateColumn, reasonColumn, dumpLocation)
                    .title(Templates.createTitleComponent("Report on Anomalies"))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(drDataSource)
                    .show()
                    .toPdf(new FileOutputStream(reportLocation));
        } catch (DRException e) {
            logger.error(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

    }

    public static void addEventToReport(String reason,String dumpLocation){
        drDataSource.add(new Date(),reason,dumpLocation);
    }

//    public static void main(String[] arg){
//        initReportGenerator();
//        addEventToReport("OOM","c:/");
//        createReport();
//        URL location = ReportGenerator.class.getProtectionDomain().getCodeSource().getLocation();
//        System.out.println(location.getPath());
//    }


}
