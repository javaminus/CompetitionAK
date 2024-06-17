import java.util.Arrays;

class Solution {
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        int[][] dp = new int[maxTime + 1][n];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = passingFees[0]; //初始化  对动态规划的状态定义dp[i][j] i是花费的时间 j是所到达的城市
        int ans = Integer.MAX_VALUE / 2;
        for (int i = 1; i <= maxTime; i++) {
            for (int j = 0; j < edges.length; j++) {
                int[] edge = edges[j];
                int x = edge[0], y = edge[1], time = edge[2];
                if (time <= i) {
                    dp[i][y] = Math.min(dp[i][y], dp[i - time][x] + passingFees[y]);
                    dp[i][x] = Math.min(dp[i][x], dp[i - time][y] + passingFees[x]);
                }
            }
            ans = Math.min(ans, dp[i][n - 1]);
        }
        return ans == Integer.MAX_VALUE / 2 ? -1 : ans;
    }
}