class RankNode {
    int key, height, size;
    RankNode left, right;

    RankNode(int key) {
        this.key = key;
        this.height = 1;
        this.size = 1;
    }
}

class AVLRankTree {

    int height(RankNode n) {
        return n == null ? 0 : n.height;
    }

    int size(RankNode n) {
        return n == null ? 0 : n.size;
    }

    void update(RankNode n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.size = 1 + size(n.left) + size(n.right);
        }
    }

    int getBalance(RankNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // Right Rotate
    RankNode rightRotate(RankNode y) {
        RankNode x = y.left;
        RankNode T2 = x.right;

        x.right = y;
        y.left = T2;

        update(y);
        update(x);
        return x;
    }

    // Left Rotate
    RankNode leftRotate(RankNode x) {
        RankNode y = x.right;
        RankNode T2 = y.left;

        y.left = x;
        x.right = T2;

        update(x);
        update(y);
        return y;
    }

    // Insert (descending order)
    RankNode insert(RankNode root, int key) {
        if (root == null) return new RankNode(key);

        if (key > root.key) root.left = insert(root.left, key);
        else root.right = insert(root.right, key);

        update(root);

        int balance = getBalance(root);

        // LL
        if (balance > 1 && key > root.left.key)
            return rightRotate(root);

        // RR
        if (balance < -1 && key < root.right.key)
            return leftRotate(root);

        // LR
        if (balance > 1 && key < root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL
        if (balance < -1 && key > root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Find Min
    RankNode minValueNode(RankNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Delete
    RankNode delete(RankNode root, int key) {
        if (root == null) return null;

        if (key > root.key) root.left = delete(root.left, key);
        else if (key < root.key) root.right = delete(root.right, key);
        else {
            if (root.left == null || root.right == null) {
                root = (root.left != null) ? root.left : root.right;
            } else {
                RankNode temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null) return null;

        update(root);

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Rank Function
    int rankOf(RankNode root, int key) {
        int rank = 0;

        while (root != null) {
            if (key > root.key) {
                root = root.left;
            } else if (key < root.key) {
                rank += size(root.left) + 1;
                root = root.right;
            } else {
                rank += size(root.left) + 1;
                return rank;
            }
        }
        return -1;
    }

    // Inorder Traversal
    void inorder(RankNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        AVLRankTree tree = new AVLRankTree();
        RankNode root = null;

        int[] values = {820, 540, 910, 770, 880, 460, 990, 600, 730, 950, 510};

        // Insert
        for (int v : values) {
            root = tree.insert(root, v);
        }

        // Before updates
        System.out.print("AVL Inorder before updates: ");
        tree.inorder(root);
        System.out.println();

        // Updates
        root = tree.delete(root, 540);
        root = tree.insert(root, 815);

        root = tree.delete(root, 910);
        root = tree.insert(root, 685);

        // After updates
        System.out.print("AVL Inorder after updates: ");
        tree.inorder(root);
        System.out.println();

        // Rank
        int rank = tree.rankOf(root, 770);
        System.out.println("Rank of 770 = " + rank);
    }
}