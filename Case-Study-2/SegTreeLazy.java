class SegTreeLazy {
    double[] tree;   // max value
    double[] lazy;   // pending updates
    int n;

    SegTreeLazy(int n) {
        this.n = n;
        tree = new double[4 * n];
        lazy = new double[4 * n];
        build(1, 0, n - 1);
    }

    // Build initial tree (all values = 1.0)
    void build(int node, int lo, int hi) {
        if (lo == hi) {
            tree[node] = 1.0;
            return;
        }
        int mid = (lo + hi) / 2;
        build(2 * node, lo, mid);
        build(2 * node + 1, mid + 1, hi);
        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }

    void pushDown(int node) {
        if (lazy[node] != 0) {
            tree[2 * node] += lazy[node];
            lazy[2 * node] += lazy[node];

            tree[2 * node + 1] += lazy[node];
            lazy[2 * node + 1] += lazy[node];

            lazy[node] = 0;
        }
    }

    // Range Update
    void updateRange(int node, int lo, int hi, int l, int r, double delta) {
        // No overlap
        if (hi < l || lo > r) return;

        // Full overlap
        if (l <= lo && hi <= r) {
            tree[node] += delta;
            lazy[node] += delta;
            return;
        }

        // Partial overlap
        pushDown(node);
        int mid = (lo + hi) / 2;

        updateRange(2 * node, lo, mid, l, r, delta);
        updateRange(2 * node + 1, mid + 1, hi, l, r, delta);

        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }

    // Range Query (Max)
    double queryMax(int node, int lo, int hi, int l, int r) {
        // No overlap
        if (hi < l || lo > r) return Double.MIN_VALUE;

        // Full overlap
        if (l <= lo && hi <= r) return tree[node];

        // Partial overlap
        pushDown(node);
        int mid = (lo + hi) / 2;

        return Math.max(
            queryMax(2 * node, lo, mid, l, r),
            queryMax(2 * node + 1, mid + 1, hi, l, r)
        );
    }

    // Driver
    public static void main(String[] args) {
        SegTreeLazy st = new SegTreeLazy(16);

        // Operations
        st.updateRange(1, 0, 15, 3, 9, 0.5);
        st.updateRange(1, 0, 15, 7, 14, 0.3);

        System.out.println("Max [0,15]: " +
            st.queryMax(1, 0, 15, 0, 15));

        st.updateRange(1, 0, 15, 2, 6, 0.7);

        System.out.println("Max [4,10]: " +
            st.queryMax(1, 0, 15, 4, 10));
    }
}