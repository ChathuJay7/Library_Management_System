package com.example.library;

import java.sql.Date;
import java.time.LocalDate;

public class Events {

    String EventID;
    String EventName;
    Date EventDate;
    String EventTime;

    public Events(String id, String name, Date date, String time){
        this.EventID = id;
        this.EventName = name;
        this.EventDate = date;
        this.EventTime = time;
    }

    public String getEventID(){
        return EventID;
    }

    public void setEventID(String eventID){
        EventID = eventID;
    }

    public String getEventName(){
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public Date getEventDate() {
        return EventDate;
    }

    public void setEventDate(Date eventDate) {
        EventDate = eventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }
}
