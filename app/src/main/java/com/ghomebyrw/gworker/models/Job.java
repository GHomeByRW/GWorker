package com.ghomebyrw.gworker.models;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by wewang on 11/19/15.
 */
public class Job {
    private String serviceName;
    private ScheduledDateAndTime scheduledDateAndTime;
    private JobStatus status;
    private UUID customerId;
    private Price price;
    private String location;
    private String timeZone;
    private String customerPhoneNumber;
    private String note;
    private int estimatedMinutes;
    private HashMap<String, String> questionToAnswer;
    private String fieldworker;

    public Job(String serviceName, ScheduledDateAndTime scheduledDateAndTime,
               JobStatus status,
               UUID customerId, Price acceptedPrice, String location,
               String timeZone, String customerPhoneNumber, String note, int estimatedMinutes,
               HashMap<String, String> questionToAnswer, String fieldworker) {
        this.serviceName = serviceName;
        this.scheduledDateAndTime = scheduledDateAndTime;
        this.status = status;
        this.customerId = customerId;
        this.price = acceptedPrice;
        this.location = location;
        this.timeZone = timeZone;
        this.customerPhoneNumber = customerPhoneNumber;
        this.note = note;
        this.estimatedMinutes = estimatedMinutes;
        this.questionToAnswer = questionToAnswer;
        this.fieldworker = fieldworker;
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
        return price;
    }

    public String getLocation() {
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

    public int getEstimatedMinutes() {
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
                "serviceName='" + serviceName + '\'' +
                ", scheduledDateAndTime=" + scheduledDateAndTime +
                ", status=" + status +
                ", customerId=" + customerId +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", note='" + note + '\'' +
                ", estimatedMinutes=" + estimatedMinutes +
                ", questionToAnswer=" + questionToAnswer +
                ", fieldworker='" + fieldworker + '\'' +
                '}';
    }
}