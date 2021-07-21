package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Solution {
    public static void main(String[] args) throws ParseException {
//        LogParser logParser = new LogParser(Paths.get("c:/logs/"));
//        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));

        //        /home/alexan/Java/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task39/task3913/logs

        LogParser logParser = new LogParser(
                Paths.get("/home/alexan/Java/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task39/task3913/logs/"));
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        System.out.println(logParser.getUniqueIPs(null, new Date()));
//        Eduard Petrovich Morozko
        System.out.println(logParser.getIPsForUser("Eduard Petrovich Morozko", null, new Date()));
        System.out.println(logParser.getIPsForEvent(Event.LOGIN, null, new Date()));
        System.out.println(logParser.getIPsForStatus(Status.OK, null, new Date()));
        System.out.println(logParser.getAllUsers());
        System.out.println(logParser.getUsersForIP("146.34.15.5", null, new Date()));
        System.out.println(logParser.getLoggedUsers( null, new Date()));
        System.out.println(logParser.getNumberOfUserEvents("Eduard Petrovich Morozko",null, new Date()));


        System.out.println("getNumberOfUsers " + logParser.getNumberOfUsers(null,null));
        System.out.println("______________________________");
//        192.168.100.2	Vasya Pupkin	19.03.2016 00:00:00	SOLVE_TASK 1	OK
        System.out.println(logParser.getDateWhenUserSolvedTask("Vas", 1, null,null));
        System.out.println(logParser.getDateWhenUserDoneTask("Vasya Pupkin", 15, null,null));
//146.34.15.5	Eduard Petrovich Morozko	03.01.2014 03:45:23	LOGIN	OK
//        03.01.2014 03:45:23
        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Eduard Petrovich Morozk",format.parse("03.01.2000 03:45:23"), null));
        System.out.println("getDatesWhenSomethingFailed " + logParser.getDatesWhenSomethingFailed( null,null));
        System.out.println("=========================");
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null,null));

        System.out.println("====================================+++++++++++++");
        System.out.println(logParser.getUsersForIP("127.0.0.1", null,null));
        System.out.println("________________________________");
        System.out.println();
        System.out.println(logParser.getDownloadedPluginUsers(null,null));
        System.out.println();
        System.out.println();
        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null,null));
        System.out.println();
        System.out.println(" gets ");
        System.out.println(logParser.execute("get ip"));
        System.out.println("_");
        System.out.println(logParser.execute("get user"));
        System.out.println("_");
        System.out.println(logParser.execute("get date"));
        System.out.println("_");
        System.out.println(logParser.execute("get event"));
        System.out.println("_");
        System.out.println(logParser.execute("get status"));
        System.out.println("==================");
        System.out.println("Another conclusion");
        System.out.println(logParser.execute("get user for event = \"DONE_TASK\""));
//Eduard Petrovich Morozko
        System.out.println();
        System.out.println(logParser.execute("get user for event = \"WRITE_MESSAGE\""));
        System.out.println();
        System.out.println();
        System.out.println(logParser.execute("get ip for date = \"12.12.2013 21:56:30\""));
        System.out.println("===========================================");
        System.out.println();
        System.out.println(logParser.
                execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));

        System.out.println(logParser.execute("get event"));
        System.out.println(logParser.execute("get event for ip"));
        System.out.println(" 79" + logParser.execute("get event for ip = \"value sdk lsakdll asss ss  s\""));
        System.out.println(logParser.
                execute("get event for ip = \"value sdk lsakdll asss ss  s\" and date between \"11.12.2013 0:00:00\" and \"03.02.2020 00:20:20\""));

        System.out.println("Task JR");
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));
        System.out.println("val");
        System.out.println("get ip for date " + logParser.execute("get ip for date = \"12.12.2013 21:56:30\" and date between \"11.11.2013 10:11:12\" and \"03.01.2014 03:45:23\""));
//        "get user for date = "[any_date]" and date between "[after]" and "[before]"

        System.out.println("get user for date " + logParser.execute("get user for date = \"12.12.2013 21:56:30\" and date between \"11.11.2013 10:11:12\" and \"03.01.2014 03:45:23\""));
        System.out.println("get event for date " + logParser.execute("get event for date = \"12.12.2013 21:56:30\" and date between \"11.11.2013 10:11:12\" and \"03.01.2014 03:45:23\""));
        System.out.println("get status for date " + logParser.execute("get status for date = \"12.12.2013 21:56:30\" and date between \"11.11.2013 10:11:12\" and \"03.01.2014 03:45:23\""));
        System.out.println();
        System.out.println("get status for date " + logParser.execute("get status for user = \"Eduard Petrovich Morozko\" and date between \"11.11.2013 10:11:12\" and \"03.01.2014 03:45:23\""));
        System.out.println("TEST");
        System.out.println(logParser.execute("get ip FOR event = \"LOGIN\" and date between \"09.03.2047 05:04:07\" and \"29.2.31020 5:4:7\""));
    }
}
//Вызов метода execute с параметром
// "get ip for date = "[any_date]" and date between "[after]" and "[before]""
// должен возвращать множество уникальных IP адресов, события с которых произведены
// в указанное время [any_date] в период между датами [after] и [before].