package com.Java_Template.dp.tree_dp;

/**
 * 树形dp
 * 因为这一章是树形dp，所以所有题必须使用后序（自底向上）的思路解决！！！
 *
 * 前序遍历在某些数据下不需要递归到边界（base case）就能返回，而中序遍历和后序遍历至少要递归到一个边界，从这个角度上来说，前序遍历是最快的。
 * 中序遍历很好地利用了二叉搜索树的性质，使用到的变量最少。
 * 后序遍历的思想是最通用的，即自底向上计算子问题的过程。想要学好动态规划的话，请务必掌握这个思想。
 *
 *
 * 834. 树中距离之和（https://leetcode.cn/problems/sum-of-distances-in-tree/description/） 换根dp 2197
 * 2581. 统计可能的树根数目(https://leetcode.cn/problems/count-number-of-possible-root-nodes/description/?envType=daily-question&envId=2024-02-29) 换根dp
 * 1372.二叉树中的最长交错路径(https://leetcode.cn/problems/longest-zigzag-path-in-a-binary-tree/description/) 模板题
 * 124. 二叉树中的最大路径和(https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/) 好题 模板题
 * 98. 验证二叉搜索树(https://leetcode.cn/problems/validate-binary-search-tree/description/)
 * 1373. 二叉搜索子树的最大键值和(https://leetcode.cn/problems/maximum-sum-bst-in-binary-tree/description/)
 */
public interface problem {
    // 834. 树中距离之和
    public int[] sumOfDistancesInTree(int n, int[][] edges);

    // 2581. 统计可能的树根数目
    public int rootCount(int[][] edges, int[][] guesses, int k);

    // 1372. 二叉树中的最长交错路径
    public int longestZigZag(TreeNode root);

    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root);

    // 98. 验证二叉搜索树
    public boolean isValidBST(TreeNode root);

    // 1373. 二叉搜索子树的最大键值和
    public int maxSumBST(TreeNode root);
}
