package com.Java_Template.util.General;

import java.util.Arrays;

/**
 * 矩阵快速幂
 */
public class matrix_quick_pow {
    static final int[][] a = {
            {1, 2},
            {3, 4}
    };
    static final int[][] b = {
            {3, 1},
            {2, 4}
    };

    //矩阵乘法
    //返回的是a*b矩阵的结果矩阵
    public static int[][] multi(int a[][], int b[][]) {
        int x = a.length, y = b[0].length, z = b.length;
        int[][] ans = new int[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                for (int k = 0; k < z; k++)
                    ans[i][j] += a[i][k] * b[k][j];
        return ans;
    }

    //矩阵快速幂
    public static int[][] pow_multi(int a[][], int n) {
        int m = a.length;
        //初始化ans结果矩阵
        int ans[][] = new int[m][m];
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < m; j ++) {
                if (i == j) ans[i][j] = 1;
            }
        }
        //写法基本与整数快速幂一致
        while (n != 0) {
            if ((n & 1) == 1) ans = multi(ans, a);
            a = multi(a, a);
            n >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        //矩阵乘法
        int[][] c = multi(a, b);
        for (int[] arrs : c) {
            System.out.println(Arrays.toString(arrs));
        }
        System.out.println();
        //快速幂
        int[][] d = pow_multi(a, 2);
        for (int[] arrs : d) {
            System.out.println(Arrays.toString(arrs));
        }
    }
}
