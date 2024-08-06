class Solution {
    public int numberOfStableArrays(int zero, int one, int limit) {
        int Mod = (int) 1e9 + 7;
        // 定义dp[i][j][k]表示有i个0,j个1的方案数目，其中第(i+j)个数是k
        long[][][] dp = new long[zero + 1][one + 1][2];
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            dp[i][0][0] = 1;
        }
        for (int j = 1; j <= Math.min(one, limit); j++) {
            dp[0][j][1] = 1;
        }
        for (int i = 1; i <= zero; i++) {
            for (int j = 1; j <= one; j++) {
                dp[i][j][0] = (int) (dp[i - 1][j][0] + dp[i - 1][j][1] + ( (i > limit ? Mod -dp[i - limit - 1][j][1] : 0))) % Mod;
                dp[i][j][1] = (int) (dp[i][j - 1][0] + dp[i][j - 1][1] + ((j > limit ? Mod - dp[i][j - limit - 1][0] : 0))) % Mod;
            }
        }
        return (int) ((dp[zero][one][0] + dp[zero][one][1]) % Mod);
    }
}