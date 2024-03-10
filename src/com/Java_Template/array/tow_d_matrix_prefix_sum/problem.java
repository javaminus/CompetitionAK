package com.Java_Template.array.tow_d_matrix_prefix_sum;

/**
 * 二维前缀和
 * 1277. 统计全为 1 的正方形子矩阵（https://leetcode.cn/problems/count-square-submatrices-with-all-ones/description/）
 * 221. 最大正方形（https://leetcode.cn/problems/maximal-square/description/）
 * 1504. 统计全 1 子矩形(https://leetcode.cn/problems/count-submatrices-with-all-ones/description/)
 * 85. 最大矩形(https://leetcode.cn/problems/maximal-rectangle/description/)
 *
 */
public interface problem {

    // 1277. 统计全为 1 的正方形子矩阵
    public int countSquares(int[][] matrix);

    // 最大正方形
    public int maximalSquare(char[][] matrix);

    // 1504. 统计全 1 子矩形
    public int numSubmat(int[][] mat);

    // 85. 最大矩形
    public int maximalRectangle(char[][] matrix);
}
