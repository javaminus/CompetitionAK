import java.util.Arrays;

class Solution {
    private static long Mod = (long) 1e9 + 7;
    public int checkRecord(int n) {
        long[][] start = {{1, 0, 0, 0, 0, 0}};
        long[][] base = {{1, 1, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 0},
                {1, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 0, 0}};
        long[][] ans = multiply(start, power(base, n));
        long sum = Arrays.stream(ans[0]).sum();
        return (int) (sum % Mod);
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
                    // ans[i][j] += (long) a[i][c] * b[c][j] % Mod; 报错
                    ans[i][j] = (ans[i][j] + (a[i][c] * b[c][j] % Mod)) % Mod;
                }
            }
        }
        return ans;
    }

    // 矩阵快速幂
    // 要求矩阵m是正方形矩阵
    public static long[][] power(long[][] matrix, int p) {
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