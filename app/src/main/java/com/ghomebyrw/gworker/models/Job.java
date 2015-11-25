package com.ghomebyrw.gworker.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by wewang on 11/19/15.
 */
public class Job implements Serializable {
    private UUID id;
    private String serviceName;
    private ScheduledDateAndTime scheduledDateAndTime;
    private JobStatus status;
    private UUID customerId;
    private Price finalPrice;
    private Location location;
    private String timeZone;
    private String customerPhoneNumber;
    private String note;
    private Integer estimatedMinutes;
    private HashMap<String, String> questionToAnswer;
    private String fieldworker;

    public Job() {
    }

    public Job(Price price) {
        this.finalPrice = price;
    }

    public Job(UUID id, String serviceName, ScheduledDateAndTime scheduledDateAndTime,
               JobStatus status,
               UUID customerId, Price acceptedPrice, Location location,
               String timeZone, String customerPhoneNumber, String note, int estimatedMinutes,
               HashMap<String, String> questionToAnswer, String fieldworker) {
        this.id = id;
        this.serviceName = serviceName;
        this.scheduledDateAndTime = scheduledDateAndTime;
        this.status = status;
        this.customerId = customerId;
        this.finalPrice = acceptedPrice;
        this.location = location;
        this.timeZone = timeZone;
        this.customerPhoneNumber = customerPhoneNumber;
        this.note = note;
        this.estimatedMinutes = estimatedMinutes;
        this.questionToAnswer = questionToAnswer;
        this.fieldworker = fieldworker;
    }

    public UUID getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ScheduledDateAndTime getScheduledDateAndTime() {
        return scheduledDateAndTime;
    }

    public JobStatus getStatus() {
        return status;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Price getAcceptedPrice() {
        return finalPrice;
    }

    public Location getLocation() {
        return location;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public String getNote() {
        return note;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public HashMap<String, String> getQuestionToAnswer() {
        return questionToAnswer;
    }

    public String getFieldworker() {
        return fieldworker;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", scheduledDateAndTime=" + scheduledDateAndTime +
                ", status=" + status +
                ", customerId=" + customerId +
                ", finalPrice=" + finalPrice +
                ", location=" + location +
                ", timeZone='" + timeZone + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", note='" + note + '\'' +
                ", estimatedMinutes=" + estimatedMinutes +
                ", questionToAnswer=" + questionToAnswer +
                ", fieldworker='" + fieldworker + '\'' +
                '}';
    }
}