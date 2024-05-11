package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class A {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            int a = sc.nextInt();
            int k = sc.nextInt();
            solve(n, a, k);
        }
        sc.close();
    }

    private static void solve(int n, int a, int k) {
        int cur = a + 1;
        StringBuilder ans = new StringBuilder();
        while (k-- > 0) {
            if (cur % 7 == 0 || Integer.toString(cur).contains("7")) {
                ans.append("p ");
            }else{
                ans.append(cur).append(" ");
            }
            cur += n;
        }
        System.out.println(ans);
    }
}
