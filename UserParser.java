package com.javarush.task.task39.task3913;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserParser {
    private UserParser() {
    }

    public static User getUser(String userData) {
        String[] tokens = userData.split("\t");

        String ip = tokens[0];
        String name = tokens[1];
        Date date = parseStringToDate(tokens[2]);
        int task = 0;
        Event event = null;
        Status status = parseStringToStatus(tokens[4]);

        if (tokens[3].contains(" ")) {
            String[] subTokens = tokens[3].split(" ");
            event = parseStringToEvent(subTokens[0]);
            task = parseStringToInt(subTokens[1]);
            return new User(ip, name, date, event, task, status);
        } else {
            event = parseStringToEvent(tokens[3]);
            return new User(ip, name, date, event, status);
        }
    }

    private static Date parseStringToDate(String stringDate) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private static Event parseStringToEvent(String stringEvent) {
        switch (stringEvent) {
            case "LOGIN":
                return Event.LOGIN;
            case "DOWNLOAD_PLUGIN":
                return Event.DOWNLOAD_PLUGIN;
            case "WRITE_MESSAGE":
                return Event.WRITE_MESSAGE;
            case "SOLVE_TASK":
                return Event.SOLVE_TASK;
            case "DONE_TASK":
                return Event.DONE_TASK;
            default:
                throw new IllegalArgumentException(stringEvent);
        }
    }

    private static int parseStringToInt(String stringTask) {
        return Integer.parseInt(stringTask);
    }

    private static Status parseStringToStatus(String stringStatus) {
        switch (stringStatus) {
            case "OK":
                return Status.OK;
            case "FAILED":
                return Status.FAILED;
            case "ERROR":
                return Status.ERROR;
            default:
                throw new IllegalArgumentException(stringStatus);
        }
    }
}
