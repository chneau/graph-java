package chneau.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class Dijkstra {
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

        return null;
    }
}
