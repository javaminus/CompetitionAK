package com.atcoder.abc_371;

import java.util.Scanner;
import java.util.Arrays;

// https://atcoder.jp/contests/abc371/tasks/abc371_e 就是求每个点的贡献
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] A = new int[N];
        int[] lst = new int[N];
        Arrays.fill(lst, -1);
        long ans = 0;
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt() - 1;
            if (lst[A[i]] != -1) {
                ans += (long) (i - lst[A[i]]) * (lst[A[i]] + 1);
            }
            lst[A[i]] = i;
        }
        for (int i = 0; i < N; i++) {
            if (lst[i] != -1) {
                ans += (long) (N - lst[i]) * (lst[i] + 1);
            }
        }
        System.out.println(ans);
    }
}
