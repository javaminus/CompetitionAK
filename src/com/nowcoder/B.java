package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class B {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        char[] a;
        char[] b;
        a = sc.next().toCharArray();
        b = sc.next().toCharArray();
        for (int i = 0; i < n; i++) {
            if (a[i] > b[i]) {
                char t = a[i];
                a[i] = b[i];
                b[i] = t;
            }
        }
        StringBuilder aa = new StringBuilder();
        StringBuilder bb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            aa.append(a[i]);
            bb.append(b[i]);
        }
        BigInteger A = new BigInteger(aa.toString());
        BigInteger B = new BigInteger(bb.toString());
        BigInteger ans = A.multiply(B);
        BigInteger res = ans.mod(new BigInteger("998244353"));
        System.out.println(res);
        sc.close();
    }
}
