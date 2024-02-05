package com.Java_Template.dp.tree_dp;

/**
 * 树形dp
 * 834. 树中距离之和（https://leetcode.cn/problems/sum-of-distances-in-tree/description/） 换根dp
 * 2581. 统计可能的树根数目(https://leetcode.cn/problems/count-number-of-possible-root-nodes/description/?envType=daily-question&envId=2024-02-29) 换根dp
 *
 */
public interface problem{
    // 834. 树中距离之和
    public int[] sumOfDistancesInTree(int n, int[][] edges);

    // 2581. 统计可能的树根数目
    public int rootCount(int[][] edges, int[][] guesses, int k);
}
