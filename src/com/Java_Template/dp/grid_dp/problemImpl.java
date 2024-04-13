package com.Java_Template.dp.grid_dp;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2024/4/13 14:41
 *
 * 网格dp
 */
public class problemImpl {
    /* 1289. 下降路径最小和 II

       给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
       非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列 */
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j == k) {
                        continue;
                    }
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + grid[i][j]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }

    /* 1594. 矩阵的最大非负积
        给你一个大小为 m x n 的矩阵 grid 。最初，你位于左上角 (0, 0) ，每一步，你可以在矩阵中 向右 或 向下 移动。
        在从左上角 (0, 0) 开始到右下角 (m - 1, n - 1) 结束的所有路径中，找出具有 最大非负积 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。
        返回 最大非负积 对 109 + 7 取余 的结果。如果最大积为 负数 ，则返回 -1 。
        注意，取余是在得到最大积之后执行的。 */
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length, Mod = (int) 1e9 + 7;
        long[][] dpMax = new long[m][n];
        long[][] dpMin = new long[m][n];
        dpMax[0][0] = grid[0][0];
        dpMin[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dpMax[i][0] = dpMax[i - 1][0] * grid[i][0];
            dpMin[i][0] = dpMin[i - 1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dpMax[0][i] = dpMax[0][i - 1] * grid[0][i];
            dpMin[0][i] = dpMin[0][i - 1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] > 0) {
                    dpMax[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] < 0) {
                    dpMax[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] == 0) {
                    dpMax[i][j] = 0;
                    dpMin[i][j] = 0;
                }
            }
        }
        return (int) (Math.max(dpMax[m - 1][n - 1], -1) % Mod);
    }


    /*  1301. 最大得分的路径数目

        给你一个正方形字符数组 board ，你从数组最右下方的字符 'S' 出发。

        你的目标是到达数组最左上角的字符 'E' ，数组剩余的部分为数字字符 1, 2, ..., 9 或者障碍 'X'。在每一步移动中，你可以向上、向左或者左上方移动，可以移动的前提是到达的格子没有障碍。

        一条路径的 「得分」 定义为：路径上所有数字的和。

        请你返回一个列表，包含两个整数：第一个整数是 「得分」 的最大值，第二个整数是得到最大得分的方案数，请把结果对 10^9 + 7 取余。

        如果没有任何路径可以到达终点，请返回 [0, 0] 。*/
    public int[] pathsWithMaxScore(List<String> board) {
        int Mod = (int) 1e9 + 7;
        int n = board.size();
        int[][][] dp = new int[n + 1][n + 1][2];
        dp[n][n][1] = 1; // 最开始就一种方法
        for (int i = n; i > 0; i--) {
            String s = board.get(i - 1);
            for (int j = n; j > 0; j--) {
                char c = s.charAt(j - 1);
                if (c != 'X' && (dp[i][j][1] > 0 || dp[i - 1][j][1] > 0 || dp[i][j - 1][1] > 0)) {
                    int maxScore = Math.max(dp[i][j][0], Math.max(dp[i - 1][j][0], dp[i][j - 1][0]));
                    dp[i - 1][j - 1][0] = maxScore + c - '0';
                    if (dp[i][j][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i][j][1] % Mod;
                    }
                    if (dp[i - 1][j][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i - 1][j][1] % Mod;
                    }
                    if (dp[i][j- 1][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i][j - 1][1] % Mod;
                    }
                }
            }
        }
        return new int[]{Math.max(0, dp[0][0][0] - ('E' + 'S' - '0' - '0')), dp[0][0][1] % Mod};
    }


    /*  题目：2435. 矩阵中和能被 K 整除的路径
        给你一个下标从 0 开始的 m x n 整数矩阵 grid 和一个整数 k 。你从起点 (0, 0) 出发，每一步只能往 下 或者往 右 ，你想要到达终点 (m - 1, n - 1) 。
        请你返回路径和能被 k 整除的路径数目，由于答案可能很大，返回答案对 109 + 7 取余 的结果。*/

    /*  超时
private int ans = 0;
public int numberOfPaths(int[][] grid, int k) {
    int Mod = (int) 1e9 + 7;
    int m = grid.length, n = grid[0].length;
    dfs(grid, 0, 0, 0, m, n, k);
    return ans;
}

private void dfs(int[][] grid, int sum, int i, int j, int m, int n, int k) {
    if (i == m || j == n) {
        return;
    }
    sum += grid[i][j];
    if (i == m - 1 && j == n - 1) {
        if (sum % k == 0) {
            ans++;
        }
        return;
    }
    dfs(grid, sum, i + 1, j, m, n, k);
    dfs(grid, sum, i, j + 1, m, n, k);
}*/
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length, Mod = (int) 1e9 + 7;
        /*套路：把路径和模 k 的结果当成一个扩展维度。
        具体地，定义 f[i][j][v] 表示从左上走到 (i,j)，且路径和模 k 的结果为 v 时的路径数。 */
        int[][][] dp = new int[m + 1][n + 1][k];
        dp[1][0][0] = 1; // dp[0][1][0] = 1也行
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < k; l++) {
                    dp[i + 1][j + 1][(l + grid[i][j]) % k] = (dp[i + 1][j][l] + dp[i][j + 1][l]) % Mod;
                }
            }
        }
        return dp[m][n][0];
    }


    /*174. 地下城游戏
        恶魔们抓住了公主并将她关在了地下城 dungeon 的 右下角 。地下城是由 m x n 个房间组成的二维网格。我们英勇的骑士最初被安置在 左上角 的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。

        骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。

        有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。

        为了尽快解救公主，骑士决定每次只 向右 或 向下 移动一步。

        返回确保骑士能够拯救到公主所需的最低初始健康点数。

        注意：任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间*/
    public int calculateMinimumHP(int[][] dungeon) {
        int n = dungeon.length, m = dungeon[0].length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = n - 1; i >= 0; --i) {
            for (int j = m - 1; j >= 0; --j) {
                int minn = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(minn - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }
}
