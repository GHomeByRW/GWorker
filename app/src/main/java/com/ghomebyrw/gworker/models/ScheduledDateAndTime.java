package com.ghomebyrw.gworker.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by wewang on 11/19/15.
 */
public class ScheduledDateAndTime {
    private Date startTime;
    private Date endTime;

    public ScheduledDateAndTime(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTimeRange() {
        SimpleDateFormat formatter = new SimpleDateFormat("Ka");
        return formatter.format(startTime).toLowerCase() + " - " + formatter.format(endTime).toLowerCase();
    }

    public String getStartTimeHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("K");
        return formatter.format(startTime);
    }

    public String getStartTimeAMPM() {
        SimpleDateFormat formatter = new SimpleDateFormat("a");
        return formatter.format(startTime).toLowerCase();
    }

    public String getEndTimeHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("K");
        return formatter.format(endTime);
    }

    public String getEndTimeAMPM() {
        SimpleDateFormat formatter = new SimpleDateFormat("a");
        return formatter.format(endTime).toLowerCase();
    }
}
