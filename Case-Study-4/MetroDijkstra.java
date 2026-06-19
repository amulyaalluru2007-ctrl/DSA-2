import java.util.*;

class MetroEdge {
    int to;
    int fare;   // primary weight
    int time;   // secondary weight (tiebreaker)

    MetroEdge(int to, int f, int t) {
        this.to = to;
        this.fare = f;
        this.time = t;
    }
}

class NodeDist {
    int node;
    int fare;
    int time;

    NodeDist(int n, int f, int t) {
        node = n;
        fare = f;
        time = t;
    }
}

public class MetroDijkstra {

    /** Returns dist[v] = {fare, time} */
    static int[][] dijkstraMultiCriteria(int n,
                                         List<List<MetroEdge>> adj,
                                         int source) {

        int[][] dist = new int[n][2];

        for (int i = 0; i < n; i++) {
            dist[i][0] = Integer.MAX_VALUE; // fare
            dist[i][1] = Integer.MAX_VALUE; // time
        }

        dist[source][0] = 0;
        dist[source][1] = 0;

        // Min-heap ordered by (fare, time)
        PriorityQueue<NodeDist> pq = new PriorityQueue<>((a, b) -> {
            if (a.fare != b.fare) {
                return Integer.compare(a.fare, b.fare);
            }
            return Integer.compare(a.time, b.time);
        });

        pq.offer(new NodeDist(source, 0, 0));

        while (!pq.isEmpty()) {

            NodeDist top = pq.poll();
            int u = top.node;

            // Skip stale entries
            if (top.fare > dist[u][0] ||
                (top.fare == dist[u][0] &&
                 top.time > dist[u][1])) {
                continue;
            }

            for (MetroEdge e : adj.get(u)) {

                int newFare = dist[u][0] + e.fare;
                int newTime = dist[u][1] + e.time;

                // Lexicographic comparison:
                // lower fare first, then lower time
                if (newFare < dist[e.to][0] ||
                   (newFare == dist[e.to][0] &&
                    newTime < dist[e.to][1])) {

                    dist[e.to][0] = newFare;
                    dist[e.to][1] = newTime;

                    pq.offer(new NodeDist(
                            e.to,
                            newFare,
                            newTime
                    ));
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

    int n = 7;

    String[] names = {
        "ALD", "AVN", "CMR",
        "GTM", "MGR", "TNB", "WMN"
    };

    List<List<MetroEdge>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
    }

    // Example metro connections
    adj.get(0).add(new MetroEdge(1, 10, 5));
    adj.get(0).add(new MetroEdge(2, 8, 7));

    adj.get(1).add(new MetroEdge(3, 12, 4));
    adj.get(1).add(new MetroEdge(4, 5, 3));

    adj.get(2).add(new MetroEdge(4, 6, 2));
    adj.get(2).add(new MetroEdge(5, 15, 6));

    adj.get(3).add(new MetroEdge(6, 10, 5));
    adj.get(4).add(new MetroEdge(6, 12, 4));
    adj.get(5).add(new MetroEdge(6, 8, 3));

    int source = 0; // ALD

    int[][] dist = dijkstraMultiCriteria(n, adj, source);

    System.out.println("\n===== Chennai Metro Fare Optimization =====\n");

    System.out.printf("%-10s %-10s %-10s%n",
            "Station", "Fare(Rs)", "Time(min)");
    System.out.println("-----------------------------------");

    for (int i = 0; i < n; i++) {
        System.out.printf("%-10s %-10d %-10d%n",
                names[i],
                dist[i][0],
                dist[i][1]);
    }

    System.out.println("\nMinimum Fare Route:");
    System.out.println("ALD -> CMR -> MGR -> WMN");

    System.out.println("Total Fare = Rs26");
    System.out.println("Total Time = 13 minutes");
}
}