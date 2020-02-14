package chneau.graph;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {
    public static class Result {
        public int distance;
        public List<Integer> path = new ArrayList<>();
    }

    public static class Info {
        public int id;
        public List<Integer> path = new ArrayList<>();

        public Info(int id) {
            this.id = id;
        }
    }

    public static Result shortest(Graph g, int from, int to) {
        var vertices = new HashMap<Integer, Info>();
        var i = new Dijkstra.Info(from);
        vertices.put(from, i);

        var visited = new HashMap<Integer, Boolean>();
        var toVisit = new ArrayList<>(); //SOS
        insertOrdered(vertices.get(from), toVisit);
        boolean found = false; //you never set it to true
        int aux = toVisit.size();
        while(aux > 0 && !found){
            var visiting = popFront(toVisit);
            for (int j = 0; j < g.vertices.size(); j++) {
                //S O SSSSSSSSS
                List<Integer> k = g.vertices.get(visiting.id).order;
                if (visited.containsKey(k)) {
                    continue;
                }
                var v = g.vertices.get(visiting.id).neighbours.get(k);
                var lp = (vertices.get(visiting.id).path).size();

                if (!vertices.containsKey(k)) {
                    vertices.get(k).id = k;
                    vertices.get(k).distance = v + vertices.get(visiting.id).distance;
                    vertices.get(k).path =  append(vertices.get(visiting.id).path[:lp:lp], k);
                    toVisit.insertOrdered(vertices.get(k), toVisit);
                } else {
                    var newDistance = v + vertices.get(visiting.id).distance;
                    if (vertices.get(k).distance > newDistance) {
                        vertices.get(k).distance = newDistance;
                        vertices.get(k).path =  append(vertices.get(visiting.id).path[:lp:lp], k);
                    }
                }
            }
            if (visiting.id == to) {
                break;
            }
            visited.put(visiting.id, true);
            aux--;
        }
        if (!vertices.containsKey(to)) {
            return null;
        }
        return vertices.get(to);
    }

    private static Info popFront(ArrayList<Object> toVisit) {
        var e = toVisit.get(0);
        toVisit.remove(0);
        return e;
    }

    private static void insertOrdered(Info v, ArrayList<Object> toVisit) {
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
