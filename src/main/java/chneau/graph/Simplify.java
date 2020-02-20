package chneau.graph;

// public class Simplify {

//     private static void removeEdge(Vertex v, Integer edge) {
//         v.neighbours.remove(edge);
//         v.order.remove(edge);
//     }

//     public static void graph(Graph g) {
//         int l = g.vertices.size();
//         while (true) {
//             simplify(g);
//             if (l == g.vertices.size()) {
//                 break;
//             }
//             l = g.vertices.size();
//         }
//         while (true) {
//             biSimplify(g);
//             if (l == g.vertices.size()) {
//                 break;
//             }
//             l = g.vertices.size();
//         }
//     }

//     private static void simplify(Graph g) {
//         var optimisable = new HashSet<Integer>();
//         var where = new HashMap<Integer, List<Integer>>();
//         for (var e : g.vertices.entrySet()) {
//             var k = e.getKey();
//             var v = e.getValue();
//             if (v.order.size() == 1) { // if edge only going to one vertix
//                 optimisable.add(k);
//             }
//             for (var i : v.order) { // map where a vertix appear
//                 var list = where.getOrDefault(i, new ArrayList<Integer>());
//                 list.add(k);
//                 where.put(i, list);
//             }
//         }
//         for (var k : new HashSet<>(optimisable)) {
//             var w = where.get(k);
//             if (w == null || w.size() != 1) { // remove vertex with multiple parents
//                 optimisable.remove(k);
//             }
//         }
//         for (var k : new HashSet<>(where.keySet())) {
//             if (!optimisable.contains(k)) { // remove useless data on where map
//                 where.remove(k);
//             }
//         }
//         for (var mid : optimisable) {
//             int from = where.get(mid).get(0);
//             if (mid == from) { // a round has been reduced
//                 continue;
//             }
//             if (!g.vertices.containsKey(from)) {
//                 continue;
//             }
//             int to = g.vertices.get(mid).order.get(0);
//             if (mid == to) {
//                 continue;
//             }
//             if (from == to) { // don't remove end of bi directional path
//                 continue;
//             }
//             var ok = simplifyVertices(g, from, mid, to);
//             if (!ok) {
//                 System.out.println("NOT OK FOR" + from + " " + mid + " " + to);
//             }
//             g.vertices.remove(mid);
//         }
//     }

//     private static void biSimplify(Graph g) {
//         var optimisable = new HashSet<Integer>();
//         var where = new HashMap<Integer, List<Integer>>();
//         for (var e : g.vertices.entrySet()) {
//             var k = e.getKey();
//             var v = e.getValue();
//             if (v.order.size() == 2) { // if edge only going to one vertix
//                 optimisable.add(k);
//             }
//             for (var i : v.order) { // map where a vertix appear
//                 var list = where.getOrDefault(i, new ArrayList<Integer>());
//                 list.add(k);
//                 where.put(i, list);
//             }
//         }
//         for (var k : new HashSet<>(optimisable)) {
//             if (where.get(k).size() != 2) { // remove vertex with multiple parents
//                 optimisable.remove(k);
//             }
//         }
//         for (var k : new HashSet<>(where.keySet())) {
//             if (!optimisable.contains(k)) { // remove useless data on where map
//                 where.remove(k);
//             }
//         }
//         for (var mid : optimisable) {
//             int from = where.get(mid).get(0);
//             int to = where.get(mid).get(1);
//             if (!g.vertices.containsKey(from)) {
//                 continue;
//             }
//             if (!g.vertices.containsKey(to)) {
//                 continue;
//             }
//             if (!g.vertices.containsKey(mid)) {
//                 continue;
//             }
//             if (!g.vertices.get(from).neighbours.containsKey(mid)) {
//                 continue;
//             }
//             if (!g.vertices.get(mid).neighbours.containsKey(to)) {
//                 continue;
//             }
//             if (!g.vertices.get(to).neighbours.containsKey(mid)) {
//                 continue;
//             }
//             if (!g.vertices.get(mid).neighbours.containsKey(from)) {
//                 continue;
//             }
//             if (from == mid) {
//                 // removeEdge(g.vertices.get(from), mid);
//                 continue;
//             }
//             if (mid == to) {
//                 // removeEdge(g.vertices.get(mid), to);
//                 continue;
//             }
//             boolean ok = simplifyVertices(g, from, mid, to);
//             if (!ok) {
//                 System.out.println("1 NOT OK FOR" + from + " " + mid + " " + to);
//             }
//             ok = simplifyVertices(g, to, mid, from);
//             if (!ok) {
//                 // Is this ok or should it be to, mid, from?
//                 System.out.println("2 NOT OK FOR" + from + " " + mid + " " + to);
//             }
//             g.vertices.remove(mid);
//         }
//     }

//     private static boolean simplifyVertices(Graph g, int from, int mid, int to) {
//         if (g.vertices.get(from).neighbours.get(mid) == null) {
//             return false;
//         }
//         if (g.vertices.get(mid).neighbours.get(to) == null) {
//             return false;
//         }
//         int cost1 = g.vertices.get(from).neighbours.get(mid);
//         int cost2 = g.vertices.get(mid).neighbours.get(to);
//         int cost = cost1 + cost2;
//         removeEdge(g.vertices.get(from), mid);
//         g.vertices.get(from).addEdge(to, cost);
//         return true;
//     }
// }
