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
