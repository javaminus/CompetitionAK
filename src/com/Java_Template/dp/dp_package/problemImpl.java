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


    // 474. 一和零
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1]; // 表示在前len个字符串中选最大不超过m和n的最长子集
        for (int i = 1; i <= len; i++) {
            String str = strs[i - 1];
            int[] t = f(str);
            int zero = t[0], one = t[1];
            for (int j = 0; j <=m ; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = dp[i - 1][j][k]; // 不选当前字符串
                    if (zero <= j && one <= k) {
                        // dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j - zero][k - one] + 1); 这样就会报错
                        // 其实是dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zero][k - one] + 1),如果要选当前值，那么也是dp[i - 1]+1呀
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - zero][k - one] + 1);
                    }
                }
            }
        }
        return dp[len][m][n];
    }

    // 滚动数组优化
    public int findMaxForm1(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] dp = new int[m + 1][n + 1]; // 表示在前len个字符串中选最大不超过m和n的最长子集
        for (int i = 1; i <= len; i++) {
            String str = strs[i - 1];
            int[] t = f(str);
            int zero = t[0], one = t[1];
            for (int j = m; j >= zero; j--) { // 倒叙遍历，因为是01背包
                for (int k = n; k >= one; k--) {
                    dp[j][k] = Math.max(dp[j][k], dp[j - zero][k - one] + 1);
                }
            }
        }
        return dp[m][n];
    }
    private int[] f(String s) {
        int[] res = new int[2];
        for (int i = 0; i < s.length(); i++) {
            res[s.charAt(i) - '0']++;
        }
        return res;
    }

    // 1155. 掷骰子等于目标和的方法数
    @Override
    public int numRollsToTarget(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0;
        }
        int[][] dp = new int[n + 1][target + 1 - n]; // dp[i][j]表示前i个骰子和为 target+1-n 的数量  差分
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = target - n; j >= 0; j--) {
                for (int x = 0; x < k && j - x >= 0; x++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - x]) % Mod;
                }
            }
        }
        return dp[n][target - n];
    }


    // 1049. 最后一块石头的重量 II
    @Override
    public int lastStoneWeightII(int[] stones) {
        // 转换成背包问题真的不好想
        int sum1 = Arrays.stream(stones).sum();
        int sum = sum1 / 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        // 01背包
        for (int stone : stones) {
            for (int i = sum; i >= stone; i--) {
                if (dp[i - stone]) {
                    dp[i] = true;
                }
            }
        }
        for (int i = sum; i >= 0; i--) {
            if (dp[i]) {
                return sum1 - sum * 2;
            }
        }
        return 0;
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
        /* 这个也会报错，这里主要是因为可以重复选择的原因，这样写就是没有利用重复的元素，如果要重复，那么就要从小到大
         * for (int coin : coins) {
            for (int i = amount; i >= coin; i++) {
                dp[i] += dp[i - coin];
            }
        }
         */
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }


    // 1449. 数位成本和为目标值的最大数字
    @Override
    public String largestNumber(int[] cost, int target) {
        /**
         * 完全背包问题
         */
        int[][] dp = new int[10][target + 1]; // dp[i][j]表示在前i个数，最多可以选几个数，也就是最长数位
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        dp[0][0] = 0; // 选0个数的数位长度为0
        for (int i = 1; i <=9 ; i++) {
            for (int j = 1; j <= target; j++) { // 正序，因为是完全背包，可以选重复的元素
                if (cost[i - 1] > j) {
                    // dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]); 这里这么写都不行！！！
                    dp[i][j] = dp[i - 1][j];
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - cost[i - 1]] + 1);
                }
            }
        }
        if (dp[9][target] < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int digit = 9, remain = target;
        while (digit > 0) {
            if (remain >= cost[digit - 1] && dp[digit][remain] == dp[digit][remain - cost[digit - 1]] + 1) {
                // 选择了当前数字
                sb.append(digit);
                remain -= cost[digit - 1];
            }else{
                digit--;
            }
        }
        return sb.toString();
    }
    // 滚动数组优化空间
    public String largestNumber1(int[] cost, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= 9; i++) {
            for (int j = cost[i - 1]; j <= target; j++) {
                dp[j] = Math.max(dp[j], dp[j - cost[i - 1]] + 1);
            }
        }
        if (dp[target] < 0) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        int digit = 9, remain = target;
        while (digit > 0) {
            if (remain >= cost[digit - 1] && dp[remain] == dp[remain - cost[digit - 1]] + 1) {
                remain -= cost[digit - 1];
                sb.append(digit);
            }else{
                digit--;
            }
        }
        return sb.toString();
    }

    // 879. 盈利计划
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        // 其中 dp[i][j][k]表示前 i 种工作的子集当中成员总数至多为 j 且总利润至少为 k 的计划数。
        int m = group.length;
        int[][][] dp = new int[m + 1][n + 1][minProfit + 1];
        for (int i = 0; i <= n; i++) {
            dp[0][i][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= minProfit; k++) {
                    if (j < group[i - 1]) {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }else{
                        dp[i][j][k] = (dp[i - 1][j][k] + dp[i - 1][j - group[i - 1]][Math.max(k - profit[i - 1], 0)]) % Mod;
                    }
                }
            }
        }
        return dp[m][n][minProfit];
    }
    // 滚动数组优化
    public int profitableSchemes1(int n, int minProfit, int[] group, int[] profit) {
        int m = group.length;
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int j = 0; j <= n; j++) {
            dp[j][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= group[i - 1]; j--) { // 倒叙
                for (int k = minProfit; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - group[i - 1]][Math.max(k - profit[i - 1], 0)]) % Mod;
                }
            }
        }
        return dp[n][minProfit];
    }
}
