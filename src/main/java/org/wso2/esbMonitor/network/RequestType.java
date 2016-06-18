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
