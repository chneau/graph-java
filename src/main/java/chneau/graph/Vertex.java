package chneau.graph;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    public final Map<Integer, Edge> neighbours = new HashMap<>();

    public void addEdge(int to, Edge edge) {
        var val = neighbours.get(to);
        if (val != null) {
            neighbours.put(to, edge);
            return;
        }
        neighbours.put(to, edge);
    }

    @Override
    public String toString() {
        return neighbours.keySet().toString();
    }
}
