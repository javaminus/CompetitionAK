package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class D {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = sc.next();
            for (int j = 0; j < m; j++) {
                char c = s.charAt(j);
                if (c == '*') {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (grid[i][j] == 1) {
                    int k = 1, mx = 0;
                    while (i - k >= 0 && j - k >= 0 && j + k < m) {
                        if (grid[i - k][j - k] == 1 && grid[i - k][j + k] == 1) {
                            mx++;
                        }else{
                            break;
                        }
                        k++;
                    }
                    ans = Math.max(ans, mx);
                }
            }
        }
        System.out.println(ans);
        sc.close();
    }
}
