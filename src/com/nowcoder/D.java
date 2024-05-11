package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class D {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    static int[] a = new int[1050];
    public static void main(String[] args) {
        int n = 1 << sc.nextInt() - 1;
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        dfs(0, n);
        System.out.println(Arrays.toString(a));
        sc.close();
    }

    static void dfs(int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        ArrayList<Integer> ji = new ArrayList<>();
        ArrayList<Integer> ou = new ArrayList<>();
        for (int i = 0; i <= r - l; i++) {
            if (i % 2 == 1) {
                ji.add(a[i]);
            }else{
                ou.add(a[i]);
            }
        }
        for (int i = l; i <= mid; i++) {
            a[i] = ou.get(i - l);
        }
        for (int i = mid + 1; i <= r; i++) {
            a[i] = ji.get(i - mid - 1);
        }
        dfs(l, mid);
        dfs(mid + 1, r);
    }

}
