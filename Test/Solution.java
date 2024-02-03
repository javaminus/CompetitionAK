import java.util.Arrays;

class Solution {
    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        int ans = 0, left = 0;
        for (int right = 0; right < n; right++) {
            while (distanceSum(prefix, nums, left, right, (left + right) / 2) > k) {
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    // 把 nums[l] 到 nums[r] 都变成 nums[i]的距离
    private long distanceSum(long[] prefix, int[] nums, int left, int right, int median) {
        long leftSum = (long) nums[median] * (median - left) - (prefix[median] - prefix[left]);
        long rightSum = prefix[right + 1] - prefix[median + 1] - (long) (right - median) * nums[median];
        return leftSum + rightSum;
    }
}