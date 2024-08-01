import java.util.Arrays;

class Solution {
    private static int INF = Integer.MAX_VALUE / 2;
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int mask = 1 << n;
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int s = 1; s < mask; s++) {
            int c = Integer.bitCount(s);
            for (int i = 0; i < n; i++) {
                if (((s << i) & 1) == 0) {
                    continue;
                }
                dp[s] = Math.min(dp[s], dp[(1 << i) ^ s] + (nums1[c - 1] + nums2[i]));
            }
        }
        return dp[mask - 1];
    }
}