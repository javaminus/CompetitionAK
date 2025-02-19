package com.Java_Template.string.string_hash_template; 
/**
 * PalindromeSubstringCheck
 * 
 * 功能:
 * 给定一个字符串和多次子串查询，判断每个查询的子串是否为回文串。
 * 通过预处理正向和反向字符串的哈希值，然后在查询时比较对应区间的哈希值，达到快速判断的目的。
 */
import java.util.Scanner;

public class PalindromeSubstringCheck {
    static final long P = 131;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        
        // 预处理正向哈希
        long[] hash = new long[n + 1];
        long[] power = new long[n + 1];
        power[0] = 1;
        for (int i = 0; i < n; i++) {
            hash[i + 1] = hash[i] * P + s.charAt(i);
            power[i + 1] = power[i] * P;
        }
        
        // 预处理反向哈希（字符串反序处理）
        long[] revHash = new long[n + 1];
        long[] revPower = new long[n + 1];
        revPower[0] = 1;
        for (int i = n - 1, idx = 0; i >= 0; i--, idx++) {
            revHash[idx + 1] = revHash[idx] * P + s.charAt(i);
            revPower[idx + 1] = revPower[idx] * P;
        }
        
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int l = sc.nextInt(); // 假设下标从0开始
            int r = sc.nextInt();
            if(isPalindrome(hash, power, revHash, revPower, l, r, n))
                System.out.println("Palindrome");
            else
                System.out.println("Not Palindrome");
        }
        sc.close();
    }
    
    // 检查子串 s[l...r] 是否为回文串
    static boolean isPalindrome(long[] hash, long[] power, long[] revHash, long[] revPower, int l, int r, int n) {
        long forwardHash = getHash(hash, power, l, r);
        // 反向哈希处理中：对应于 s[l...r] 的反转应该出现在 revHash 中 [n-1-r, n-1-l]
        int revL = n - 1 - r;
        int revR = n - 1 - l;
        long backwardHash = getHash(revHash, revPower, revL, revR);
        return forwardHash == backwardHash;
    }
    
    static long getHash(long[] hash, long[] power, int L, int R) {
        return hash[R + 1] - hash[L] * power[R - L + 1];
    }
}