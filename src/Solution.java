class Solution {
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int n = nums.length;
        int i0 = -1, i1 = -1;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] > right) {
                i0 = i;
            }
            if (nums[i] >= left) {
                i1 = i;
            }
            ans += i1 - i0;
        }
        return ans;
    }
}