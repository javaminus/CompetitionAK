package com.nowcoder;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class E {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static int Mod = 998244353;
    public static void main(String[] args) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        String s = sc.next();
        long[][] dp = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((s.charAt(i) == '0' && s.charAt(j) == '1') || (s.charAt(i) == '1' && s.charAt(j) == '0')) {
                    dp[i][j] += dp[i][j - 1] + 1;
                }
            }
        }
        while (m-- > 0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            System.out.println(dp[l - 1][r - 1]);
        }
    }
}
