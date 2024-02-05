package com.Java_Template.deque;

/**
 * 双端队列
 *
 * 239. 滑动窗口最大值(https://leetcode.cn/problems/sliding-window-maximum/description/) 典中典 单调队列
 * 1696. 跳跃游戏 VI（https://leetcode.cn/problems/jump-game-vi/description/?envType=daily-question&envId=2024-02-05） 动态规划+单调队列
 * 862. 和至少为 K 的最短子数组(https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/description/)
 * 1425. 带限制的子序列和(https://leetcode.cn/problems/constrained-subsequence-sum/description/) 动态规划+单调队列
 */
public interface problem {
    // 239. 滑动窗口最大值
    public int[] maxSlidingWindow(int[] nums, int k);

    // 1696. 跳跃游戏 VI
    public int maxResult(int[] nums, int k);

    // 862. 和至少为 K 的最短子数组
    public int shortestSubarray(int[] nums, int k);

    // 1425. 带限制的子序列和
    public int constrainedSubsetSum(int[] nums, int k);
}
