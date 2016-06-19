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

package org.wso2.esbMonitor.network;

/**
 * Created by Dinanjana on 18/06/2016.
 */
public enum RequestType {
    HTTP_SENDER(0),
    HTTP_RECEIVER(1),
    HTTPS_SENDER(2),
    HTTPS_RECEIVER(3);
    private int id;

    private RequestType(int id){
        this.id = id ;
    }
    public static int getId(RequestType requestType){
        switch (requestType){
            case HTTP_SENDER:
                return 0;
            case HTTP_RECEIVER:
                return 1;
            case HTTPS_SENDER:
                return 2;
            case HTTPS_RECEIVER:
                return 3;
            default:
                return -1;
        }
    }

}
