package com.Java_Template.util;

import java.util.Scanner;

/**
 * 组合数|杨辉三角
 */
public class combination_number_Yang_Hui_triangle {
    private static int N = 1010;
    private static int[][] C = new int[N][N]; // 组合数
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    C[i][j] = 1;
                }else{
                    C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
                }
            }
        }
        System.out.println(C[5][2]);
    }

}
