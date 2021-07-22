package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private List<User> userList = new ArrayList<>();

    public LogParser(Path logDir) {
        this.logDir = logDir;
        readUsers();
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return userList.stream()
                .filter(user -> isValid(user, after, before))
                .map(User::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(u -> u.getName().equalsIgnoreCase(user))
                .map(User::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(u -> u.getEvent().equals(event))
                .map(User::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(u -> u.getStatus().equals(status))
                .map(User::getIp)
                .collect(Collectors.toSet());
    }

//    =================
//      UserQuery
//    =================

    @Override
    public Set<String> getAllUsers() {
        return userList.stream()
                .map(User::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return (int) getValidUsers(after, before).stream()
                .map(User::getName)
                .distinct().count();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) getValidUsers(after, before).stream()
                .filter(u -> u.getName().equalsIgnoreCase(user))
                .map(User::getEvent)
                .distinct().count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getIp().equals(ip))
                .map(User::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getUserSetName(Event.LOGIN, after, before, -1);
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getUserSetName(Event.DOWNLOAD_PLUGIN, after, before, -1);
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getUserSetName(Event.WRITE_MESSAGE, after, before, -1);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getUserSetName(Event.SOLVE_TASK, after, before, -1);
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUserSetName(Event.SOLVE_TASK, after, before, task);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUserSetName(Event.DONE_TASK, after, before, -1);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUserSetName(Event.DONE_TASK, after, before, task);
    }

//    __________________

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getDatesWhen(user, event, null, after, before);
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {             ///
        return getDatesWhen(null, null, Status.FAILED, after, before);
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesWhen(null, null, Status.ERROR, after, before);
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return getDateWhen(Event.LOGIN, user, -1, after, before);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return getDateWhen(Event.SOLVE_TASK, user, task, after, before);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return getDateWhen(Event.DONE_TASK, user, task, after, before);
    }


    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesWhen(user, Event.WRITE_MESSAGE, null, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesWhen(user, Event.DOWNLOAD_PLUGIN, null, after, before);
    }

//    =====================
//          EventQuery
//    =====================

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getValidUsers(after, before).stream().
                map(User::getEvent).
                collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getIp().equals(ip))
                .map(User::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(u -> u.getName().equalsIgnoreCase(user))
                .map(User::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getStatus().equals(Status.FAILED))
                .map(User::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getStatus().equals(Status.ERROR))
                .map(User::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int) getValidUsers(after, before).stream()
                .filter(user -> user.getEvent().equals(Event.SOLVE_TASK))
                .filter(user -> user.getTask() == task)
                .count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int) getValidUsers(after, before).stream()
                .filter(user -> isValid(user, after, before))
                .filter(user -> user.getTask() == task)
                .filter(user -> user.getEvent().equals(Event.DONE_TASK))
                .count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getEvent().equals(Event.SOLVE_TASK))
                .collect(Collectors.toMap(
                        User::getTask,
                        User -> 1,
                        Integer::sum
                ));

//        Map<Integer, Integer> result = new HashMap<>();
//        for (User user : userList) {
//            if (isValid(user, after, before)) {
//                if (user.getEvent().equals(Event.SOLVE_TASK)) {
//                    int task = user.getTask();
//                    int count = result.getOrDefault(task, 0);
//                    result.put(task, count + 1);
//                }
//            }
//        }
//        return result;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return getValidUsers(after, before).stream()
                .filter(user -> user.getEvent().equals(Event.DONE_TASK))
                .collect(Collectors.toMap(User::getTask,
                        User -> 1,
                        Integer::sum));
    }

//      =====================
//              QLQuery
//      =====================

    @Override
    public Set<Object> execute(String query) {
        query = query.toLowerCase(Locale.ROOT);
        Pattern pattern = Pattern.compile("get (?<field>\\w+)( for (?<field2>\\w+))?( = \"(?<value>.*?)\")?( and date between \"(?<after>.*?)\" and \"(?<before>.*?)\")?");
        Matcher matcher = pattern.matcher(query);

        if (!matcher.find()) System.err.println("Pattern exception");
//
        String field = matcher.group("field");
        String field2 = matcher.group("field2");
        String value = matcher.group("value");
        String after = matcher.group("after");
        String before = matcher.group("before");

        if (after != null && before != null) {
            return betweenDate(field, field2, value, after, before);
        } else if (field2 != null && value != null) {
            switch (field2) {
                case "date":
                    return dateQuery(field, value);
                default:
                    return userList.stream()
                            .filter(user -> user.getFieldForName(field2).toString().equalsIgnoreCase(value))
                            .map(user -> user.getFieldForName(field))
                            .collect(Collectors.toSet());
            }
        } else {
            return simpleQuery(field);
        }
    }

    private Set<Object> betweenDate(String field, String field2, String value, String after, String before) {
        DateFormat format = new SimpleDateFormat("d.M.y H:m:s");

        Date afterDate = null;
        Date beforeDate = null;

        try {
            afterDate = format.parse(after);
            beforeDate = format.parse(before);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Date finalAfterDate = afterDate;
        final Date finalBeforeDate = beforeDate;

        switch (field2) {
            case "date":
                Date currentDate = null;
                try {
                    currentDate = format.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final long fiTime = currentDate.getTime();
                return userList.stream()
                        .filter(user -> isValid(user,finalAfterDate,finalBeforeDate))
                        .filter(user -> user.getDate().getTime() == fiTime)
                        .map(user -> user.getFieldForName(field))
                        .collect(Collectors.toSet());
            default:
                return userList.stream()
                        .filter(user -> isValid(user, finalAfterDate, finalBeforeDate))
                        .filter(user -> user.getFieldForName(field2).toString().equalsIgnoreCase(value))
                        .map(user -> user.getFieldForName(field))
                        .collect(Collectors.toSet());
        }
    }

    private Set<Object> dateQuery(String field, String value) {
//        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        DateFormat format = new SimpleDateFormat("d.M.y H:m:s");
        Date currentDate = null;
        try {
            currentDate = format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final long fiTime = currentDate.getTime();
        return userList.stream()
                .filter(user -> user.getDate().getTime() == fiTime)
                .map(user -> user.getFieldForName(field))
                .collect(Collectors.toSet());
    }

    private Set<Object> simpleQuery(String query) {
        switch (query) {
            case "ip":
                return new HashSet<>(getUniqueIPs(null, null));
            case "user":
                return new HashSet<>(getAllUsers());
            case "event":
                return new HashSet<>(getAllEvents(null, null));
            case "date":
                return userList.stream()
                        .map(User::getDate)
                        .collect(Collectors.toSet());
            case "status":
                return userList.stream()
                        .map(User::getStatus)
                        .collect(Collectors.toSet());
            default:
                throw new IllegalArgumentException(query);
        }
    }

    //      =====================

    private Set<String> getUserSetName(Event event, Date after, Date before, int task) {
        if (task == -1) {
            return getValidUsers(after, before).stream()
                    .filter(user -> user.getEvent().equals(event))
                    .map(User::getName)
                    .collect(Collectors.toSet());
        } else {
            return getValidUsers(after, before).stream()
                    .filter(user -> user.getEvent().equals(event))
                    .filter(user -> user.getTask() == task)
                    .map(User::getName)
                    .collect(Collectors.toSet());
        }
    }

    private Set<Date> getDatesWhen(String user, Event event, Status status, Date after, Date before) {
        if (event == null & user == null) {
            return getValidUsers(after, before).stream()
                    .filter(u -> u.getStatus().equals(status))
                    .map(User::getDate)
                    .collect(Collectors.toSet());
        } else {
            return getValidUsers(after, before).stream()
                    .filter(u -> u.getName().equalsIgnoreCase(user))
                    .filter(u -> u.getEvent().equals(event))
                    .map(User::getDate)
                    .collect(Collectors.toSet());
        }
    }

    private Date getDateWhen(Event event, String user, int task, Date after, Date before) {
        try {
            if (task == -1) {
                return userList.stream()
                        .filter(u -> isValid(u, after, before))
                        .filter(u -> u.getName().equalsIgnoreCase(user))
                        .filter(u -> u.getEvent().equals(event))
                        .min(User::compareDate).get().getDate();
            } else {
                return userList.stream()
                        .filter(u -> isValid(u, after, before))
                        .filter(u -> u.getName().equalsIgnoreCase(user))
                        .filter(u -> u.getEvent().equals(event))
                        .filter(u -> u.getTask() == task)
                        .min(User::compareDate).get().getDate();
            }
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private void readUsers() {
        try {
            Files.walk(logDir)
                    .filter(path -> !Files.isDirectory(path))
                    .filter(path -> path.toFile().getName().toLowerCase().endsWith(".log"))
                    .forEach(path -> {
                        try {
                            Files.lines(path)
                                    .forEach(line -> userList.add(UserParser.getUser(line)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<User> getValidUsers(Date after, Date before) {
        return userList.stream()
                .filter(user -> isValid(user, after, before))
                .collect(Collectors.toSet());
    }

    private boolean isValid(User user, Date after, Date before) {
        after = (after == null) ? new Date(0) : after;
        before = (before == null) ? new Date(Long.MAX_VALUE) : before;
        return (user.getDate().getTime() > after.getTime()) & (user.getDate().getTime() < before.getTime());
    }
}