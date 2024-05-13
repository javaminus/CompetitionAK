import java.util.Arrays;

class Solution {
    char[] s;
    int[][][] memo;
    int k;
    int len;
    public int numberOfBeautifulIntegers(int low, int high, int k) {
        this.k = k;
        return cal(high) - cal(low - 1);
    }

    private int cal(int n) {
        s = Integer.toString(n).toCharArray();
        len = s.length;
        memo = new int[len][len * 2 + 1][k];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len * 2 + 1; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, len, 0, true, false);
    }

    private int dfs(int i, int diff, int mod, boolean isLimit, boolean isNum) { // diff 奇数 - 偶数
        if (i == len) {
            return isNum && mod == 0 && diff == len ? 1 : 0;
        }
        if (isNum && !isLimit && memo[i][diff][mod] != -1) {
            return memo[i][diff][mod];
        }
        int res = 0;
        if (!isNum) {
            res = dfs(i + 1, diff, mod, false, false);
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = isNum ? 0 : 1; j <= up; j++) {
            res += dfs(i + 1, diff + (j % 2 == 1 ? 1 : -1), (mod * 10 + j) % k, isLimit && j == up, true);
        }
        if (isNum && !isLimit) {
            memo[i][diff][mod] = res;
        }
        return res;
    }
}