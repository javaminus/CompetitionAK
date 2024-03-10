class Solution {
    public boolean checkArray(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[n];
        diff[0] = nums[0];
        for (int i = 1; i < n; i++) {
            diff[i] = nums[i] - nums[i - 1];
        }
        for (int i = 0; i < n - k; i++) {
            if (diff[i] > 0) {
                diff[i + k] += diff[i];
                diff[i] = 0;
            } else if (diff[i] < 0) {
                return false;
            }
        }
        for (int i = n - k; i < n; i++) {
            if (diff[i] != 0) {
                return false;
            }
        }
        return true;
    }
}