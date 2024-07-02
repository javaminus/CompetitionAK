import java.util.Arrays;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int left = 0, right = 0; // 滑窗左闭右闭
        int[] dp = new int[n]; // 使用dp(j)表示当前j以及j之前的满足条件的最小区间长度
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        int sum = 0, ans = Integer.MAX_VALUE / 2;
        while (right < n) {
            sum += arr[right];
            while (left <= right && sum > target) {
                sum -= arr[left++];
            }
            if (sum == target) {
                dp[right] = right - left + 1;
                if (left != 0) {
                    ans = Math.min(ans, dp[left - 1] + dp[right]);
                }
            }
            if (right != 0) {
                dp[right] = Math.min(dp[right], dp[right - 1]);
            }
            right++;
        }
        return ans == Integer.MAX_VALUE / 2 ? -1 : ans;
    }

}