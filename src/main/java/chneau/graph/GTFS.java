package chneau.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.apache.commons.csv.CSVFormat;

public class GTFS {
    public HashMap<String, Map<String, StopTime>> stopTimes = new HashMap<>(); // trip_id + stop_id
    public HashMap<String, Calendar> calendars = new HashMap<>(); // trip_id
    public HashMap<String, Stop> stops = new HashMap<>(); // stop_id

    public static class Calendar {
        public boolean[] days = new boolean[7];
        public LocalDateTime start;
        public LocalDateTime end;

        public String daysAsString() {
            var res = new ArrayList<String>();
            var days = Arrays.asList("mo", "tu", "we", "th", "fr", "sa", "su");
            for (int i = 0; i < this.days.length; i++) {
                if (this.days[i]) {
                    res.add(days.get(i));
                }
            }
            return String.join(",", res);
        }
    }

    public static class Stop {
        public double lat;
        public double lng;
    }

    public static class StopTime { // TOFIX
        public String arrival;
        public String departure;
    }

    private static boolean bool(String str) {
        if (str.equals("0")) {
            return false;
        }
        return true;
    }

    public static GTFS read(String fpath) throws IOException {
        var f = new File(fpath);
        var fis = new FileInputStream(f);
        var zis = new ZipInputStream(fis);
        var isr = new InputStreamReader(zis);

        var stops = new HashMap<String, Stop>(); // stop_id
        var stopTimes = new HashMap<String, Map<String, StopTime>>(); // trip_id stop_id

        var trips = new HashMap<String, Set<String>>(); // service_id
        var calendars = new HashMap<String, Calendar>(); // service_id

        var yyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (var e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) {
            switch (e.getName()) {
                case "calendar.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            var c = new Calendar();
                            c.days[0] = bool(record.get("monday"));
                            c.days[1] = bool(record.get("tuesday"));
                            c.days[2] = bool(record.get("wednesday"));
                            c.days[3] = bool(record.get("thursday"));
                            c.days[4] = bool(record.get("friday"));
                            c.days[5] = bool(record.get("saturday"));
                            c.days[6] = bool(record.get("sunday"));
                            c.start =
                                    LocalDate.parse(record.get("start_date"), yyyymmdd)
                                            .atStartOfDay();
                            c.end =
                                    LocalDate.parse(record.get("end_date"), yyyymmdd)
                                            .atStartOfDay();
                            calendars.put(record.get("service_id"), c);
                        }
                    }
                    break;
                case "stops.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            var s = new Stop();
                            s.lat = Double.parseDouble(record.get("stop_lat"));
                            s.lng = Double.parseDouble(record.get("stop_lon"));
                            stops.put(record.get("stop_id"), s);
                        }
                    }
                    break;
                case "stop_times.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            var s = new StopTime();
                            s.arrival = record.get("arrival_time");
                            s.departure = record.get("departure_time");
                            var tID = record.get("trip_id");
                            var v = stopTimes.getOrDefault(tID, new HashMap<>());
                            v.put(record.get("stop_id"), s);
                            stopTimes.put(tID, v);
                        }
                    }
                    break;
                case "trips.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            var sID = record.get("service_id");
                            var val = trips.getOrDefault(sID, new HashSet<>());
                            val.add(record.get("trip_id"));
                            trips.put(sID, val);
                        }
                    }
                    break;
            }
        }

        var calendarsByTrip = new HashMap<String, Calendar>(); // trip_id
        for (var e : trips.entrySet()) {
            var serviceID = e.getKey();
            var tripIDs = e.getValue();

            for (var tripID : tripIDs) {
                calendarsByTrip.put(tripID, calendars.get(serviceID));
            }
        }
        zis.close();
        var gtfs = new GTFS();
        gtfs.calendars = calendarsByTrip;
        gtfs.stopTimes = stopTimes;
        gtfs.stops = stops;
        return gtfs;
    }

    public Graph toGraph() {
        var g = new Graph();

        return g;
    }
}
