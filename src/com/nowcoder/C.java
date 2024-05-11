package com.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class C {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    static long Mod = 998244353;
    public static void main(String[] args) {
        int T = sc.nextInt();
        while (T-- > 0) {
            long m = sc.nextInt();
            long a = sc.nextInt();
            long b = sc.nextInt();
            long c = sc.nextInt();
            long m1 = m * m % Mod * m % Mod;
            long a1 = m * (m - 1) % Mod * (m - 2) % Mod * a % Mod;
            long b1 = m * (m - 1) % Mod * 3 % Mod * b % Mod;
            long c1 = m * c % Mod;
            long n = (a1 + b1 + c1) % Mod;
            long x = gcd(n, m1);
            n /= x;
            m1 /= x;
            BigInteger mm = new BigInteger(Long.toString(m1));
            BigInteger nn = new BigInteger(Long.toString(n));
            BigInteger mod = new BigInteger(Long.toString(Mod - 2));
            BigInteger mod1 = new BigInteger(Long.toString(Mod));
            BigInteger ans = nn.multiply(mm.modPow(mod, mod1)).mod(mod1);
            System.out.println(ans);
        }
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
