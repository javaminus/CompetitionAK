package com.Java_Template.dp.dp_package;

import java.util.List;

/**
 * 背包问题
 * <p>
 * <p>
 * 0-1背包问题
 * 2915. 和为目标值的最长子序列的长度(https://leetcode.cn/problems/length-of-the-longest-subsequence-that-sums-to-target/)
 * 416. 分割等和子集(https://leetcode.cn/problems/partition-equal-subset-sum/description/)
 * 494. 目标和(https://leetcode.cn/problems/target-sum/description/)
 * 2585. 获得分数的方法数(https://leetcode.cn/problems/number-of-ways-to-earn-points/description/)
 * 474. 一和零(https://leetcode.cn/problems/ones-and-zeroes/description/)
 * 1155. 掷骰子等于目标和的方法数(https://leetcode.cn/problems/number-of-dice-rolls-with-target-sum/description/) 组合背包OR多重背包，差分
 * 1049. 最后一块石头的重量 II(https://leetcode.cn/problems/last-stone-weight-ii/description/)
 * <p>
 * 完全背包问题
 * 322. 零钱兑换(https://leetcode.cn/problems/coin-change/)
 * 518. 零钱兑换 II(https://leetcode.cn/problems/coin-change-ii/description/)
 * 1449. 数位成本和为目标值的最大数字(https://leetcode.cn/problems/form-largest-integer-with-digits-that-add-up-to-target/description/)
 * 879. 盈利计划(https://leetcode.cn/problems/profitable-schemes/description/)
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

    // 474. 一和零
    public int findMaxForm(String[] strs, int m, int n);

    // 1155. 掷骰子等于目标和的方法数
    public int numRollsToTarget(int n, int k, int target);

    // 1049. 最后一块石头的重量 II
    public int lastStoneWeightII(int[] stones);

    // 494. 目标和
    public int findTargetSumWays(int[] nums, int target);

    // 518. 零钱兑换 II
    public int change(int amount, int[] coins);

    // 1449. 数位成本和为目标值的最大数字
    public String largestNumber(int[] cost, int target);

    // 879. 盈利计划
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit);

}
