package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class B {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int[] nums = new int[n];
        long ans = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
            if (nums[i] / 3 > 1) {
                ans += (nums[i] / 3 - 1);
            }
        }
        System.out.println(ans);
    }
}
