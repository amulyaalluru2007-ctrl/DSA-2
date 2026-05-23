class BSTNode {
    int key;
    BSTNode left, right;

    BSTNode(int key) {
        this.key = key;
    }
}

class BST {

    // INSERT
    static BSTNode insert(BSTNode root, int key) {
        if (root == null) return new BSTNode(key);

        if (key < root.key)
            root.left = insert(root.left, key);
        else
            root.right = insert(root.right, key);

        return root;
    }

    // DELETE
    static BSTNode delete(BSTNode root, int key) {
        if (root == null) return null;

        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            BSTNode temp = minValue(root.right);
            root.key = temp.key;
            root.right = delete(root.right, temp.key);
        }
        return root;
    }

    static BSTNode minValue(BSTNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // INORDER (for output)
    static void inorder(BSTNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {

        int[] arr = {20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85, 90};

        BSTNode root = null;

        // Insert
        for (int x : arr)
            root = insert(root, x);

        System.out.print("BST Inorder before deletion: ");
        inorder(root);

        // Delete 30, 70, 50
        root = delete(root, 30);
        root = delete(root, 70);
        root = delete(root, 50);

        System.out.print("\nBST Inorder after deletion: ");
        inorder(root);
    }
}