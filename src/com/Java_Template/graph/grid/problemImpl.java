package com.Java_Template.graph.grid;

/**
 * @author Minus
 * @date 2024/3/16 15:17
 */
public class problemImpl implements problem {

    // 2684. 矩阵中移动的最大次数
    private int ans;
    public int maxMoves(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            dfs(grid, i, 0);
        }
        return ans;
    }

    private void dfs(int[][] grid, int i, int j) {
        ans = Math.max(ans, j);
        if (ans == grid[0].length - 1) {
            return;
        }
        for (int k = Math.max(i - 1, 0); k < Math.min(i + 2, grid.length); k++) {
            if (grid[k][j + 1] > grid[i][j]) {
                dfs(grid, k, j + 1);
            }
        }
        grid[i][j] = 0;
    }
}
