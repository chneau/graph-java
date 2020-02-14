package chneau.graph;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class GraphTest {
    @Test
    public void testDijkstraSimple1() {
        Graph example = new Graph();
        example.addEdge(1, 2, 1);
        example.addEdge(1, 3, 1);
        example.addEdge(2, 4, 5);
        example.addEdge(3, 4, 1);
        example.addEdge(4, 5, 1);
        var res = Dijkstra.shortest(example, 1, 5);
        assertEquals(Arrays.asList(1, 3, 4, 5), res.path);
        assertEquals(3, res.distance);
    }

    @Test
    public void testDijkstraSimple2() {
        Graph example = new Graph();
        example.addEdge(1, 2, 1);
        example.addEdge(1, 3, 1);
        example.addEdge(2, 4, 5);
        example.addEdge(3, 4, 1);
        example.addEdge(4, 5, 1);
        example.addEdge(2, 5, 0);
        var res = Dijkstra.shortest(example, 1, 5);
        assertEquals(Arrays.asList(1,2,5), res.path);
        assertEquals(1, res.distance);
    }
}
