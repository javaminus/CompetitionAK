import java.lang.reflect.Array;
import java.util.Arrays;

class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Integer[] ids = new Integer[n];
        Arrays.setAll(ids, i -> i);
        Arrays.sort(ids, (i, j) -> endTime[i] - endTime[j]);
        int[] dp = new int[n + 1]; // 表示前i个工作能获得的最大报酬
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            for (int j = 0; j < n; j++) {
                if (startTime[ids[i - 1]] >= endTime[ids[j]]) {
                    dp[i] = Math.max(dp[i], dp[ids[j]] + profit[ids[j]]);
                }
            }
        }
        return dp[n];
    }
}