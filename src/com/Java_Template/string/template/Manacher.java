package com.Java_Template.string.template;

import java.util.Arrays;

/**
 * Manacher 算法
 * 用途：
 * 1、计算以s[i]或则（s[i]和s[i+1]）为回文中心的最长回文字串长度
 * 2、判断任意子串是否为回文串
 * 3、计算s[i]开始的最长回文子串长度
 * 4、计算s[i]结尾的最长回文子串长度
 */
public class Manacher {

    /**
     * 精简版 返回最长的回文串
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s.charAt(i);
        }
        t[n * 2 + 2] = '$';
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        int boxM = 0, boxR = 0, maxI = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }
        int hl = halfLen[maxI];
        return s.substring((maxI - hl) / 2, (maxI + hl) / 2 - 1);
    }

    /**
     * 返回回文串的数目
     */
    public int countSubstrings(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s.charAt(i);
        }
        t[n * 2 + 2] = '$';
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        int boxM = 0, boxR = 0, maxI = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }
        int ans = 0;
        for (int i = 2; i <= n * 2; i++) {
            ans += halfLen[i] / 2;
        }
        return ans;
    }



    /**
     * 详细注释版
     */
    public String longestPalindrome_P(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s.charAt(i);
        }
        t[n * 2 + 2] = '$';

        // 定义一个奇回文串的回文半径=(长度+1)/2，即保留回文中心，去掉一侧后的剩余字符串的长度
        // halfLen[i] 表示在 t 上的以 t[i] 为回文中心的最长回文子串的回文半径
        // 即 [i-halfLen[i]+1,i+halfLen[i]-1] 是 t 上的一个回文子串
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        // boxR 表示当前右边界下标最大的回文子串的右边界下标+1
        // boxM 为该回文子串的中心位置，二者的关系为 r=mid+halfLen[mid]
        int boxM = 0, boxR = 0, maxI = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                // 记 i 关于 boxM 的对称位置 i'=boxM*2-i
                // 若以 i' 为中心的最长回文子串范围超出了以 boxM 为中心的回文串的范围（即 i+halfLen[i'] >= boxR）
                // 则 halfLen[i] 应先初始化为已知的回文半径 boxR-i，然后再继续暴力匹配
                // 否则 halfLen[i] 与 halfLen[i'] 相等
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            // 暴力扩展
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }
        int hl = halfLen[maxI]; // maxI是最长的回文串的中心位置
        // 注意 t 上的最长回文子串的最左边和最右边都是 '#'
        // 所以要对应到 s，最长回文子串的下标是从 (maxI-hl)/2 到 (maxI+hl)/2-2
        return s.substring((maxI - hl) / 2, (maxI + hl) / 2 - 1);
    }
}
