/*
 *
 *  * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package org.wso2.esbMonitor.reporting;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.log4j.Logger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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




}
