package com.leetcode.biweekly_contest_136;

/**
 * @author Minus
 * @date 2024/8/4 0:53
 */
class Solution {
    // https://leetcode.cn/problems/minimum-number-of-flips-to-make-binary-grid-palindromic-ii/
    public int minFlips(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;
        // 情况一：处理有四个对应位置的格子
        for (int i = 0, ii = m - 1; i < ii; i++, ii--) {
            for (int j = 0, jj = n - 1; j < jj; j++, jj--) {
                int sum = grid[i][j] + grid[i][jj] + grid[ii][j] + grid[ii][jj];
                ans += Math.min(sum, 4 - sum);
            }
        }
        // 情况二：处理在对称轴上的格子
        int cnt1 = 0, diff = 0;
        if ((m & 1) == 1) {
            int i = m / 2;
            for (int j = 0, jj = n - 1; j < jj; j++, jj--) {
                if (grid[i][j] == grid[i][jj] && grid[i][j] == 1) {
                    cnt1 += 2;
                } else if (grid[i][j] != grid[i][jj]) {
                    diff++;
                }
            }
        }
        if ((n & 1) == 1) {
            int j = n / 2;
            for (int i = 0, ii = m - 1; i < ii; i++, ii--) {
                if (grid[i][j] == grid[ii][j] && grid[i][j] == 1) {
                    cnt1 += 2;
                } else if (grid[i][j] != grid[ii][j]) {
                    diff++;
                }
            }
        }
        ans += diff;
        System.out.println(ans);
        // 如果没有对称不同的格子，1 的数量又不被 4 整除，那只能花两次操作，把两个 1 都变成 0
        if (cnt1 % 4 == 2 && diff == 0) {
            ans += 2;
        }
        // 情况三：处理矩阵中心的格子
        if (m % 2 == 1 && n % 2 == 1 && grid[m / 2][n / 2] == 1) {
            ans++;
        }
        return ans;
    }
}
