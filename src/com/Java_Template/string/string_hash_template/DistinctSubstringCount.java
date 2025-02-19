package com.Java_Template.string.string_hash_template; 
/**
 * DistinctSubstringCount
 * 
 * 功能:
 * 给定一个字符串，利用字符串 Hash 技术计算所有不同的子串个数。
 * 枚举所有可能的子串，并使用 HashSet 存储每个子串对应的哈希值，从而去重并计算不同子串的数量。
 */
import java.util.HashSet;
import java.util.Scanner;

public class DistinctSubstringCount {
    static final long P = 131;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        
        // 预处理哈希数组和幂次数组
        long[] hash = new long[n + 1];
        long[] power = new long[n + 1];
        power[0] = 1;
        for (int i = 0; i < n; i++) {
            hash[i + 1] = hash[i] * P + s.charAt(i);
            power[i + 1] = power[i] * P;
        }
        
        HashSet<Long> substrings = new HashSet<>();
        // 枚举所有子串，并将子串的哈希值加入集合
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                long curHash = getHash(hash, power, i, j);
                substrings.add(curHash);
            }
        }
        
        System.out.println("Total distinct substrings: " + substrings.size());
        sc.close();
    }
    
    static long getHash(long[] hash, long[] power, int L, int R) {
        return hash[R + 1] - hash[L] * power[R - L + 1];
    }
}