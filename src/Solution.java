import java.util.Arrays;
import java.util.List;

class Solution {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        // 和为目标值的最长子序列的长度
        int n = nums.size();
        int[][] dp = new int[n + 1][target + 1]; // dp[i][j]表示前i个数中选择和为j的最长长度
        Arrays.fill(dp[0], -1);
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            System.arraycopy(dp[i - 1], 0, dp[i], 0, target + 1);
            int num = nums.get(i - 1);
            for (int j = num; j <= target; j++) {
                if (dp[i - 1][j - num] < 0) {
                    continue;
                }
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - num] + 1);
            }
        }
        return dp[n][target];
    }
}