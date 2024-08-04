package com.leetcode.weekly_contest_409;

import java.util.*;

/**
 * @author Minus
 * @date 2024/8/4 16:02
 * 力扣409场周赛 2/4 rank 1270 差点第二题都翻车
 */
public class Solution {
    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-i/  两种解法 bfs ,floyd
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) { // bfs解法
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int i = 0; i < n - 1; i++) {
            g[i].add(i + 1);
        }
        int m = queries.length;
        int[] ans = new int[m];
        Queue<Integer> queue = new LinkedList<>();
        int[] dist = new int[n];
        for (int i = 0; i < m; i++) {
            g[queries[i][0]].add(queries[i][1]);
            queue.offer(0);
            Arrays.fill(dist, -1); // bfs
            dist[0] = 0;
            while (!queue.isEmpty()) {
                Integer sn = queue.poll();
                for (int fn : g[sn]) {
                    if (dist[fn] == -1) {
                        dist[fn] = dist[sn] + 1;
                        queue.offer(fn);
                    }

                }
            }
            ans[i] = dist[n - 1];
        }
        return ans;
    }
    // 我的过题解法：floyd解法
    public int[] shortestDistanceAfterQueries1(int n, int[][] queries) {
        int m = queries.length;
        int[] ans = new int[m];
        int[][] w = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(w[i], Integer.MAX_VALUE / 2);
            w[i][i] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                w[i][j] = j - i;
            }
        }
        int[][] dp = w;
        for (int r = 0; r < m; r++) {
            int p = queries[r][0];
            int q = queries[r][1];
            for (int i = 0; i <= p; i++) {
                for (int j = q; j < n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][p] + dp[q][j] + 1);
                }
            }
            ans[r] = dp[0][n - 1];
        }
        return ans;


    }
    // 这里展示我的第一个超时的写法  为什么超时呢？这边不就是常规的bfs吗？因为少了 visited[poll] = true;
    public int[] shortestDistanceAfterQueriesTE(int n, int[][] queries) {
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int i = 0; i < n - 1; i++) {
            g[i].add(i + 1);
        }
        boolean[] visited = new boolean[n];
        int m = queries.length;
        int[] ans = new int[m];
        next:
        for (int i = 0; i < m; i++) {
            int[] q = queries[i];
            Arrays.fill(visited, false);
            g[q[0]].add(q[1]);
            ArrayDeque<Integer> deque = new ArrayDeque<>();
            deque.addLast(0);
            int step = 0;
            while (!deque.isEmpty()) {
                step++;
                int size = deque.size();
                for (int j = 0; j < size; j++) {
                    Integer poll = deque.pollFirst();
                    visited[poll] = true; // 少了这一行
                    for (int y : g[poll]) {
                        if (y == n - 1) {
                            ans[i] = step;
                            continue next;
                        }
                        if (!visited[y]) {
                            deque.addLast(y);
                        }
                    }
                }
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/shortest-distance-after-road-addition-queries-ii/description/
    // 审题呀，不存在两个查询 i 和 j 使得 queries[i][0] < queries[j][0] < queries[i][1] < queries[j][1]，这个条件是什么意思？
    // 其实这个条件是说，如果把一条道路 u → v 看成区间 [u, v)，那么所有区间要么互相包含，要么不相交，不存在相交但不包含的情况。
    // 包含直接就不用管。这是一个很好的性质，能够帮助我们通过贪心快速求出最短路。对于任意一条从 0 到 (n - 1) 的路径，如果里面存在多个小区间可以被一个大区间包含，那么直接把它们替换成这个大区间，答案会变得更好。
    // 因此我们可以用一个 set 维护当前的最短路包含哪些区间。加入一个区间后，如果它能够包含一些小区间，则从 set 里把这些小区间踢掉。答案就是这个 set 的大小。
    public int[] shortestDistanceAfterQueriesII(int n, int[][] queries) {
        TreeSet<int[]> intervals = new TreeSet<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < n - 1; i++) {
            intervals.add(new int[]{i, i + 1});
        }
        for (int i = 0; i < m; i++) {
            int u = queries[i][0], v = queries[i][1];
            int[] interval = new int[]{u, v};
            int[] prev = intervals.floor(interval); // floor 返回小于或等于给定键的最大键
            if (prev == null || prev[1] <= u) {
                int[] next;
                while ((next = intervals.ceiling(interval)) != null && next[1] <= v) { // ceil 返回大于或等于给定键的最小键
                    intervals.remove(next);
                }
                intervals.add(interval);
            }
            ans[i] = intervals.size();
        }
        return ans;
    }

    // 标准解法 并查集
    public int[] shortestDistanceAfterQueriesIII(int n, int[][] queries) {
        int[] parent = new int[n - 1];
        Arrays.setAll(parent, i -> i);
        int m = queries.length;
        int[] ans = new int[m];
        int cnt = n - 1;
        for (int i = 0; i < m; i++) {
            int u = queries[i][0], v = queries[i][1] - 1;
            int fv = find(parent, v);
            for (int j = find(parent, u); j < v; j = find(parent, j + 1)) {
                parent[j] = fv;
                cnt--;
            }
            ans[i] = cnt;
        }
        return ans;
    }
    private int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }

}
