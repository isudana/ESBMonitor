package org.wso2.esbMonitor.jvmDetails.transport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Dinanjana on 29/05/2016.
 * This serves as the DAO object for network
 * traffic details
 */

@Table(name = "HTTP_LOG")
public class PassThruHTTPBean {

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

    public int getActiveThreadCount() {
        return activeThreadCount;
    }

    public void setActiveThreadCount(int activeThreadCount) {
        this.activeThreadCount = activeThreadCount;
    }

    public double getAvgSizeREcieved() {
        return avgSizeRecieved;
    }

    public void setAvgSizeREcieved(double avgSizeREcieved) {
        this.avgSizeRecieved = avgSizeREcieved;
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
