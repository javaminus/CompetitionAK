package test;

class Node {
    Node leftNode, rightNode;
    int val;
    Node(int val) {
        this.val = val;
    }
}

public class BinaryTreePrinter {
    public static void main(String[] args) {
        // 创建一个示例二叉树
        Node root = new Node(1);
        root.leftNode = new Node(2);
        root.rightNode = new Node(3);
        root.leftNode.leftNode = new Node(4);
        root.leftNode.rightNode = new Node(5);
        root.rightNode.leftNode = new Node(6);
        root.rightNode.rightNode = new Node(7);

        // 打印二叉树
        printTree(root, 0, "Root");
    }

    // 递归打印二叉树
    public static void printTree(Node node, int level, String label) {
        if (node == null) {
            return;
        }

        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(label + ": " + node.val);

        printTree(node.leftNode, level + 1, "L");
        printTree(node.rightNode, level + 1, "R");
    }
}