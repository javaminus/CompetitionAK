package com.template.二叉树;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @author Minus
 * @date 2023/12/15 10:47
 */
public class 反转二叉树的奇数层 {
    // bfs
    public TreeNode reverseOddLevels(TreeNode root) {
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean isOdd = false;
        while (!q.isEmpty()) {
            ArrayList<TreeNode> arr = new ArrayList<>();
            int n = q.size();
            for (int i = 0; i < n; i++) {
                TreeNode node = q.poll();
                if (isOdd) {
                    arr.add(node);
                }
                if (node.left != null) {
                    q.offer(node.left);
                    q.offer(node.right);
                }
            }
            // 交换孩子节点
            if (isOdd) {
                for (int l = 0, r = n - 1; l < r; l++, r--) {
                    int temp = arr.get(l).val;
                    arr.get(l).val = arr.get(r).val;
                    arr.get(r).val = temp;
                }
            }
            isOdd ^= true;
        }
        return root;
    }
}
