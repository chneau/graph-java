package chneau.graph;

import java.util.ArrayList;
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

        public Info(int id) {
            this.id = id;
        }
    }

    public static Result shortest(Graph g, int from, int to) {
        var vertices = new HashMap<Integer, Info>();
        var i = new Dijkstra.Info(from);
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
                var info = vertices.get(id);
                if (!vertices.containsKey(id)) {
                    info.id = id;
                    info.distance = v + vertices.get(visiting.id).distance;
                    info.path = new ArrayList<>(vertices.get(visiting.id).path);
                    info.path.add(id);
                    insertOrdered(info, toVisit);
                } else {
                    var newDistance = v + vertices.get(visiting.id).distance;
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

    private static void insertOrdered(Info v, List<Info> toVisit) { // *1
        if (toVisit.size() == 0) {
            toVisit.add(0, v);
            return;
        }
        var back = toVisit.get(toVisit.size());
        // Help
        if (back.distance < v.distance) {
            toVisit.add(toVisit.size() + 1, v);
            ;
            return;
        }
        int i = 0;
        Info current = (Info) toVisit.get(i);
        var next = current;
        // heeeelp
        while (current.path.size() < v.path.size() && current.id != v.id) {
            i++;
            next = (Info) toVisit.get(i);
            if (next == null) {
                break;
            }
            current = next;
        }
        if (current.id == v.id) {
            return;
        }
        toVisit.add(i + 1, v);

    }
}
