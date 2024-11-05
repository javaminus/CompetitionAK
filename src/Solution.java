import java.util.Arrays;

class Solution {
    public int[] resultsArray(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            int i0 = i;
            while (i + 1 < n && nums[i + 1] == 1 + nums[i]) {
                i++;
            }
            while (i0 + k - 1 <= i) {
                ans[i0] = nums[i0 + k - 1];
                i0++;
            }
        }
        return ans;
    }
}