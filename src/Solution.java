import java.util.ArrayList;
import java.util.List;

class Solution {
    private static long Mod = (long) 1e9 + 7;
    public int numberOfWays(String s, String t, long k) {
        int n = s.length();
        int c = findAll(s + s.substring(0, n - 1), t, getNext(t)).length;
        long[][] m = {
                {c - 1, c},
                {n - c, n - 1 - c},
        };
        m = power(m, k);
        return s.equals(t) ? (int) m[0][0] : (int) m[0][1];
    }

    private static int[] getNext(String pattern) { // 求next数组
        int n = pattern.length();
        int[] next = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }


    private static int[] findAll(String s, String pattern, int[] next) { // 获取所有匹配的节点
        List<Integer> matchIndices = new ArrayList<>();
        int n = s.length();
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1]; // Move to the next possible match position
            }
            if (s.charAt(i) == pattern.charAt(j)) {
                j++; // Increment the pattern index
            }
            if (j == pattern.length()) {
                matchIndices.add(i - j + 1); // Add the matching index to the list
                j = next[j - 1]; // Reset the pattern index to the next possible match position
            }
        }
        int[] result = new int[matchIndices.size()];
        for (int i = 0; i < matchIndices.size(); i++) {
            result[i] = matchIndices.get(i);
        }
        return result;
    }


    // 矩阵相乘
    // a的列数一定要等于b的行数
    public static long[][] multiply(long[][] a, long[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        long[][] ans = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    // ans[i][j] += (long) a[i][c] * b[c][j] % Mod;
                    ans[i][j] = (ans[i][j] + (a[i][c] * b[c][j] % Mod)) % Mod;
                }
            }
        }
        return ans;
    }

    // 矩阵快速幂
    // 要求矩阵m是正方形矩阵
    public static long[][] power(long[][] matrix, long p) {
        int n = matrix.length;
        long[][] ans = new long[n][n];
        // 对角线全是1、剩下数字都是0的正方形矩阵，称为单位矩阵
        // 相当于正方形矩阵中的1，矩阵a * 单位矩阵 = 矩阵a
        for (int i = 0; i < n; i++) {
            ans[i][i] = 1;
        }
        while (p != 0) {
            if ((p & 1) == 1) {
                ans = multiply(ans, matrix);
            }
            matrix = multiply(matrix, matrix);
            p >>= 1;
        }
        return ans;
    }
}
