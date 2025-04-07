class Solution {
    public long maximumTripletValue(int[] nums) {
        int n = nums.length;
        int[] preMX = new int[n];
        int[] sufMX = new int[n];
        preMX[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preMX[i] = Math.max(preMX[i - 1], nums[i]);
        }
        sufMX[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            sufMX[i] = Math.max(sufMX[i + 1], nums[i]);
        }
        long ans = 0;
        for (int i = 1; i < n - 1; i++) {
            ans = Math.max(ans, (long) (preMX[i - 1] - nums[i]) * sufMX[i + 1]);
        }
        return ans;
    }
}