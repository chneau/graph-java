package chneau.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex {
    public final Map<Integer, Integer> neighbours = new HashMap<>();
    public List<Integer> order = new ArrayList<>();

    public void sort() {
        Collections.sort(order, (Integer i, Integer j) -> neighbours.get(i) - neighbours.get(j));
    }

    public void addEdge(int to, int cost) {
        var val = neighbours.get(to);
        if (val != null) {
            if (val == cost) {
                return;
            }
            neighbours.put(to, cost);
            sort();
            return;
        }
        neighbours.put(to, cost);
        order.add(to);
        sort();
    }
}
