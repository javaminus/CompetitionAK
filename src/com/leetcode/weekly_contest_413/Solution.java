package com.leetcode.weekly_contest_413;

import java.util.Arrays;
import java.util.List;

/**
 * dp的一集 rank 450(vp 2/4)
 * 学习蛙佬题解  https://leetcode.cn/circle/discuss/MLYP0V/
 * Q3 状压dp
 * Q4 区间dp
 *
 */
public class Solution {
    // https://leetcode.cn/problems/select-cells-in-grid-with-maximum-score/  状压dp
    public int maxScore(List<List<Integer>> grid) {
        int m = grid.size(), n = grid.get(0).size();
        int[][] g = new int[m][n];
        int mx = 0;
        for (int i = 0; i < m; i++) { // 个人喜欢操作数组
            for (int j = 0; j < n; j++) {
                g[i][j] = grid.get(i).get(j);
                mx = Math.max(mx, g[i][j]);
            }
        }
        int[] f = new int[mx + 1]; // f[i]表示元素i在哪几行的状态
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[g[i][j]] |= (1 << i);
            }
        }
        int[][] dp = new int[mx + 1][1 << m]; // dp[x][s]表示在考虑前x个数字，选择行的状态为s的最大结果
        for (int i = 0; i < mx + 1; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        }
        dp[0][0] = 0;
        int ans = Integer.MIN_VALUE / 2;
        for (int x = 1; x < mx + 1; x++) {
            for (int s = 0; s < 1 << m; s++) {
                dp[x][s] = Math.max(dp[x][s], dp[x - 1][s]); // 不放入x
                // 尝试选择第i行的x
                for (int i = 0; i < m; i++) {
                    if ((((f[x] >> i) & 1) == 1) && (((s >> i) & 1) == 1)) {
                        dp[x][s] = Math.max(dp[x][s], dp[x - 1][s ^ (1 << i)] + x);
                    }
                }
                ans = Math.max(ans, dp[x][s]);
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximum-xor-score-subarray-queries/description/   Q4 区间dp
    public int[] maximumSubarrayXor(int[] nums, int[][] queries) {
        int n = nums.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                f[i][j] = f[i + 1][j] ^ f[i][j - 1];
            }
        }
        int[][] g = new int[n][n]; // 区间最大值 f(l, r) = max(f(l, r), g(l, r - 1), g(l + 1, r))
        for (int i = n - 1; i >= 0; i--) {
            g[i][i] = f[i][i];
            for (int j = i + 1; j < n; j++) {
                g[i][j] = Math.max(f[i][j], Math.max(g[i][j - 1], g[i + 1][j]));
            }
        }
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int l = queries[i][0], r = queries[i][1];
            ans[i] = g[l][r];
        }
        return ans;
    }
}
