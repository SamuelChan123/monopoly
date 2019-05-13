package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventLog {

    private List<String> eventLog;

    public EventLog() {
        eventLog = new ArrayList<>();
    }

    public void addAndPrintEvent(String event) {
        eventLog.add(event);
        System.out.println(event);
    }

    public String getLogAsString() {
        StringBuilder eventLogString = new StringBuilder();
        List<String> reversed = new ArrayList<>(eventLog);
        Collections.reverse(reversed);
        for (String s : reversed) {
            eventLogString.append(s).append(System.lineSeparator());
        }
        return eventLogString.toString();
    }
}
