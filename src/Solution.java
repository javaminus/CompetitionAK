import java.util.Arrays;

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = m; i > 0; i--) {
            for (int j = n; j > 0; j--) {
                dp[i][j] = Math.min(Math.max(dp[i - 1][j], dp[j][i - 1]) + dungeon[i - 1][j - 1], 0);
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return -dp[0][0] + 1;
    }
}