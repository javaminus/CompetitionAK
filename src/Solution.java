import java.util.Arrays;

class Solution {
    public int minDifficulty(int[] nums, int k) {
        int n = nums.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                // f[i][j] = max(f[i][j - 1], nums[j])
                if (i == j) {
                    f[i][j] = nums[j];
                }else{
                    f[i][j] = Math.max(f[i][j - 1], nums[j]);
                }
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + f[x][i - 1]);
                }
            }
        }
        return dp[n][k] == Integer.MAX_VALUE / 2 ? -1 : dp[n][k];
    }
}