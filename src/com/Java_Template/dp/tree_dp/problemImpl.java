package com.Java_Template.dp.tree_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class problemImpl implements problem {

    // 834. 树中距离之和
    List<Integer>[] g;
    int[] size; // 当前节点的子树大小，算上自己
    int[] ans;
    @Override
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        size = new int[n];
        ans = new int[n];
        dfs(0, -1, 0);
        reRoot(0, -1);
        return ans;
    }

    private void dfs(int x, int fa, int depth) {
        ans[0] += depth;
        size[x] = 1;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, depth + 1);
                size[x] += size[y];
            }
        }
    }


    private void reRoot(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                ans[y] = ans[x] + g.length - 2 * size[y];
                reRoot(y, x);
            }
        }
    }


    // 2581. 统计可能的树根数目
    int k;
    // List<Integer>[] g;
    HashSet<Long> set;
    int cnt0;
    int ans1;
    public int rootCount(int[][] edges, int[][] guesses, int k) {
        this.k = k;
        g = new List[edges.length + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        set = new HashSet<Long>();
        for (int[] guess : guesses) {
            int x = guess[0], y = guess[1];
            set.add(((long) x << 32 | y));
        }
        cnt0 = 0;
        ans1 = 0;
        dfs(0, -1);
        reRoot(0, -1, cnt0);
        return ans1;
    }

    private void dfs(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                if (set.contains((long) x << 32 | y)) {
                    cnt0++;
                }
                dfs(y, x);
            }
        }
    }

    private void reRoot(int x, int fa, int cnt) {
        if (cnt >= k) {
            ans1++;
        }
        for (int y : g[x]) {
            if (y != fa) {
                int c = cnt;
                if (set.contains((long) x << 32 | y)) {
                    c--;
                }
                if (set.contains((long) y << 32 | x)) {
                    c++;
                }
                reRoot(y, x, c);
            }
        }
    }

}
