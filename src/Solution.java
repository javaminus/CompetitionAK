import java.util.ArrayList;

class Solution {
    public int minimumOperations(String s) {
        int n = s.length();
        int[][] dp = new int[n][3];
        dp[0][0] = s.charAt(0) == 'y' ? 1 : 0;
        dp[0][1] = dp[0][2] = dp[1][2] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int isRed = s.charAt(i) == 'r' ? 1 : 0;
            int isYellow = s.charAt(i) == 'y' ? 1 : 0;
            dp[i][0] = dp[i - 1][0] + isYellow;
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + isRed;
            if (i > 1) {
                dp[i][2] = Math.min(dp[i - 1][1], dp[i - 1][2]) + isYellow;
            }
        }
        return dp[n - 1][2];
    }
}