package com.songw;

/**
 * @author Minus
 * @date 2023/4/8 16:15
 */
public class LeetCode343 {

    public static void main(String[] args) {
        System.out.println(integerBreak(11));
    }

    public static int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i < n; i++) {
            for (int j = 2; i > j; j++) {
                dp[i] = Math.max(dp[i], Math.max(dp[i - j] * j,(i-j)*j));
            }
            System.out.println(dp[i]);
        }
        return dp[n-1];
    }
}
