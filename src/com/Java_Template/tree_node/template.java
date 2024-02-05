package com.Java_Template.tree_node;

import java.util.*;

/**
 * @author Minus
 * @date 2024/1/15 21:26
 */
class TreeNode{
    int val;
    TreeNode left,right;
    public TreeNode(){}
    public TreeNode(int val){
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};

class TreeOrder{
    // 前序bfs 迭代
    public List<Integer> preorderTraversalBFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                res.add(node.val);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }
        return res;
    }

    // 前序 递归
    public List<Integer> preorderTraversalDFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        preorder(root, res);
        return res;
    }
    public void preorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        preorder(root.left, res);
        preorder(root.right, res);
    }

    // 中序 bfs
    public List<Integer> inorderTraversalBFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stk = new LinkedList<TreeNode>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    // 中序
    public List<Integer> inorderTraversalDFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }

    public void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }


    // bfs 后序
    public static List<Integer> postOrderBFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }

    // 后序
    public List<Integer> postOrderDFS(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        postorder(root, res);
        return res;
    }

    public void postorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        postorder(root.left, res);
        postorder(root.right, res);
        res.add(root.val);
    }

    // 层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }

        return ret;
    }


    // 垂序遍历
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // 存储遍历结果的临时数组，每个元素为 [行号, 列号, 值]
        ArrayList<int[]> data = new ArrayList<>();
        // 执行深度优先搜索遍历二叉树
        dfs(root, 0, 0, data);

        // 根据列号、行号、值的顺序进行排序
        data.sort((a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] != b[0] ? a[0] - b[0] : a[2] - b[2]);

        // 用于存储最终结果的列表
        ArrayList<List<Integer>> ans = new ArrayList<>();
        // 上一列的值，初始为负无穷小
        int lastCol = Integer.MIN_VALUE;

        // 遍历排序后的结果，按列将值添加到结果列表中
        for (int[] d : data) {
            if (d[1] != lastCol) {
                // 如果列号不同，表示进入新的一列，创建一个新的列表
                lastCol = d[1];
                ans.add(new ArrayList<>());
            }
            // 将当前值添加到最后一个列表中
            ans.get(ans.size() - 1).add(d[2]);
        }

        return ans;
    }

    // 深度优先搜索遍历二叉树，同时记录行号、列号和值
    private void dfs(TreeNode root, int row, int col, List<int[]> data) {
        if (root == null) {
            return;
        }
        // 将当前节点的行号、列号和值添加到临时数组中
        data.add(new int[]{row, col, root.val});
        // 递归遍历左子树，行号加1，列号减1
        dfs(root.left, row + 1, col - 1, data);
        // 递归遍历右子树，行号加1，列号加1
        dfs(root.right, row + 1, col + 1, data);
    }

}

