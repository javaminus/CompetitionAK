package com.Java_Template.dp.dp_package;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2024/3/17 15:52
 */
public class problemImpl implements problem {

    /**
     * 和为目标值的最长子序列的长度 0-1背包问题（选与不选）
     */
    @Override
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size();
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int num = nums.get(i - 1);
            // 选择num对dp的影响
            for (int j = target; j >= num; j--) { // 这里j>=num,因为当背包容量j小于num时肯定装不了num
                if (dp[j - num] < 0) { // 也就是dp[j - num] = -1的时候，表示背包没有剩余容量为j - num的时候
                    continue;
                }
                dp[j] = Math.max(dp[j], dp[j - num] + 1);
            }
        }
        return dp[target];
    }

    /**
     * 416. 分割等和子集
     */
    @Override
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1) {
            return false;
        }
        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        int n = nums.length;
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = sum; j >= num; j--) {
                if (dp[j - num]) {
                    dp[j] = true;
                }
            }
        }
        return dp[sum];
    }


    /**
     * 零钱兑换 （完全背包问题）
     */
    @Override
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) { // 能放进银币coin
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE / 2 ? -1 : dp[amount];
    }

    // 2585. 获得分数的方法数
    int Mod = (int) 1e9 + 7;
    public int waysToReachTarget(int target, int[][] types) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int[] type : types) {
            int count = type[0], marks = type[1];
            for (int i = target; i >= 0; i--) { // 背包容量
                for (int j = 1; j <= count && j * marks <= i; j++) {
                    dp[i] = (dp[i] + dp[i - j * marks]) % Mod;
                }
            }
        }
        return dp[target];
    }

    /**
     * 494. 目标和
     */
    @Override
    public int findTargetSumWays(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        if ((sum - target) % 2 == 1 || sum - target < 0) {
            return 0;
        }
        target = (sum - target) / 2;
        int n = nums.length;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1; // 表示方案数为1，即一个负数都不选
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = target; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[target];
    }

    // 518. 零钱兑换 II
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        /*
         交换循环顺序会报错，因为如果背包的容量在外层，那么对于amount = 5,coins = {1,2,5}有 1，2，2是一组，2，1，2是一组，这样就会统计重复的答案
         for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] += dp[i - coin];
                }
            }
        }*/
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }


}
