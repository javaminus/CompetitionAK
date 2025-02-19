package com.Java_Template.string.string_hash_template; 
/**
 * SubstringEquality
 * 
 * 功能:
 * 给定一个长字符串和多组查询，每组查询给出两个子串区间，判断这两个子串是否相等。
 * 使用 Rolling Hash 算法预处理哈希值和幂次数，从而在常数时间内比较两个子串的哈希值。
 */
import java.util.Scanner;

public class SubstringEquality {
    static final long P = 131; // 哈希基数

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        
        // 预处理哈希值和幂次
        long[] hash = new long[n + 1];
        long[] power = new long[n + 1];
        power[0] = 1;
        for (int i = 0; i < n; i++) {
            hash[i + 1] = hash[i] * P + s.charAt(i);
            power[i + 1] = power[i] * P;
        }
        
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int l1 = sc.nextInt(); // 起始下标（假设输入为0-indexed）
            int r1 = sc.nextInt();
            int l2 = sc.nextInt();
            int r2 = sc.nextInt();
            long hash1 = getHash(hash, power, l1, r1);
            long hash2 = getHash(hash, power, l2, r2);
            if(hash1 == hash2) {
                System.out.println("Equal");
            } else {
                System.out.println("Not Equal");
            }
        }
        sc.close();
    }
    
    // 计算子串 s[L...R] 的哈希值
    static long getHash(long[] hash, long[] power, int L, int R) {
        return hash[R + 1] - hash[L] * power[R - L + 1];
    }
}