package com.Java_Template.graph.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 没有环，就不需要visited数组
 *
 * 834. 树中距离之和：【求图的所有距离和】：给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边，给定整数 n 和数组 edges ， edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。
 */
public class template {
    int res;
    // 暴力写法
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e-> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            res = 0;
            dfs(g, i, -1, 0);
            ans[i] = res;
        }
        return ans;
    }
    private void dfs(List<Integer>[] g, int x, int fa, int depth) { // 这里要加入一个depth的参数
        res += depth;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(g, y, x, depth + 1);
            }
        }
    }

    // 换根dp
}
