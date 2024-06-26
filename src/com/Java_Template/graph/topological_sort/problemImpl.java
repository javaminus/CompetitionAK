package com.Java_Template.graph.topological_sort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class problemImpl implements problem {
    // 310. 最小高度树
    @Override
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (n == 1) {
            ans.add(0);
            return ans;
        }
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        int[] degree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
            degree[x]++;
            degree[y]++;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }
        int maxNodeNumber = n;
        while (maxNodeNumber > 2) {
            int size = queue.size();
            maxNodeNumber -= size;
            for (int i = 0; i < size; i++) {
                int x = queue.poll();
                for (int y : g[x]) {
                    degree[y]--;
                    if (degree[y] == 1) {
                        queue.offer(y);
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            ans.add(queue.poll());
        }
        return ans;
    }

    // 2192. 有向无环图中一个节点的所有祖先
    public List<List<Integer>> getAncestors(int n, int[][] edges){
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[y].add(x); // 逆序dfs
        }
        List[] ans = new List[n];
        Arrays.setAll(ans, e -> new ArrayList<Integer>());
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited, false);
            dfs(g, visited, i);
            visited[i] = false;
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    ans[i].add(j);
                }
            }
        }
        return Arrays.asList(ans);
    }

    private void dfs(List<Integer>[] g, boolean[] visited, int x) {
        visited[x] = true;
        for (int y : g[x]) {
            if (!visited[y]) {
                dfs(g, visited, y);
            }
        }
    }

}
