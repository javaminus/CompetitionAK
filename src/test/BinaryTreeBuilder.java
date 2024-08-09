package test;

import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class BinaryTreeBuilder {
    public static TreeNode buildTree(int[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode currentNode = queue.poll();

            if (levelOrder[i] != -1) {
                currentNode.left = new TreeNode(levelOrder[i]);
                queue.offer(currentNode.left);
            }
            i++;

            if (i < levelOrder.length && levelOrder[i] != -1) {
                currentNode.right = new TreeNode(levelOrder[i]);
                queue.offer(currentNode.right);
            }
            i++;
        }

        return root;
    }

    public static void main(String[] args) {
        int[] levelOrder = {3, 9, 20, -1, -1, 15, 7};
        TreeNode root = buildTree(levelOrder);
        // 这里可以添加代码来验证树的结构，例如打印树的层序遍历结果
    }
}
