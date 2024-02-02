package com.template.数组;

/**
 * 计算偏移量，使用二维数组存储
 */
public class 重合矩阵 {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        int[][] count = new int[2 * n + 1][2 * n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int point = img1[i][j];
                if (point == 1) {
                    for (int k = 0; k < n; k++) {
                        for (int l = 0; l < n; l++) {
                            if (img2[k][l] == 1) {
                                count[i - k + n][j - l + n] += 1;
                            }
                        }
                    }
                }

            }
        }
        int ans = 0;
        for (int[] c : count) {
            for (int c1 : c) {
                ans = Math.max(ans, c1);
            }
        }
        return ans;
    }
}
