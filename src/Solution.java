import java.util.Arrays;

class Solution {
    private char[] s, t;
    private int[][] memo;

    public int minDistance(String text1, String text2) {
        s = text1.toCharArray();
        t = text2.toCharArray();
        int n = s.length, m = t.length;
        memo = new int[n][m];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示还没有计算过
        return dfs(n - 1, m - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0) return j + 1;
        if (j < 0) return i + 1;
        if (memo[i][j] != -1) return memo[i][j]; // 之前算过了
        if (s[i] == t[j]) return memo[i][j] = dfs(i - 1, j - 1);
        return memo[i][j] = Math.min(Math.min(dfs(i - 1, j), dfs(i, j - 1)), dfs(i - 1, j - 1)) + 1;
    }
}
