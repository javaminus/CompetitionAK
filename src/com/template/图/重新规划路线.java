package com.template.图;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minus
 * @date 2023/12/7 10:53
 */
public class 重新规划路线 {
    public int minReorder(int n, int[][] connections) {
        List<int[]>[]  grid = new List[n];
        for (int i = 0; i < n; i++) {
            grid[i] = new ArrayList<int[]>();
        }
        for (int[] edge : connections) {
            grid[edge[0]].add(new int[]{edge[1], 1});
            grid[edge[1]].add(new int[]{edge[0], 0});
        }
        return dfs(grid, -1, 0);
    }

    private int dfs(List<int[]>[] grid, int parent, int x) {
        int ans = 0;
        for (int[] cur : grid[x]) {
            if (cur[0] != parent) {
                ans += cur[1] + dfs(grid, x, cur[0]);
            }
        }
        return ans;
    }
}
