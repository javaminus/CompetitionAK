package com.Java_Template.dp.math_dp;

/**
 * 第二类斯特林数
 * 题解：https://leetcode.cn/problems/find-the-number-of-possible-ways-for-an-event/solutions/2948578/zu-he-shu-xue-di-er-lei-si-te-lin-shu-py-e6sv/
 */
public class lc_3317 { // 这里就学一个东西，将i个人划分成 j 个非空集合的方案数（这 j 个集合没有顺序）。递推式如何写？
    private static final int Mod = (int) 1e9 + 7;
    private static final long[][] dp = new long[1001][1001];
    static { // 定义 dp(i,j) 表示把 i 个人划分成 j 个非空集合的方案数（这 j 个集合没有顺序）。
        dp[0][0] = 1;
        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + (long) j * dp[i - 1][j]) % Mod; // 第二项是j*, 这里与第一类斯特林数不同
            }
        }
    }

    public int numberOfWays(int n, int x, int y) {
        long ans = 0, perm = 1, powY = 1;
        for (int i = 1; i <= Math.min(n, x); i++) {
            perm = perm * (x + 1 - i) % Mod;
            powY = powY * y % Mod;
            ans = (ans + perm * dp[n][i] % Mod * powY) % Mod;
        }
        return (int) ans;
    }
}
