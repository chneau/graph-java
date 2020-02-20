package chneau.graph;

public class GraphTest {
    // @Test
    // public void testDijkstraSimple1() {
    //     Graph g = new Graph();
    //     g.addEdge(1, 2, 1);
    //     g.addEdge(1, 3, 1);
    //     g.addEdge(2, 4, 5);
    //     g.addEdge(3, 4, 1);
    //     g.addEdge(4, 5, 1);
    //     var res = Dijkstra.shortest(g, 1, 5, LocalDateTime.now());
    //     assertEquals(Arrays.asList(1, 3, 4, 5), res.path);
    //     assertEquals(3, res.distance);
    // }

    // @Test
    // public void testDijkstraSimple2() {
    //     Graph g = new Graph();
    //     g.addEdge(1, 2, 1);
    //     g.addEdge(1, 3, 1);
    //     g.addEdge(2, 4, 5);
    //     g.addEdge(3, 4, 1);
    //     g.addEdge(4, 5, 1);
    //     g.addEdge(2, 5, 0);
    //     var res = Dijkstra.shortest(g, 1, 5);
    //     assertEquals(Arrays.asList(1, 2, 5), res.path);
    //     assertEquals(1, res.distance);
    // }

    // @Test
    // public void testSimplifySimple1() {
    //     Graph g = new Graph();
    //     g.addEdge(1, 2, 1);
    //     g.addEdge(2, 3, 1);
    //     g.addEdge(3, 4, 1);
    //     g.addEdge(4, 5, 1);
    //     Simplify.graph(g);
    //     assertEquals(Arrays.asList(1, 5).stream().collect(Collectors.toSet()), g.vertices.keySet());
    //     assertEquals(Integer.valueOf(4), g.vertices.get(1).neighbours.get(5));
    // }

    // @Test
    // public void testSimplifyBi1() {
    //     Graph g = new Graph();
    //     g.addBiEdge(1, 2, 1);
    //     g.addBiEdge(2, 3, 1);
    //     g.addBiEdge(3, 4, 1);
    //     g.addBiEdge(4, 5, 1);
    //     Simplify.graph(g);
    //     assertEquals(Arrays.asList(1, 5).stream().collect(Collectors.toSet()), g.vertices.keySet());
    //     assertEquals(Integer.valueOf(4), g.vertices.get(1).neighbours.get(5));
    //     assertEquals(Integer.valueOf(4), g.vertices.get(5).neighbours.get(1));
    // }
}
