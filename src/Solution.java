import java.util.Arrays;

class Solution {
    public int maxValue(int[][] events, int k) {
        int n = events.length;
        Arrays.sort(events, (a, b) -> a[1] - b[1]); // 按照结束时间排序
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            int index = search(events, events[i - 1][0], i);
            for (int j = 1; j <= k; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[index + 1][j - 1] + events[i - 1][2]);
            }
        }
        return dp[n][k];
    }

    // 返回 endTime < target 的最大下标
    private int search(int[][] events, int target, int right) {
        int left = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events[mid][1] < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }
}