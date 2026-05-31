import java.util.*;

public class BFSBoundedDepth {

    static class Node {
        String user;
        int depth;

        Node(String user, int depth) {
            this.user = user;
            this.depth = depth;
        }
    }

    static class TraceResult {
        LinkedHashSet<String> reached;
        Map<String, String> parent;

        TraceResult(LinkedHashSet<String> reached, Map<String, String> parent) {
            this.reached = reached;
            this.parent = parent;
        }
    }

    static TraceResult bfsBounded(Map<String, List<String>> adj,
                                  String source,
                                  int maxDepth) {

        Set<String> visited = new HashSet<>();
        LinkedHashSet<String> reached = new LinkedHashSet<>();
        Map<String, String> parent = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();

        visited.add(source);
        queue.add(new Node(source, 0));

        while (!queue.isEmpty()) {

            Node cur = queue.poll();
            String u = cur.user;
            int depth = cur.depth;

            if (depth == maxDepth) continue;

            List<String> neighbors = adj.getOrDefault(u, new ArrayList<>());
            Collections.sort(neighbors);

            for (String v : neighbors) {

                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(new Node(v, depth + 1));

                    reached.add(v);
                    parent.put(v, u);
                }
            }
        }

        return new TraceResult(reached, parent);
    }

    // reconstruct path A -> ... -> X
    static String getPath(Map<String, String> parent, String node) {
        List<String> path = new ArrayList<>();

        while (node != null) {
            path.add(node);
            node = parent.get(node);
        }

        Collections.reverse(path);
        return String.join(" -> ", path);
    }

    public static void main(String[] args) {

        Map<String, List<String>> adj = new HashMap<>();

        adj.put("A", Arrays.asList("B", "C"));
        adj.put("B", Arrays.asList("D", "E"));
        adj.put("C", Arrays.asList("E", "F"));
        adj.put("D", Arrays.asList("G"));
        adj.put("E", Arrays.asList("G", "H"));
        adj.put("F", Arrays.asList("H"));
        adj.put("G", Arrays.asList("I"));
        adj.put("H", Arrays.asList("I"));
        adj.put("I", new ArrayList<>());

        TraceResult result = bfsBounded(adj, "A", 3);

        System.out.println("set=" + result.reached.toString().replace(" ", ""));
        System.out.println("Total Reached=" + result.reached.size());

        System.out.println("\nPaths of edges that are having Multiple in-edges :");

        for (String node : Arrays.asList("E", "G", "H")) {
            System.out.println(node + " : " + getPath(result.parent, node));
        }
    }
}