package chneau.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dijkstra {
    public class Result {
        public int distance;
        public List<Integer> path = new ArrayList<>();
    }

    public class Info {
        public int id;
        public List<Integer> path = new ArrayList<>();

        public Info(int id) {
            this.id = id;
        }
    }

    public static Result shortest(Graph g, int from, int to) {
        var vertices = new HashMap<Integer, Info>();
        var i = new Info(from);
        vertices.put(from, i);

        return null;
    }
}
