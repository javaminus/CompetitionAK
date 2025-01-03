class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int maxS = Integer.MIN_VALUE;
        int minS = 0;
        int maxF = 0, minF = 0, sum = 0;
        for (int x : nums) {
            maxF = Math.max(maxF, 0) + x;
            maxS = Math.max(maxS, maxF);
            minF = Math.min(minF, 0) + x;
            minS = Math.min(minS, minF);
            sum += x;
        }
        return sum == minS ? maxS : Math.max(maxS, sum - minS);
    }
}