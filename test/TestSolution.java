package com.javarush.task.task39.task3913.test;

import com.javarush.task.task39.task3913.Event;
import com.javarush.task.task39.task3913.LogParser;
import com.javarush.task.task39.task3913.Status;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TestSolution {
    private static LogParser log = new LogParser(Paths.get("/home/alexan/Java/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task39/task3913/logs/"));
    private static DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Test
    public void getUniqueIPsTEST() {
        Set<String> ipLogs = log.getUniqueIPs(null, null);
        List<String> ipArr = Arrays.asList("146.34.15.6", "127.0.0.1", "192.168.100.2", "146.34.15.5", "12.12.12.12", "120.120.120.122", "7.8.9.123");
        Set<String> ip = new HashSet<>(ipArr);

        Assert.assertArrayEquals(ipLogs.toArray(), ip.toArray());
    }

    @Test
    public void getNumberOfUniqueIPsTEST() {
        Set<String> ipLogs = log.getUniqueIPs(null, null);
        int ipCount = 7;

        Assert.assertEquals(ipLogs.size(), ipCount);
    }

    @Test
    public void getIPsForUserTEST() {
        Set<String> ipsName = log.getIPsForUser("Eduard Petrovich Morozko", null, null);
        Set<String> ipsNameTwo = log.getIPsForUser("Vasya Pupkin", null, null);

        String[] ipsCurrentUser = {"146.34.15.6", "146.34.15.5", "127.0.0.1"};
        String[] ipsCurrentUserTwo = {"192.168.100.2", "127.0.0.1"};

        Set<String> ipsSet = new HashSet<>(Arrays.asList(ipsCurrentUser));
        Set<String> ipsSetTwo = new HashSet<>(Arrays.asList(ipsCurrentUserTwo));

        Assert.assertEquals(ipsName, ipsSet);
        Assert.assertEquals(ipsNameTwo, ipsSetTwo);
    }

    @Test
    public void getIPsForEventTEST() {
        Set<String> ipsLog = log.getIPsForEvent(Event.WRITE_MESSAGE, null, null);
        String[] ips = {"146.34.15.6", "146.34.15.5", "127.0.0.1"};
        Set<String> ipsSetExpected = new HashSet<>(Arrays.asList(ips));
        Assert.assertEquals(ipsLog, ipsSetExpected);
    }

    @Test
    public void getIPsForStatusTEST() {
        Set<String> ipsLog = log.getIPsForStatus(Status.OK, null, null);
        String[] ips = {"127.0.0.1", "192.168.100.2", "146.34.15.6", "146.34.15.5", "12.12.12.12", "120.120.120.122", "7.8.9.123"};
        Set<String> ipsSetExpected = new HashSet<>(Arrays.asList(ips));

        Assert.assertEquals(ipsLog, ipsSetExpected);
    }

    @Test
    public void getAllUsers() {
        Set<String> nameSetLog = log.getAllUsers();
        String[] names = {"Amigo", "Vasya Pupkin", "Eduard Petrovich Morozko", "Best user of the BEST"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getNumberOfUsersTEST() {
        int countUser = log.getNumberOfUsers(null, null);
        int countExpected = 4;

        Assert.assertEquals(countExpected, countUser);
    }

    @Test
    public void getNumberOfUserEventsTEST() {
        int countUserEvent = log.getNumberOfUserEvents("Eduard Petrovich Morozko", null, null);
        int countExpected = 5;
        Assert.assertEquals(countExpected, countUserEvent);
    }

    @Test
    public void getUsersForIPTEST() {
        Set<String> nameSetLog = log.getUsersForIP("127.0.0.1", null, null);
        String[] names = {"Amigo", "Vasya Pupkin", "Eduard Petrovich Morozko"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getLoggedUsersTEST() {
        Set<String> nameSetLog = log.getLoggedUsers(null, null);
        String[] names = {"Amigo", "Vasya Pupkin", "Eduard Petrovich Morozko", "Best user of the BEST"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getDownloadedPluginUsersTEST() {
        Set<String> nameSetLog = log.getDownloadedPluginUsers(null, null);
        String name = "Eduard Petrovich Morozko";
        Set<String> nameSetExpected = new HashSet<>(Collections.singletonList(name));

        Assert.assertEquals(nameSetExpected, nameSetLog);
    }

    @Test
    public void getWroteMessageUsersTEST() {
        Set<String> nameSetLog = log.getWroteMessageUsers(null, null);
        String[] names = {"Eduard Petrovich Morozko", "Vasya Pupkin"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetExpected, nameSetLog);
    }

    @Test
    public void getSolvedTaskUsersTEST() {
        Set<String> nameSetLog = log.getSolvedTaskUsers(null, null);
        String[] names = {"Amigo", "Vasya Pupkin", "Eduard Petrovich Morozko"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getSolvedTaskUsersWithNumbTaskTEST() {
        Set<String> nameSetLog = log.getSolvedTaskUsers(null, null, 18);
        String[] names = {"Amigo", "Vasya Pupkin"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getDoneTaskUsersTEST() {
        Set<String> nameSetLog = log.getDoneTaskUsers(null, null);
        String[] names = {"Vasya Pupkin", "Eduard Petrovich Morozko"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getDoneTaskUsersWithNumbTaskTEST() {
        Set<String> nameSetLog = log.getDoneTaskUsers(null, null, 48);
        String[] names = {"Eduard Petrovich Morozko"};
        Set<String> nameSetExpected = new HashSet<>(Arrays.asList(names));

        Assert.assertEquals(nameSetLog, nameSetExpected);
    }

    @Test
    public void getDatesForUserAndEventTEST() {
        try {
            Set<Long> datesLog = log.getDatesForUserAndEvent("Eduard Petrovich Morozko", Event.WRITE_MESSAGE, null, null).stream()
                    .map(Date::getTime)
                    .collect(Collectors.toSet());

            Set<Long> dates = new HashSet<>(Arrays.asList(
                    format.parse("12.12.2013 21:56:30").getTime(),
                    format.parse("11.12.2013 10:11:12").getTime()));

            Assert.assertEquals(dates, datesLog);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getDatesWhenSomethingFailedAndErrorTEST() {
        try {
            Set<Long> datesLog = log.getDatesWhenSomethingFailed(null, null).stream()
                    .map(Date::getTime)
                    .collect(Collectors.toSet());

            Set<Long> dates = new HashSet<>(Arrays.asList(
                    format.parse("05.01.2021 20:22:55").getTime(),
                    format.parse("11.12.2013 10:11:12").getTime(),
                    format.parse("12.12.2013 21:56:30").getTime()));

            Assert.assertEquals(dates, datesLog);

            datesLog = log.getDatesWhenErrorHappened(null, null).stream()
                    .map(Date::getTime)
                    .collect(Collectors.toSet());

            dates = new HashSet<>(Collections.singletonList(
                    format.parse("30.01.2014 12:56:22").getTime()));

            Assert.assertEquals(dates, datesLog);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDateWhenUserLoggedFirstTimeTEST() {
        try {
            long dateLog = log.getDateWhenUserLoggedFirstTime("Eduard Petrovich Morozko", null, null).getTime();
            long dateExpected = format.parse("03.01.2008 03:45:23").getTime();

            Assert.assertEquals(dateLog, dateExpected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDateWhenUserSolvedTaskAndDoneTEST() {
        try {
            long dateLog = log.getDateWhenUserSolvedTask("Vasya Pupkin", 1, null, null).getTime();
//            long dateExpected = format.parse("19.03.2016 00:00:00").getTime();
            long dateExpected = format.parse("19.03.2010 00:00:00").getTime();

            Assert.assertEquals(dateLog, dateExpected);

            dateLog = log.getDateWhenUserDoneTask("Vasya Pupkin", 15, null, null).getTime();
            dateExpected = format.parse("30.08.2009 16:08:40").getTime();

            Assert.assertEquals(dateLog, dateExpected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDatesWhenUserWroteMessageAndDownloadPluginTEST() {
        try {
            Set<Long> datesLog = log.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null).stream()
                    .map(Date::getTime)
                    .collect(Collectors.toSet());

            Set<Long> datesExpected = new HashSet<>(Arrays.asList(
                    format.parse("11.12.2013 10:11:12").getTime(),
                    format.parse("12.12.2013 21:56:30").getTime()));

            Assert.assertEquals(datesExpected, datesLog);

            datesLog = log.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null).stream()
                    .map(Date::getTime)
                    .collect(Collectors.toSet());

            datesExpected = new HashSet<>(Arrays.asList(
                    format.parse("13.09.2013 5:04:50").getTime(),
                    format.parse("13.09.2003 5:04:50").getTime()));

            Assert.assertEquals(datesExpected, datesLog);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllEventsAndCountTEST() {
        Set<Event> eventLog = log.getAllEvents(null, null);

        Set<Event> eventExpected = new HashSet<>(Arrays.asList(
                Event.LOGIN, Event.DOWNLOAD_PLUGIN, Event.WRITE_MESSAGE,
                Event.SOLVE_TASK, Event.DONE_TASK));
        Set<Event> eventUnexpected = new HashSet<>(Arrays.asList(Event.LOGIN, Event.DONE_TASK));

        Assert.assertEquals(eventExpected, eventLog);
        Assert.assertNotEquals(eventLog, eventUnexpected);

        Assert.assertEquals(eventLog.size(), eventExpected.size());
    }

    @Test
    public void getEventsForIPTEST() {
        Set<Event> eventLog = log.getEventsForIP("127.0.0.1", null, null);
        Set<Event> eventExpected = new HashSet<>(Arrays.asList(Event.LOGIN, Event.WRITE_MESSAGE));

        Assert.assertEquals(eventLog, eventExpected);

        Set<Event> eventLogTwo = log.getEventsForIP("146.34.15.5", null, null);
        Set<Event> eventExpectedTwo = new HashSet<>(Arrays.asList(Event.LOGIN, Event.WRITE_MESSAGE, Event.DOWNLOAD_PLUGIN, Event.DONE_TASK));

        Assert.assertEquals(eventLogTwo, eventExpectedTwo);
    }

    @Test
    public void getEventsForUserTEST() {
        Set<Event> eventLog = log.getEventsForUser("Eduard Petrovich Morozko", null, null);
        Set<Event> eventExpected = new HashSet<>(
                Arrays.asList(Event.LOGIN, Event.DOWNLOAD_PLUGIN, Event.WRITE_MESSAGE, Event.DONE_TASK, Event.SOLVE_TASK));

        Assert.assertEquals(eventLog, eventExpected);
    }

    @Test
    public void getFailedEventsAndErrorTEST() {
        Set<Event> eventLog = log.getFailedEvents(null, null);
        Set<Event> eventExpected = new HashSet<>(
                Arrays.asList(Event.WRITE_MESSAGE, Event.DONE_TASK, Event.SOLVE_TASK));

        Assert.assertEquals(eventExpected, eventLog);

        eventLog = log.getErrorEvents(null, null);
        eventExpected = new HashSet<>(Arrays.asList(Event.SOLVE_TASK));

        Assert.assertEquals(eventExpected, eventLog);
    }

    @Test
    public void getNumberOfAttemptToSolveTaskTEST() {
        int logNumber = log.getNumberOfAttemptToSolveTask(18, null, null);
        int numbExpected = 3;

        Assert.assertEquals(logNumber, numbExpected);
    }

    @Test
    public void getAllSolvedTasksAndTheirNumber() {
        Map<Integer, Integer> logMap = log.getAllSolvedTasksAndTheirNumber(null, null);
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(18, 3);
        expectedMap.put(1, 2);
        expectedMap.put(0, 1);

        Assert.assertEquals(logMap, expectedMap);
    }

    @Test
    public void getAllDoneTasksAndTheirNumber() {
        Map<Integer, Integer> logMap = log.getAllDoneTasksAndTheirNumber(null, null);
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(48, 1);
        expectedMap.put(15, 2);

        Assert.assertEquals(logMap, expectedMap);
    }

    @Test
    public void simpleExecuteTEST() {
        Set<Object> setLog = log.execute("get ip");
        Set<Object> expectedSet = new HashSet<>(
                Arrays.asList("120.120.120.122", "146.34.15.6", "146.34.15.5",
                        "192.168.100.2", "12.12.12.12", "127.0.0.1", "7.8.9.123"));

        Assert.assertEquals(setLog, expectedSet);

        setLog = log.execute("get event");
        expectedSet = new HashSet<>(Arrays.asList(Event.DOWNLOAD_PLUGIN, Event.WRITE_MESSAGE, Event.DONE_TASK, Event.SOLVE_TASK, Event.LOGIN));

        Assert.assertEquals(setLog, expectedSet);

        setLog = log.execute("get user");
        expectedSet = new HashSet<>(Arrays.asList("Amigo", "Vasya Pupkin", "Eduard Petrovich Morozko", "Best user of the BEST"));

        Assert.assertEquals(setLog, expectedSet);

        setLog = log.execute("get status");
        expectedSet = new HashSet<>(Arrays.asList(Status.OK, Status.ERROR, Status.FAILED));

        Assert.assertEquals(setLog, expectedSet);

        setLog = log.execute("get ip for user = \"Eduard Petrovich Morozko\"");
        expectedSet = new HashSet<>(Arrays.asList("146.34.15.6", "146.34.15.5", "127.0.0.1"));

        Assert.assertEquals(setLog, expectedSet);
    }
}
