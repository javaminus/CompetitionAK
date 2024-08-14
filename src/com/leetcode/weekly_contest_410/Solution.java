package com.leetcode.weekly_contest_410;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2024/8/11 12:32
 */
public class Solution {
    // https://leetcode.cn/problems/count-the-number-of-good-nodes/ T2
    List<Integer>[] g;
    int[] tmp;
    int[] parent;
    public int countGoodNodes(int[][] edges) {
        int n = edges.length + 1;
        g = new List[n];
        parent = new int[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        tmp = new int[n];
        dfs(0, -1);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int pre = -1;
            boolean flag = true;
            for (int y : g[i]) {
                if (y == parent[i]) {
                    continue;
                }
                if (pre == -1) {
                    pre = tmp[y];
                } else if (pre != tmp[y]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans++;
            }
        }
        return ans;
    }
    private void dfs(int x, int fa) {
        parent[x] = fa;
        tmp[x]++;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x);
                tmp[x] += tmp[y];
            }
        }
    }

    // https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/description/ T3、T4 一样
    private static int Mod = (int) 1e9 + 7;
    public int countOfPairs(int[] nums) {
        int n = nums.length;
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        // f(i)(j) = sum(f(i - 1)(j'))，其中j' <= min(j, j + nums(i - 1) - nums(i))
        long[][] dp = new long[n][mx + 1], g = new long[n][mx + 1];
        for (int i = 0; i <= nums[0]; i++) {
            dp[0][i] = 1;
        }
        g[0][0] = dp[0][0];
        for (int i = 1; i <= mx; i++) {
            g[0][i] = (g[0][i - 1] + dp[0][i]) % Mod;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= nums[i]; j++) {
                int lim = Math.min(j, j + nums[i - 1] - nums[i]);
                if (lim >= 0) {
                    dp[i][j] = g[i - 1][lim];
                }
            }
            // 计算前缀和
            g[i][0] = dp[i][0];
            for (int j = 1; j <= mx; j++) {
                g[i][j] = (g[i][j - 1] + dp[i][j]) % Mod;
            }
        }
        return (int) g[n - 1][mx];
    }
}
