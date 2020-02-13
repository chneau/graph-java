package chneau.graph;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    public Map<Integer, Vertex> vertices = new HashMap<>();

    public void addBiEdge(int from, int to, int cost) {
        addEdge(from, to, cost);
        addEdge(to, from, cost);
    }

    public void addEdge(int from, int to, int cost) {
        if (!vertices.containsKey(from)) {
            vertices.put(from, new Vertex());
        }
        if (!vertices.containsKey(to)) {
            vertices.put(to, new Vertex());
        }
        vertices.get(from).addEdge(to, cost);
    }
}
