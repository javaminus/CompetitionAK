package com.Java_Template.dp.dp_package;

import java.util.List;

/**
 * 背包问题
 *
 *
 * 0-1背包问题
 * 2915. 和为目标值的最长子序列的长度(https://leetcode.cn/problems/length-of-the-longest-subsequence-that-sums-to-target/)
 * 416. 分割等和子集(https://leetcode.cn/problems/partition-equal-subset-sum/description/)
 * 494. 目标和(https://leetcode.cn/problems/target-sum/description/)
 * 2585. 获得分数的方法数(https://leetcode.cn/problems/number-of-ways-to-earn-points/description/)
 *
 * 完全背包问题
 * 322. 零钱兑换(https://leetcode.cn/problems/coin-change/)
 * 518. 零钱兑换 II(https://leetcode.cn/problems/coin-change-ii/description/)
 *
 *
 *
 */
public interface problem {

    // 2915. 和为目标值的最长子序列的长度
    public int lengthOfLongestSubsequence(List<Integer> nums, int target);

    // 416. 分割等和子集
    public boolean canPartition(int[] nums);

    // 322. 零钱兑换
    public int coinChange(int[] coins, int amount);

    // 2585. 获得分数的方法数
    public int waysToReachTarget(int target, int[][] types);

    // 494. 目标和
    public int findTargetSumWays(int[] nums, int target);

    // 518. 零钱兑换 II
    public int change(int amount, int[] coins);
}
