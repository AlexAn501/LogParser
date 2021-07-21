package com.javarush.task.task39.task3913;

import java.util.Date;

public class User {
    private String ip;
    private String name;
    private Date date;
    private Event event;
    private int task;
    private Status status;

    public User(String ip, String name, Date date, Event event, int task, Status status) {
        this.ip = ip;
        this.name = name;
        this.date = date;
        this.event = event;
        this.task = task;
        this.status = status;
    }

    public User(String ip, String name, Date date, Event event, Status status) {
        this.ip = ip;
        this.name = name;
        this.date = date;
        this.event = event;
        this.status = status;
    }

    public Object getFieldForName(String field){
        switch (field){
            case "ip": return ip;
            case "user": return name;
            case "date": return date;
            case "event": return event;
            case "status": return status;
            default: return null;
        }
    }

    public String getIp() {
        return ip;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    public Status getStatus() {
        return status;
    }

    public int getTask() {
        return task;
    }

    public static int compareDate(User u1, User u2) {
        return Long.compare(u1.getDate().getTime(), u2.getDate().getTime());
    }
}
