package org.wso2.reporting.beans;

import java.util.Date;

/**
 * Created by Dinanjana on 14/05/2016.
 */
public class ESBEvent {
    private String reason;
    private String dumplocation;
    private Date date;

    public ESBEvent(String reason,String dumplocation){
        this.reason = reason;
        this.dumplocation = dumplocation;
        this.date = new Date();
    }

    public String getReason() {
        return reason;
    }

    public String getDumplocation() {
        return dumplocation;
    }

    public Date getDate() {
        return date;
    }
}
