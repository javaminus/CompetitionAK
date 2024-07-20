class Solution {
    private int[][] dp;
    private int m, n, result = 0;
    public int countPyramids(int[][] grid) {
        // 跑两次倒三角
        m = grid.length;
        n = grid[0].length;
        dp = new int[m][n];
        f(grid);
        for (int i = 0; i < m >> 1; i++) {
            int[] t = grid[i];
            grid[i] = grid[m - i - 1];
            grid[m - i - 1] = t;
        }
        f(grid);
        return result;
    }

    private void f(int[][] grid) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = grid[i][j];
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j + 1])) + 1;
                    result += dp[i][j] - 1;
                }
            }
        }
    }
}