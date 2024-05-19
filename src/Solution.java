import java.util.Arrays;

class Solution {
    int[][] memo;
    public int countVowelStrings(int n) {
        memo = new int[n + 1][5];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, n);
    }

    private int dfs(int i, int j, int n) {
        if (i == n) {
            return 1;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        for (int k = j; k <= 4; k++) {
            res += dfs(i + 1, k, n);
        }
        return memo[i][j] = res;
    }
}