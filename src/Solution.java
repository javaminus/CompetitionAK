import java.util.Arrays;

class Solution {
    private static final int Mod = (int) (1e9 + 7);

    public int numWays(String[] words, String target) {
        int m = words[0].length(), n = target.length();
        int[][] dp = new int[n + 1][m + 1]; // dp[i][j]表示从前j个字母选择构成target[:i]的方案数
        Arrays.fill(dp[0], 1); // 构成空字串的方案数，都是1
        int[][] cnt = new int[m + 1][26]; // cnt[i][j]表示前i个字符构成字符（j+'a'）的方案数
        for (String s : words) {
            for (int i = 0; i < m; i++) {
                cnt[i + 1][s.charAt(i) - 'a']++;
            }
        }
        for (int i = 1; i <= n; i++) {
            int c = target.charAt(i - 1) - 'a';
            int t = m - (n - i);
            for (int j = i; j <= t; j++) {
                long ans = (long) cnt[j][c] * dp[i - 1][j - 1] + dp[i][j - 1];
                dp[i][j] = (int) (ans % Mod);
            }
        }
        return dp[n][m];
    }
}