package com.template.动态规划;

/**
 * @author Minus
 * @date 2023/12/1 22:48
 */
public class 完全平方数 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int mmin = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                mmin = Math.min(mmin, dp[i - j * j]);
            }
            dp[i] = mmin + 1;
        }
        return dp[n];

    }
}
