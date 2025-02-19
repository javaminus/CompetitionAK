package com.Java_Template.string.string_hash_template; 
/**
 * LongestDuplicateSubstring
 * 
 * 功能:
 * 给定一个字符串，使用二分查找和 Rolling Hash 算法查找其中最长的重复（至少出现两次）的子串。
 * 预先计算哈希数组和幂次数组，然后在二分查找过程中利用哈希值判断子串是否重复出现。
 */
import java.util.HashSet;
import java.util.Scanner;

public class LongestDuplicateSubstring {
    static final long P = 131;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = s.length();
        
        // 预处理哈希数组与幂次数组
        long[] hash = new long[n + 1];
        long[] power = new long[n + 1];
        power[0] = 1;
        for (int i = 0; i < n; i++) {
            hash[i + 1] = hash[i] * P + s.charAt(i);
            power[i + 1] = power[i] * P;
        }
        
        int left = 1, right = n;
        int start = -1, maxLen = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            int idx = duplicateSubstring(s, hash, power, mid);
            if (idx != -1) {
                // 找到重复子串，更新最长长度和起始位置
                start = idx;
                maxLen = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        if(start != -1) {
            System.out.println("Longest duplicate substring is: " + s.substring(start, start + maxLen));
        } else {
            System.out.println("No duplicate substring found.");
        }
        sc.close();
    }
    
    // 检查是否存在长度为 len 的重复子串，返回重复子串的起始位置；如没有返回 -1
    static int duplicateSubstring(String s, long[] hash, long[] power, int len) {
        int n = s.length();
        HashSet<Long> seen = new HashSet<>();
        for (int i = 0; i <= n - len; i++) {
            long curHash = getHash(hash, power, i, i + len - 1);
            if (seen.contains(curHash)) {
                return i;
            }
            seen.add(curHash);
        }
        return -1;
    }
    
    static long getHash(long[] hash, long[] power, int L, int R) {
        return hash[R + 1] - hash[L] * power[R - L + 1];
    }
}