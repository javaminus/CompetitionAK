package com.Java_Template.dp.interval_dp;

import java.util.Arrays;
import java.util.List;

/**
 * 区间dp[i][j]，i就是数组的左边，j就是数组右边
 */
public class problemImpl implements problem {
    private int[] nums;
    private int[][] memo;
    @Override
    public int maxOperations(int[] nums) {
        int n = nums.length;
        this.nums = nums;
        memo = new int[n][n];
        int res1 = helper(2, n - 1, nums[0] + nums[1]); // 最前面两个
        int res2 = helper(0, n - 3, nums[n - 2] + nums[n - 1]); // 最后两个
        int res3 = helper(1, n - 2, nums[0] + nums[n - 1]); // 第一个和最后一个
        return Math.max(Math.max(res1, res2), res3) + 1; // 加上第一次操作
    }
    private int helper(int i, int j, int target) {
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(i, j, target);
    }
    private int dfs(int i, int j, int target) {
        if (i >= j) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        if (nums[i] + nums[i + 1] == target) {
            res = Math.max(res, dfs(i + 2, j, target) + 1);
        }
        if (nums[j - 1] + nums[j] == target) {
            res = Math.max(res, dfs(i, j - 2, target) + 1);
        }
        if (nums[i] + nums[j] == target) {
            res = Math.max(res, dfs(i + 1, j - 1, target) + 1);
        }
        return memo[i][j] = res;
    }

    @Override
    public boolean wordBreak(String s, List<String> wordDict) {
        // HashSet<String> set = new HashSet<>(wordDict); 可以使用set去重,以及set的查找
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
