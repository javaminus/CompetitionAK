package com.template.图;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2023/12/5 16:18
 *
 * https://leetcode.cn/problems/minimum-fuel-cost-to-report-to-the-capital/description/
 */
public class 到达首都的最少油耗 {
    private long ans;
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length;
        List<Integer>[] grid = new ArrayList[n + 1]; // 总共有n+1个节点
        Arrays.setAll(grid, e -> new ArrayList<>());
        for (int[] road : roads) {
            int x = road[0], y = road[1];
            grid[x].add(y);
            grid[y].add(x);
        }
        dfs(grid, -1, 0, seats);
        return ans;
    }

    private int dfs(List<Integer>[] grid, int fa, int cur, int seats) {
        int size = 1;
        for (int x : grid[cur]) {
            if (x != fa) {
                size += dfs(grid, cur, x, seats);
            }
        }
        if (cur > 0) {
            ans += (size - 1) / seats + 1;
        }
        return size;
    }

}
