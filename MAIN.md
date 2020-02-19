# Main.java

## Exploring the parser

```java
package chneau.graph;

import java.io.File;
import java.util.ArrayList;

import com.graphhopper.reader.ReaderElement;
import com.graphhopper.reader.ReaderNode;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.reader.osm.OSMInputFile;

public class Main {

    public static void main(String[] args) {
        var nodes = new ArrayList<ReaderNode>();
        var ways = new ArrayList<ReaderWay>();
        try (var in = new OSMInputFile(new File("./private/scotland.pbf"))) {
            in.open();
            ReaderElement item;
            out:
            while ((item = in.getNext()) != null) {
                if (nodes.size() % 1e5 == 0) {
                    System.out.println(nodes.size());
                }
                switch (item.getType()) {
                case ReaderElement.NODE:
                    final var node = (ReaderNode) item;
                    // System.out.println(node);
                    nodes.add(node);
                    break;
                case ReaderElement.WAY:
                    final var way = (ReaderWay) item;
                    // System.out.println(way);
                    ways.add(way);
                    break;
                case ReaderElement.RELATION:
                    break out;

                default:
                    break;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Problem while parsing file", ex);
        }

    }
}
```

## Reading GTFS data

```java 
package chneau.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;
import org.apache.commons.csv.CSVFormat;

// trips.txt read column service_id
// stop_times.txt        ^ trip_id
//                              get stop_id,arrival_time,departure_time
// calendar.txt          ^
// monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date
// stops.txt                    ^ stop_id
//                              get stop_lat,stop_lon
public class Main {

    public static void main(String[] args) throws Exception {
        var f = new File("private/leeds.gtfs");
        var fis = new FileInputStream(f);
        var zis = new ZipInputStream(fis);
        var isr = new InputStreamReader(zis);
        for (var e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) {
            switch (e.getName()) {
                case "calendar.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            System.out.println(record.get("service_id"));
                            System.out.println(record.get("monday"));
                            System.out.println(record.get("tuesday"));
                            System.out.println(record.get("wednesday"));
                            System.out.println(record.get("thursday"));
                            System.out.println(record.get("friday"));
                            System.out.println(record.get("saturday"));
                            System.out.println(record.get("sunday"));
                            System.out.println(record.get("start_date"));
                            System.out.println(record.get("end_date"));
                        }
                    }
                    break;
                case "stops.txt":
                    {
                        var csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
                        for (var record : csvParser) {
                            System.out.println(record.get("stop_id"));
                            System.out.println(record.get("stop_lat"));
                            System.out.println(record.get("stop_lon"));
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
    }
}
```
