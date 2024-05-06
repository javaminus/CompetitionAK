package com.nowcoder;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class E {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        Arrays.sort(nums);
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            int mx = 0, minus = calSum(nums[i]);
            for (int j = i - 1; j >= 0; j--) {
                int sum = nums[i] + nums[j] - calRepeat(nums[i], nums[j]);
                if (mx - minus > sum) {
                    break;
                }
                mx = Math.max(mx, sum);
            }
            if (ans - minus > mx) {
                break;
            }
            ans = Math.max(ans, mx);
        }
        System.out.println(ans);

    }

    private static int calSum(int x) {
        String s = Integer.toString(x);
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            ans += s.charAt(i) - '0';
        }
        return ans;
    }

    private static int calRepeat(int a, int b) {
        int ans = 0;
        String A = Integer.toString(a);
        String B = Integer.toString(b);
        for (int i = 0; i < Math.min(A.length(), B.length()); i++) {
            if (A.charAt(i) == B.charAt(i)) {
                ans += A.charAt(i) - '0';
            }
        }
        return ans;
    }
}
