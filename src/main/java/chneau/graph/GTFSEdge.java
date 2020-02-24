package chneau.graph;

import java.time.Duration;
import java.time.LocalDateTime;

import chneau.timetable.TimeTable;

/** GTFSEdge */
public class GTFSEdge implements Cost {
    public TimeTable tt;
    public Duration getCost(LocalDateTime c) {
        // var t = new TimeTable(1, OpenHours.parse("mo 06:00-16:00"));

        return null;
    }
}
