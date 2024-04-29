package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class A {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] nums = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                String s = sc.next();
                if (s.equals("#")) { // 海洋
                    nums[i][j] = 0;
                }else{
                    nums[i][j] = 1; // 陆地
                }
            }
        }
    }


    private static int dfs(int[][] nums, boolean[][] visited, int i, int j) {
        if (visited[i][j]) {
            return 0;
        }
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int ans = 0;
        for (int k = 0; k < 4; k++) {
            int newI = i + dir[k][0];
            int newJ = j + dir[k][1];
            if (newI >= 0 && newI < nums.length && newJ >= 0 && newJ < nums[0].length) {
                ans += dfs(nums, visited, newI, newJ);
            }
        }
        return ans;
    }
}
