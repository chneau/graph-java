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
