package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int B = sc.nextInt(); // 正
        int A = sc.nextInt(); // 负
        long ans = sc.nextInt();
        int[] nums = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            nums[i] = sc.nextInt();
        }
        Arrays.sort(nums);
        for (int i = 0; i < n - 1; i++) {
            if (A > 0 && nums[i] < 0) {
                ans -= nums[i];
                A--;
            } else if (A > 0 && nums[i] >= 0 && n - 1 - i > B) {
                ans -= nums[i];
                A--;
            }else{
                ans += nums[i];
                B--;
            }
        }
        System.out.println(ans);
    }
}
