package com.nowcoder;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class F {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int X = sc.nextInt();
        int[] nums1 = new int[n];
        for (int i = 0; i < n; i++) {
            nums1[i] = sc.nextInt();
        }
        int m = sc.nextInt();
        int Y = sc.nextInt();
        int[] nums2 = new int[m];
        for (int i = 0; i < m; i++) {
            nums2[i] = sc.nextInt();
        }
        boolean[] cnt1 = new boolean[10005];
        boolean[] cnt2 = new boolean[10005];
        int Clrlss = 0, Yaya = 0;
        for (int i = 0; i < 10000; i++) {
            Arrays.sort(nums1);
            for (int j = 0; j < n; j++) {
                if (nums1[j] >= i) {
                    nums1[j] -= i;
                    cnt1[i] = true;
                    break;
                }
            }
            if (!cnt1[i]) {
                if (X >= i) {
                    X -= i;
                    cnt1[i] = true;
                }else{
                    Clrlss = i;
                    break;
                }
            }
        }
        for (int i = 0; i < 10000; i++) {
            Arrays.sort(nums2);
            for (int j = 0; j < m; j++) {
                if (nums2[j] >= i) {
                    nums2[j] -= i;
                    cnt2[i] = true;
                    break;
                }
            }
            if (!cnt2[i]) {
                if (Y >= i) {
                    Y -= i;
                    cnt2[i] = true;
                }else{
                    Yaya = i;
                    break;
                }
            }
        }
        if (Yaya == Clrlss) {
            System.out.println("Draw");
        } else if (Yaya > Clrlss) {
            System.out.println("Yaya");
        }else{
            System.out.println("Clrlss");
        }
    }
}
