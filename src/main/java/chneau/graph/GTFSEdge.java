package chneau.graph;

import chneau.timetable.TimeTable;
import java.time.Duration;
import java.time.LocalDateTime;

/** GTFSEdge */
public class GTFSEdge implements Cost {
    public TimeTable tt;

    public Duration getCost(LocalDateTime ldt) {
        var when = tt.when(ldt, Duration.ofNanos(1), 1);
        if (when == null) {
            return Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        }
        return Duration.between(ldt, when);
    }
}
