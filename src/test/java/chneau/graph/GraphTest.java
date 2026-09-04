package chneau.graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest {

    @Test
    public void testDijkstraSimple1() {
        Graph g = new Graph();
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 1);
        var res = Dijkstra.shortest(g, 1, 5);
        assertNotNull(res);
        assertEquals(List.of(1, 3, 4, 5), res.getPath());
        assertEquals(3, res.getDistance());
    }

    @Test
    public void testDijkstraSimple2() {
        Graph g = new Graph();
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 1);
        g.addEdge(2, 5, 0);
        var res = Dijkstra.shortest(g, 1, 5);
        assertNotNull(res);
        assertEquals(List.of(1, 2, 5), res.getPath());
        assertEquals(1, res.getDistance());
    }

    @Test
    public void testDijkstraUnreachable() {
        Graph g = new Graph();
        g.addEdge(1, 2, 5);
        g.addEdge(3, 4, 2);
        var res = Dijkstra.shortest(g, 1, 4);
        assertNull(res, "unreachable node should return null");
    }

    @Test
    public void testDijkstraSameSourceAndTarget() {
        Graph g = new Graph();
        g.addEdge(1, 2, 5);
        var res = Dijkstra.shortest(g, 1, 1);
        assertNotNull(res);
        assertEquals(0, res.getDistance());
        assertEquals(List.of(1), res.getPath());
    }

    @Test
    public void testSimplifySimple1() {
        Graph g = new Graph();
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 1);
        Simplify.graph(g);
        assertEquals(Set.of(1, 5), g.vertices.keySet());
        assertEquals(Integer.valueOf(4), g.vertices.get(1).neighbours.get(5));
    }

    @Test
    public void testSimplifyBi1() {
        Graph g = new Graph();
        g.addBiEdge(1, 2, 1);
        g.addBiEdge(2, 3, 1);
        g.addBiEdge(3, 4, 1);
        g.addBiEdge(4, 5, 1);
        Simplify.graph(g);
        assertEquals(Set.of(1, 5), g.vertices.keySet());
        assertEquals(Integer.valueOf(4), g.vertices.get(1).neighbours.get(5));
        assertEquals(Integer.valueOf(4), g.vertices.get(5).neighbours.get(1));
    }

    @Test
    public void testVertexEdgeReplacement() {
        Vertex v = new Vertex();
        v.addEdge(2, 10);
        assertEquals(1, v.order.size());
        assertEquals(10, v.neighbours.get(2));

        // Same cost should be no-op
        v.addEdge(2, 10);
        assertEquals(1, v.order.size());

        // Updated cost
        v.addEdge(2, 5);
        assertEquals(1, v.order.size());
        assertEquals(5, v.neighbours.get(2));

        v.addEdge(3, 2);
        assertEquals(List.of(3, 2), v.order);
    }
}

