class AVLNode {
    int key;
    AVLNode left, right;
    int height = 1;

    AVLNode(int key) {
        this.key = key;
    }
}

class AVL {

    static int height(AVLNode n) {
        return (n == null) ? 0 : n.height;
    }

    static int getBalance(AVLNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static void updateHeight(AVLNode n) {
        if (n != null)
            n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    // RIGHT ROTATION
    static AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // LEFT ROTATION
    static AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // INSERT
    static AVLNode insert(AVLNode node, int key) {
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        updateHeight(node);

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);

        // RR
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // DELETE
    static AVLNode delete(AVLNode root, int key) {

        if (root == null) return null;

        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);
        else {

            if (root.left == null || root.right == null) {
                AVLNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null)
                    return null;
                else
                    root = temp;
            } else {
                AVLNode temp = minValue(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        updateHeight(root);

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    static AVLNode minValue(AVLNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // INORDER
    static void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {

        int[] arr = {20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85, 90};

        AVLNode root = null;

        // Insert
        for (int x : arr)
            root = insert(root, x);

        System.out.print("AVL Inorder before deletion: ");
        inorder(root);

        // Delete 30, 70, 50
        root = delete(root, 30);
        root = delete(root, 70);
        root = delete(root, 50);

        System.out.print("\nAVL Inorder after deletion: ");
        inorder(root);
    }
}