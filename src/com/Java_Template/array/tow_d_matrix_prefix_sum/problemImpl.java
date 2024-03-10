package com.Java_Template.array.tow_d_matrix_prefix_sum;

/**
 * @author Minus
 * @date 2024/3/10 13:09
 */
public class problemImpl implements problem{

    // 1277. 统计全为 1 的正方形子矩阵
    @Override
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                        ans++;
                    }else{
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1;
                        ans += dp[i][j];
                    }
                }
            }
        }
        return ans;
    }

    // 221. 最大正方形
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int ans = 0;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i + 1][j + 1] = Math.min(Math.min(dp[i][j], dp[i + 1][j]), dp[i][j + 1]) + 1;
                    ans = Math.max(dp[i + 1][j + 1], ans);
                }
            }
        }
        return ans * ans;
    }

    // 1504. 统计全 1 子矩形
    public int numSubmat(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            // 统计
            for (int j = i; j < m; j++) {
                int now = 0;
                for (int k = 0; k < n; k++) {
                    if (mat[i][k] == 0) {
                        now = 0;
                    }else{
                        now++;
                        ans += now;
                    }
                }
            }

            // 压缩
            for (int j = m - 1; j > i; j--) {
                for (int k = 0; k < n; k++) {
                    mat[j][k] = mat[j][k] & mat[j - 1][k];
                }
            }

        }
        return ans;
    }


    // 85. 最大矩形
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            // 统计
            for (int j = i; j < m; j++) {
                int cnt = 0;
                for (int k = 0; k < n; k++) {
                    if (matrix[j][k] == '0') {
                        cnt = 0;
                    }else{
                        cnt++;
                        ans = Math.max(ans, cnt * (i + 1));
                    }
                }
            }

            // 压缩
            for (int j = m - 1; j > i; j--) {
                for (int k = 0; k < n; k++) {
                    if (matrix[j][k] == '1' && matrix[j - 1][k] == '1') {
                        matrix[j][k] = '1';
                    }else{
                        matrix[j][k] = '0';
                    }
                }
            }
        }
        return ans;
    }


}
