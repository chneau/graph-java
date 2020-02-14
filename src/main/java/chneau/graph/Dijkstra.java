package chneau.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {
    public static class Result {
        public int distance;
        public List<Integer> path = new ArrayList<>();
    }

    public static class Info extends Result {
        public int id;

        public Info(int id, List<Integer> path, int distance) {
            this.id = id;
            this.path = path;
            this.distance = distance;
        }
    }

    public static Result shortest(Graph g, int from, int to) {
        var vertices = new HashMap<Integer, Info>();
        var i = new Dijkstra.Info(from, Arrays.asList(from),0);
        vertices.put(from, i);
        var visited = new HashSet<Integer>();
        var toVisit = new LinkedList<Info>(); // to extends with insertOrdered *1
        insertOrdered(vertices.get(from), toVisit);
        while (toVisit.size() > 0) {
            var visiting = popFront(toVisit);
            for (var id : g.vertices.get(visiting.id).order) {
                if (visited.contains(id)) {
                    continue;
                }
                var v = g.vertices.get(visiting.id).neighbours.get(id);
                if (!vertices.containsKey(id)) {
                    var newPath = new ArrayList<>(vertices.get(visiting.id).path);
                    newPath.add(id);
                    var info = new Dijkstra.Info(id, newPath,v + vertices.get(visiting.id).distance);
                    vertices.put(id, info);
                    insertOrdered(info, toVisit);
                } else {
                    var newDistance = v + vertices.get(visiting.id).distance;
                    var info = vertices.get(id);
                    if (info.distance > newDistance) {
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
        if (back.distance < v.distance) {
            toVisit.addLast(v);
            return;
        }
        int i = 0;
        Info current = toVisit.peekFirst();
        var next = current;
        while (current.distance < v.distance && current.id != v.id) {
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
}
