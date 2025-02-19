package com.Java_Template.string.string_hash_template; 
/**
 * RabinKarpMultiPattern
 * 
 * 功能:
 * 给定一个主串和多个模式串，使用 Rabin-Karp 算法快速判断各个模式串是否在主串中出现。
 * 预处理主串的哈希数组和幂次数组，然后对于每个模式串计算哈希并在主串中进行匹配查找。
 */
import java.util.Scanner;

public class RabinKarpMultiPattern {
    static final long P = 131;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 输入主串
        String text = sc.nextLine();
        int n = text.length();
        
        // 预处理主串哈希和值
        long[] hash = new long[n + 1];
        long[] power = new long[n + 1];
        power[0] = 1;
        for (int i = 0; i < n; i++) {
            hash[i + 1] = hash[i] * P + text.charAt(i);
            power[i + 1] = power[i] * P;
        }
        
        int q = sc.nextInt();
        sc.nextLine(); // 消耗换行符
        for (int i = 0; i < q; i++) {
            String pattern = sc.nextLine();
            if (rabinKarpSearch(text, pattern, hash, power)) {
                System.out.println("Pattern \"" + pattern + "\" found.");
            } else {
                System.out.println("Pattern \"" + pattern + "\" not found.");
            }
        }
        sc.close();
    }
    
    static boolean rabinKarpSearch(String text, String pattern, long[] hash, long[] power) {
        int n = text.length();
        int m = pattern.length();
        if(m > n) return false;
        
        long patternHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = patternHash * P + pattern.charAt(i);
        }
        
        for (int i = 0; i <= n - m; i++) {
            long curHash = getHash(hash, power, i, i + m - 1);
            if(curHash == patternHash) {
                // 由于哈希碰撞极低，可以认为找到了匹配；如需要严格验证，可进行字符串比较
                return true;
            }
        }
        return false;
    }
    
    static long getHash(long[] hash, long[] power, int L, int R) {
        return hash[R + 1] - hash[L] * power[R - L + 1];
    }
}