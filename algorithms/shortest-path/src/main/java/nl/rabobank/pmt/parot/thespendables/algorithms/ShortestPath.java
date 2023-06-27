package nl.rabobank.pmt.parot.thespendables.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    public List<String> resolve(InputStream is) throws IOException {
        try (var reader = new InputStreamReader(is)) {
            return resolve(reader);
        }
    }

    public List<String> resolve(Reader reader) throws IOException {
        try (var br = new BufferedReader(reader)) {
            var start = Integer.parseInt(br.readLine());
            var end = Integer.parseInt(br.readLine());
            var vertex = readGraph(br, start);
            return bfs(vertex, end);
        }
    }

    private List<String> bfs(Vertex start, int end) {
        var queue = new LinkedList<Vertex>();
        queue.add(start);
        while (!queue.isEmpty()) {
            var v = queue.pop();
            if (v.value == end) {
                return followParent(v);
            }
            v.edges.forEach(e -> {
                // if parent is set, then it has been visited
                if (e.end.parent == null) {
                    e.end.setParent(e.start);
                    queue.add(e.end);
                }
            });
        }
        throw new IllegalStateException("The value not found");
    }

    // following the parent to get the list of vertices
    private List<String> followParent(Vertex end) {
        var result = new ArrayList<String>();
        var v = end;
        while (v != null) {
            result.add(Integer.toString(v.value));
            v = v.parent;
        }
        Collections.reverse(result);
        return result;
    }

    private Vertex readGraph(BufferedReader br, int start) {
        var store = new HashMap<Integer, Vertex>();
        return br.lines().map(s -> s.split("\\s+"))
                .map(points -> toVertex(points, store))
                .toList() // first we need to construct everything
                .stream()
                .filter(v -> v.value == start)
                .findFirst()
                .orElseThrow();
    }

    private Vertex toVertex(String[] points, HashMap<Integer, Vertex> store) {
        var start = Integer.valueOf(points[0]);
        var end = Integer.valueOf(points[1]);
        var vs = store.computeIfAbsent(start, Vertex::new);
        var es = store.computeIfAbsent(end, Vertex::new);
        vs.addEdge(new Edge(vs, es));
        return vs;
    }

    private static class Edge {
        private final Vertex start;
        private final Vertex end;

        private Edge(Vertex start, Vertex end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start.value +
                    ", end=" + end.value +
                    '}';
        }
    }

    private static class Vertex {
        private final int value;
        private final List<Edge> edges;
        private Vertex parent = null; // for convenience

        private Vertex(int value) {
            this.value = value;
            this.edges = new ArrayList<>();
        }
        public void addEdge(Edge e) {
            this.edges.add(e);
        }
        public void setParent(Vertex p) {
            this.parent = p;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "value=" + value +
                    ", edges=" + edges +
                    '}';
        }
    }
}
