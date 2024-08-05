package com.leetcode.biweekly_contest_136;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // https://leetcode.cn/problems/time-taken-to-mark-all-nodes/description/ 换根dp
    List<Integer>[] g;
    public int[] timeTaken(int[][] edges) {
        // 先计算以0为根节点，计算树的最大深度
        int n = edges.length + 1;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        // nodes[x] 保存子树 x 的最大深度 maxD，次大深度 maxD2，以及最大深度要往儿子 my 走
        int[][] nodes = new int[n][3];
        dfs(0, -1, nodes);
        int[] ans = new int[n];
        reRoot(0, -1, 0, nodes, ans);
        return ans;
    }

    private int dfs(int x, int fa, int[][] nodes) {
        int maxD = 0;
        int maxD2 = 0;
        int my = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            int depth = dfs(y, x, nodes) + 2 - y % 2;
            if (depth > maxD) {
                maxD2 = maxD;
                maxD = depth;
                my = y;
            } else if (depth > maxD2) {
                maxD2 = depth;
            }
        }
        nodes[x][0] = maxD;
        nodes[x][1] = maxD2;
        nodes[x][2] = my;
        return maxD;
    }

    private void reRoot(int x, int fa, int fromUp, int[][] nodes, int[] ans) {
        int maxD = nodes[x][0];
        int maxD2 = nodes[x][1];
        int my = nodes[x][2];
        ans[x] = Math.max(fromUp, maxD);
        for (int y : g[x]) {
            if (y != fa) {
                reRoot(y, x, Math.max(fromUp, (y == my ? maxD2 : maxD)) + 2 - x % 2, nodes, ans);
            }
        }
    }
}
