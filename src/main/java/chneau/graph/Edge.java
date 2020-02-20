package chneau.graph;

import java.time.Duration;
import java.time.LocalDateTime;

// public interface Cost {
//     public int getCost();
// }

// public class Edge implements Cost {
//     //
//     private Integer distance;
//     private Integer speedLimit;
//     //

//     public int getCost() {
//         return distance*speedLimit;
//     }
// }

public class Edge {
    public Double distance; // in m
    public Double speed; // in km/h

    public Duration getCost(LocalDateTime c) {
        Double v = distance / speed * 3600;
        return Duration.ofMillis(v.longValue());
    }

    @Override
    public String toString() {
        return "Distance:"+distance+ " Speed:"+speed;
    }
}

// public class TransportEdge implements Cost {
// //
// private Integer distance;
// private Integer speedLimit;
// // timetable
// //

// public int getCost() { // time
// return distance*speedLimit; // + eventual wait
// }
// }
