package chneau.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * High-performance Dijkstra shortest path algorithm for Java 26+.
 */
public final class Dijkstra {

    public static class Result {
        public int distance;
        public List<Integer> path = new ArrayList<>();

        public int getDistance() {
            return distance;
        }

        public List<Integer> getPath() {
            return Collections.unmodifiableList(path);
        }

        @Override
        public String toString() {
            return "{distance=" + distance + ", path=" + path + "}";
        }
    }

    public static class Info extends Result implements Comparable<Info> {
        public int id;

        public Info(int id, List<Integer> path, int distance) {
            this.id = id;
            this.path = new ArrayList<>(path);
            this.distance = distance;
        }

        @Override
        public int compareTo(Info o) {
            int cmp = Integer.compare(this.distance, o.distance);
            if (cmp == 0) {
                return Integer.compare(this.id, o.id);
            }
            return cmp;
        }
    }

    public static Result shortest(Graph g, int from, int to) {
        Objects.requireNonNull(g, "graph must not be null");
        if (!g.vertices.containsKey(from) || !g.vertices.containsKey(to)) {
            return null;
        }

        var distances = new HashMap<Integer, Info>();
        var startInfo = new Info(from, List.of(from), 0);
        distances.put(from, startInfo);

        var pq = new PriorityQueue<Info>();
        pq.add(startInfo);

        var visited = new HashSet<Integer>();

        while (!pq.isEmpty()) {
            Info current = pq.poll();
            int u = current.id;

            if (u == to) {
                return current;
            }

            if (!visited.add(u)) {
                continue;
            }

            Vertex vertex = g.vertices.get(u);
            if (vertex == null) {
                continue;
            }

            for (int neighbor : vertex.order) {
                if (visited.contains(neighbor)) {
                    continue;
                }

                Integer edgeCost = vertex.neighbours.get(neighbor);
                if (edgeCost == null) {
                    continue;
                }

                int newDist = current.distance + edgeCost;
                Info existing = distances.get(neighbor);

                if (existing == null || newDist < existing.distance) {
                    var newPath = new ArrayList<Integer>(current.path.size() + 1);
                    newPath.addAll(current.path);
                    newPath.add(neighbor);

                    Info nextInfo = new Info(neighbor, newPath, newDist);
                    distances.put(neighbor, nextInfo);
                    pq.add(nextInfo);
                }
            }
        }

        return distances.get(to);
    }
}

