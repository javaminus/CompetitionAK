package com.template.Tree_shaped_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2023/11/5 22:32
 */
public class S100118 {
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        ArrayList[] g = new ArrayList[edges.length];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[0].add(-1);
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        long res = 0;
        for (int value : values) {
            res += value;
        }

        return res - dfs(0, -1, g, values);
    }

    private long dfs(int x, int fa, List<Integer>[] g, int[] values) {
        if (g[x].size() == 1) {
            return values[x];
        }
        long loss = 0;
        for (int y : g[x]) {
            if (y != fa) {
                loss += dfs(y, x, g, values);
            }
        }
        return Math.min(values[x], loss);


    }
}
