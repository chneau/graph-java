package chneau.graph;

import java.util.HashMap;
import java.util.HashSet;

public class Simplify {

    public void removeEdge(Vertex v, int edge) {
        v.neighbours.remove(edge);
        for (int i = 0; i < v.order.size(); i++) {
            if (v.order.get(i) == edge) {
                //Not sure about how to translate this
                // v.order = append(v.Order[:i], v.Order[i+1:]...)
                break;
            }
        }
    }

    public void simplifyGraph(Graph g) {
        int l = g.vertices.size();
        while (true) {
            simplify(g);
            if (l == g.vertices.size()) {
                break;
            }
            l = g.vertices.size();
        }
        while (true) {
            biSimplify(g);
            if (l == g.vertices.size()) {
                break;
            }
            l = g.vertices.size();
        }
    }

    private void simplify(Graph g) {
        var optimisable = new HashSet<Integer>();
        // Helppp, what is the equivalent to this in Java?
        // where = map[int][]int{};
        var where = new HashMap<Integer, Integer>();
        for (int k = 0; k < g.vertices.size(); k++) {
            //Not sure about this ----> for k, v := range g
            for (Vertex v : g.vertices) {
                if (v.order.size() == 1) { // if edge only going to one vertix
                    optimisable.add(k);
                }
                for (int i = 0; i < v.order.size(); i++) { // map where a vertix appear
                    where.put(i, k);
                }
            }
        }
        for (int k = 0; k < optimisable.size(); k++) {
            if (where.get(k).size() != 1) { // remove vertix with multiple parents
                optimisable.remove(k);
            }
        }
        for (int k = 0; k < where.size(); k++) {
            if (!optimisable.contains(k)) { // remove useless data on where map
                where.remove(k);
            }
        }
        int mid = 0;
        while (mid < optimisable.size()) {
            //SOS, I want the key of where[mid]
            int from = where.get(mid);
            if (mid == from) { // a round has been reduced
                // removeEdge(g.vertices.get(from), mid)
                continue;
            }
            if (!g.vertices.containsKey(from)) {
                continue;
            }
            int to = g.vertices.get(mid).order.get(0);
            if (mid == to) {
                continue;
            }
            if (from == to) { // don't remove end of bi directional path
                continue;
            }
            boolean ok = simplifyVertices(g, from, mid, to);
            if (!ok) {
                System.out.println("NOT OK FOR" + from + " " + mid + " " + to);
            }
            g.vertices.remove(mid);
            mid++;
        }
    }

    private void biSimplify(Graph g) {
        var optimisable = new HashSet<Integer>();
        // Helppp, what is the equivalent to this in Java?
        // where = map[int][]int{};
        var where = new HashMap<Integer, Integer>();
        for (int k = 0; k < g.vertices.size(); k++) {
            //Not sure about this ----> for k, v := range g
            for (Vertex v : g.vertices) {
                if (v.order.size() == 2) { // if edge only going to one vertix
                    optimisable.add(k);
                }
                for (int i = 0; i < v.order.size(); i++) { // map where a vertix appear
                    where.put(i, k);
                }
            }
        }
        for (int k = 0; k < optimisable.size(); k++) {
            if (where.get(k).size() != 2) { // remove vertex with multiple parents
                optimisable.remove(k);
            }
        }
        for (int k = 0; k < where.size(); k++) {
            if (!optimisable.contains(k)) { // remove useless data on where map
                where.remove(k);
            }
        }
        int mid;
        for (mid = 0; mid < optimisable.size(); mid++) {
            //SOS, I want the key of where[mid] and then the value of where[mid]
            int from = where.get(mid);
            int to = where.get(mid);

            if (!g.vertices.containsKey(from)) {
                continue;
            }
            if (!g.vertices.containsKey(to)) {
                continue;
            }
            if (!g.vertices.containsKey(mid)) {
                continue;
            }
            if (!g.vertices.get(from).neighbours.containsKey(mid)) {
                continue;
            }
            if (!g.vertices.get(mid).neighbours.containsKey(to)) {
                continue;
            }
            if (!g.vertices.get(to).neighbours.containsKey(mid)) {
                continue;
            }
            if (!g.vertices.get(mid).neighbours.containsKey(from)) {
                continue;
            }
            if (from == mid) {
                // removeEdge(g.vertices.get(from), mid);
                continue;
            }
            if (mid == to) {
                // removeEdge(g.vertices.get(mid), to);
                continue;
            }
            boolean ok = simplifyVertices(g, from, mid, to);
            if (!ok) {
                System.out.println("1 NOT OK FOR" + from + " " + mid + " " + to);
            }
            ok = simplifyVertices(g, to, mid, from);
            if (!ok) {
                // Is this ok or should it be to, mid, from?
                System.out.println("2 NOT OK FOR" + from + " " + mid + " " + to);
            }
            g.vertices.remove(mid);
        }
    }

    private boolean simplifyVertices(Graph g, int from, int mid, int to) {

        if (g.vertices.get(from).neighbours.get(mid) == null) {
            return false;
        }
        if (g.vertices.get(mid).neighbours.get(to) == null) {
            return false;
        }
        int cost1 = g.vertices.get(from).neighbours.get(mid);
        int cost2 = g.vertices.get(mid).neighbours.get(to);
        int cost = cost1 + cost2;
        removeEdge(g.vertices.get(from), mid);
        g.vertices.get(from).addEdge(to, cost);
        return true;
    }
}
