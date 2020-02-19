package chneau.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.zip.ZipInputStream;

import org.apache.commons.csv.CSVFormat;

public class GTFS {
    public static class Calendar {
        public boolean[] days = new boolean[7];
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class Stop {
        public double lat;
        public double lng;
    }

    public static class StopTime {
        public LocalDateTime arrival;
        public LocalDateTime departure;
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

        var calendars = new HashMap<String, Calendar>(); // service_id
        var stops = new HashMap<String, Stop>(); // stop_id

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
                            c.start = LocalDateTime.parse(record.get("start_date"), yyyymmdd);
                            c.end = LocalDateTime.parse(record.get("end_date"), yyyymmdd);
                            calendars.put(record.get("service_id"), c);
                        }
                    }
                    break;
                case "stops.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            var s = new Stop();
                            s.lat = Double.parseDouble("stop_lat");
                            s.lng = Double.parseDouble("stop_lon");
                            stops.put(record.get("stop_id"),s);
                        }
                    }
                    break;
                case "stop_times.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            System.out.println(record.get("trip_id"));
                            System.out.println(record.get("stop_id"));
                            System.out.println(record.get("arrival_time"));
                            System.out.println(record.get("departure_time"));
                        }
                    }
                    break;
                case "trips.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            System.out.println(record.get("service_id"));
                            System.out.println(record.get("trip_id"));
                        }
                    }
                    break;
            }
        }
        zis.close();
        return null;
    }
}
