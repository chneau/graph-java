package chneau.graph;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {
    public static class Result {
        public Duration distance;
        public List<Integer> path = new ArrayList<>();
    }

    public static class Info extends Result {
        public int id;

        public Info(int id, List<Integer> path, Duration distance) {
            this.id = id;
            this.path = path;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "id:" + id + " path:" + path + " distance:" + distance;
        }
    }

    public static Result shortest(Graph g, int from, int to, LocalDateTime clock) {
        var vertices = new HashMap<Integer, Info>();
        var i = new Info(from, Arrays.asList(from), Duration.ofMillis(0));
        vertices.put(from, i);
        var visited = new HashSet<Integer>();
        var toVisit = new LinkedList<Info>(); // to extends with insertOrdered *1
        insertOrdered(vertices.get(from), toVisit);
        while (toVisit.size() > 0) {
            var visiting = popFront(toVisit);
            // todo: <Integer, LocalDateTime>
            var results = new HashMap<Integer, Duration>();
            for (var e : g.vertices.get(visiting.id).neighbours.entrySet()) {
                results.put(e.getKey(), e.getValue().getCost(clock));
            }
            for (var e : results.entrySet()) { // var id : g.vertices.get(visiting.id).order
                var id = e.getKey();
                if (visited.contains(id)) {
                    continue;
                }
                var v = e.getValue();
                if (!vertices.containsKey(id)) {
                    var newPath = new ArrayList<>(vertices.get(visiting.id).path);
                    newPath.add(id);
                    var info = new Dijkstra.Info(id, newPath, vertices.get(visiting.id).distance.plus(v));
                    vertices.put(id, info);
                    insertOrdered(info, toVisit);
                } else {
                    var newDistance = vertices.get(visiting.id).distance.plus(v);
                    var info = vertices.get(id);
                    if (!info.distance.minus(newDistance).isNegative()) {
                        info.distance = newDistance;
                        info.path = new ArrayList<>(vertices.get(visiting.id).path);
                        info.path.add(id);
                    }
                }
            }
            if (visiting.id == to) {
                break;
            }
            visited.add(visiting.id);
        }
        if (!vertices.containsKey(to)) {
            return null;
        }
        return vertices.get(to);
    }

    private static Info popFront(List<Info> toVisit) {
        var e = toVisit.get(0);
        toVisit.remove(0);
        return e;
    }

    private static void insertOrdered(Info v, LinkedList<Info> toVisit) { // *1
        var back = toVisit.peekLast();
        if (back == null) {
            toVisit.push(v);
            return;
        }
        if (back.distance.minus(v.distance).isNegative()) {
            toVisit.addLast(v);
            return;
        }
        int i = 0;
        Info current = toVisit.peekFirst();
        var next = current;
        while (current.distance.minus(v.distance).isNegative() && current.id != v.id) {
            i++;
            if (i == toVisit.size() - 1) {
                break;
            }
            next = toVisit.get(i);
            current = next;
        }
        if (current.id == v.id) {
            return;
        }
        toVisit.add(i + 1, v);
    }

    private static int calculateCost(Date clock, List<String> transportModes, int from, int to,
            HashMap<Integer, Info> vertices) {
        var time = clock.getTime();
        var X = 0; // update
        double trafficFactor;
        if (time < X) {
            // we also have to take into account location
            trafficFactor = 1.5;
        } else {
            trafficFactor = 1.1;
        }
        var redLightsTime = 0; // update
        if (transportModes.contains("car") && transportModes.contains("bus")) {
            var cost1 = calcCarTime(from, to, vertices, trafficFactor, redLightsTime);
            var cost2 = calcBusTime(time, from, to, vertices, trafficFactor, redLightsTime);
            return getMin(cost1, cost2);
        } else if (transportModes.contains("car")) {
            var cost = calcCarTime(from, to, vertices, trafficFactor, redLightsTime);
            return cost;
        } else if (transportModes.contains("bus")) {
            var cost = calcBusTime(time, from, to, vertices, trafficFactor, redLightsTime);
            return cost;
        } else { // more transport modes?
            var cost1 = calcCarTime(from, to, vertices, trafficFactor, redLightsTime);
            var cost2 = calcBusTime(time, from, to, vertices, trafficFactor, redLightsTime);
            var cost3 = calcBicycleTime(from, to, vertices, trafficFactor, redLightsTime);
            return getMin(cost1, cost2, cost3);
        }
    }

    private static int calcCarTime(int from, int to, HashMap<Integer, Info> vertices, double trafficFactor,
            int redLightsTime) {
        var drivingTime = calcDistance(from, to, vertices) / 35;
        return (int) (trafficFactor * (redLightsTime + drivingTime));
    }

    private static int calcBusTime(long time, int from, int to, HashMap<Integer, Info> vertices, double trafficFactor,
            int redLightsTime) {
        var busStop = 0; // location
        var walkToBusTime = calcDistance(from, busStop, vertices) / 3;
        var busArrivingTime = 0;
        var waitForBusTime = (int) (busArrivingTime - (time + walkToBusTime));
        var busTime = calcDistance(busStop, to, vertices) / 25;
        return (int) (trafficFactor * (redLightsTime + busTime) + walkToBusTime + waitForBusTime);
    }

    private static int calcBicycleTime(int from, int to, HashMap<Integer, Info> vertices, double trafficFactor,
            int redLightsTime) {
        var ridingTime = calcDistance(from, to, vertices) / 10;
        return (int) (trafficFactor * (redLightsTime + ridingTime));
    }

    private static int calcDistance(int from, int to, HashMap<Integer, Info> vertices) {
        return 0;
    }

    private static int getMin(int x, int y) {
        return Math.min(x, y);
    }

    private static int getMin(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }
}
