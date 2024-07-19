import java.util.List;

class Solution {
    public int maxScore(List<List<Integer>> grid) {
        int m = grid.size(), n = grid.get(0).size();
        int[][] dp = new int[m + 1][n + 1];
        int ans = Integer.MIN_VALUE;
        int t = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //for (int k = 1; k < Math.max(m, n) && k <= Math.max(i, j); k++) {
                int k = 1;
                if (i - k >= 0) {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i + 1 - k][j + 1] + grid.get(i).get(j) - grid.get(i - k).get(j));
                    t = Math.max(t, grid.get(i).get(j) - grid.get(i - k).get(j));
                }
                if (j - k >= 0) {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i + 1][j + 1 - k] + grid.get(i).get(j) - grid.get(i).get(j - k));
                    t = Math.max(t, grid.get(i).get(j) - grid.get(i).get(j - k));
                }
                ans = Math.max(ans, dp[i + 1][j + 1]);
            }
        }
        if (t < 0) {
            return t;
        }
        return ans;
    }
}