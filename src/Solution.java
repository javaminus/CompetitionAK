import java.util.Arrays;

class Solution {
    private static final int Mod = (int) 1e9 + 7;
    public int numberOfArrays(String s, int k) {
        int n = s.length();
        if (s.charAt(0) == '0') {
            return 0;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            long num = 0, base = 1;
            for (int j = i; j >= 0 && i - j <= 10; j--) {
                num += (s.charAt(j) - '0') * base;
                if (num > k) {
                    break;
                }
                if (s.charAt(j) != '0') {
                    dp[i + 1] += dp[j];
                    dp[i + 1] %= Mod;
                }
                base *= 10;
            }
        }
        return (int) dp[n];
    }
}