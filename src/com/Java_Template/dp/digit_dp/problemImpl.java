package com.Java_Template.dp.digit_dp;


import java.util.Arrays;

public class problemImpl implements problem {

    // 1012. 至少有 1 位重复的数字
    char[] s;
    int[][] memo;
    @Override
    public int numDupDigitsAtMostN(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return n - dfs(0, 0, true, false);
    }

    private int dfs(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int res = 0;
        if (!isNum) { // 可以跳过当前数位
            res = dfs(i + 1, mask, false, false);
        }
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的数字都和 n 的一样，那么这一位至多填数字 s[i]（否则就超过 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; d++) { // 枚举要填入的数字 d
            if ((mask >> d & 1) == 0) { // d 不在 mask 中
                res += dfs(i + 1, mask | (1 << d), isLimit && d == up, true);
            }
        }
        if (!isLimit && isNum){ // isNum一定要为true，这样才是填入了数字 这里可以不写！isLimit
            memo[i][mask] = res;
        }
        return res;
    }

    int Mod = (int) 1e9 + 7;
    // int[][] memo;
    // char[] s;
    // 2719. 统计整数数目
    @Override
    public int count(String num1, String num2, int min_sum, int max_sum) {
        int ans = calc(num2, min_sum, max_sum) - calc(num1, min_sum, max_sum) + Mod;
        int sum = 0;
        for (char c : num1.toCharArray()) {
            sum += c - '0';
        }
        if (min_sum <= sum && sum <= max_sum) {
            ans++;
        }
        return ans % Mod;
    }

    private int calc(String num, int minSum, int maxSum) {
        s = num.toCharArray();
        int n = s.length;
        memo = new int[n][Math.max(maxSum, 9 * n) + 1]; // 9*n就是n次相加，每次都是9
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, minSum, maxSum);
    }

    private int dfs(int i, int sum, boolean isLimit, int minSum, int maxSum) {
        if (sum > maxSum) {
            return 0;
        }
        if (i == s.length) {
            return sum >= minSum ? 1 : 0;
        }
        if (!isLimit && memo[i][sum] != -1) {
            return memo[i][sum];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = 0; d <= upper; d++) {
            ans = (ans + dfs(i + 1, sum + d, isLimit && d == upper, minSum, maxSum)) % Mod;
        }
        if (!isLimit) {
            memo[i][sum] = ans;
        }
        return ans;
    }


    // 788. 旋转数字
    int[] map = {0, 0, 1, -1, -1, 1, 1, -1, 0, 1};
    public int rotatedDigits(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][2];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true);
    }

    private int dfs(int i, int hasDiff, boolean isLimit) {
        if (i == s.length) {
            return hasDiff;
        }
        if (!isLimit && memo[i][hasDiff] != -1) {
            return memo[i][hasDiff];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = 0; d <= upper; d++) {
            if (map[d] != -1) {
                ans += dfs(i + 1, hasDiff | map[d], isLimit && d == upper);
            }
        }
        if (!isLimit) {
            memo[i][hasDiff] = ans;
        }
        return ans;
    }
}
