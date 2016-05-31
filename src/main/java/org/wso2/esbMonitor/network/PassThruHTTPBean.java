package org.wso2.esbMonitor.network;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dinanjana on 29/05/2016.
 * This serves as the DAO object for network
 * traffic details
 */
@Entity
@Table(name = "HTTP_LOG")
public class PassThruHTTPBean {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "activeThreadCount")
    private int activeThreadCount;

    @Column(name = "avgSizeRecieved")
    private double avgSizeRecieved;

    @Column(name ="avgSizeSent")
    private double avgSizeSent;

    @Column(name ="faultsRecieving")
    private long faultsRecieving;

    @Column(name ="faultSending")
    private long faultSending;

    @Column(name ="messagesRecieved")
    private long messagesRecieved;

    @Column(name ="messageSent")
    private long messageSent;

    @Column(name ="queueSize")
    private int queueSize;

    @Column(name = "time")
    private Date date;

    public double getAvgSizeRecieved() {
        return avgSizeRecieved;
    }

    public void setAvgSizeRecieved(double avgSizeRecieved) {
        this.avgSizeRecieved = avgSizeRecieved;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getActiveThreadCount() {
        return activeThreadCount;
    }

    public void setActiveThreadCount(int activeThreadCount) {
        this.activeThreadCount = activeThreadCount;
    }

    public double getAvgSizeSent() {
        return avgSizeSent;
    }

    public void setAvgSizeSent(double avgSizeSent) {
        this.avgSizeSent = avgSizeSent;
    }

    public long getFaultsRecieving() {
        return faultsRecieving;
    }

    public void setFaultsRecieving(long faultsRecieving) {
        this.faultsRecieving = faultsRecieving;
    }

    public long getMessagesRecieved() {
        return messagesRecieved;
    }

    public void setMessagesRecieved(long messagesREcieved) {
        this.messagesRecieved = messagesREcieved;
    }

    public long getFaultSending() {
        return faultSending;
    }

    public void setFaultSending(long faultSending) {
        this.faultSending = faultSending;
    }

    public long getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(long messageSent) {
        this.messageSent = messageSent;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
