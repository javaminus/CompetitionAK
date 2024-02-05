import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int countNumbersWithUniqueDigits(int n) {
        s = Integer.toString((int) Math.pow(10, n) - 1).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false) + 1;
    }

    private int dfs(int i,int mask, boolean isLimit ,boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int ans = 0;
        if (!isNum) {
            ans += dfs(i + 1, mask, false, false);
        }
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = isNum ? 0 : 1; d <= upper; d++) {
            if ((mask >> d & 1) == 0) {
                ans += dfs(i + 1, mask | 1 << d, isLimit && d == upper, true);
            }
        }
        if (!isLimit && isNum) {
            memo[i][mask] = ans;
        }
        return ans;
    }
}