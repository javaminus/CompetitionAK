import java.util.Arrays;

class Solution {
    public int minimumSubstringsInPartition(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.setAll(dp, i -> i);
        for (int i = 1; i <= n; i++) {
            int[] cnt = new int[26];
            int mxFreq = 0, cntN = 0;
            for (int j = i - 1; j >= 0; j--) {
                int k = s.charAt(j) - 'a';
                cnt[k]++;
                if (cnt[k] == 1) {
                    cntN++;
                }
                mxFreq = Math.max(mxFreq, cnt[k]);
                if (mxFreq * cntN == i - j) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n];
    }
}