package chneau.graph;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    public final Map<Integer, Edge> neighbours = new HashMap<>();

    public void addEdge(int to, int cost) {
        var edge = new Edge();
        edge.distance = (double) cost;
        edge.speed = 1.;
        var val = neighbours.get(to);
        if (val != null) {
            neighbours.put(to, edge);
            return;
        }
        neighbours.put(to, edge);
    }
}
