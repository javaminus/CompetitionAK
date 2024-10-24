package com.Java_Template.string.template;

/**
 *
 * https://www.bilibili.com/video/BV1Ag411o7US/?spm_id_from=333.999.0.0&vd_source=607514df4428a309d5130d87a0423d0c
 */
public class MyKmp {
    private static int[] getNext(String pattern) { // 求next数组
        int n = pattern.length();
        int[] next = new int[n]; // next[0] = 0
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    private static int find(String s, String pattern, int[] next) { // 返回主串匹配模式串的第一个下标
        int n = s.length();
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1]; // 下一个匹配位为next数组的第j-1位
            }
            if (s.charAt(i) == pattern.charAt(j)) {
                j++; // 主串通过i进行加1，模式串通过j加1
            }
            if (j == pattern.length()) {
                return i - j + 1; // 返回匹配位置
            }
        }
        return -1;
    }

    public static void printNext(String s){
        System.out.println("********************");
        int[] nextI = getNext(s);
        System.out.print("模式串：'"+s+"'的next[]数组为：[");
        for(int i = 0; i < (nextI.length); i++){
            System.out.print(nextI[i]+" ");
        }
        System.out.println("]");
        System.out.println("模式串长度为："+nextI.length);
    }

    public static void main(String[] args){
        String s = "CDFGFABABAFABABAAAQWEDC";
        String t = "ABABAA";
        int[] next = getNext(t);
        int res = find(s, t, next);
        if (res!=-1){
            System.out.println("起始位置为："+res);
        }
        else System.out.println("主串中不包含字符串："+t);
        printNext("ABCDABD");
        printNext("ABABAA");
        printNext("ABAABCAC");
        printNext("ABCABCABC");
    }

    /*起始位置为：11
    ********************
    模式串：'ABCDABD'的next[]数组为：[0 0 0 0 1 2 0 ]
    模式串长度为：7
    ********************
    模式串：'ABABAA'的next[]数组为：[0 0 1 2 3 1 ]
    模式串长度为：6
    ********************
    模式串：'ABAABCAC'的next[]数组为：[0 0 1 1 2 0 1 0 ]
    模式串长度为：8*/

}