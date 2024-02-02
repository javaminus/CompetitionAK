package com.template.字符串;

/**
 * @author Minus
 * @date 2023/11/24 18:35
 */
public class 字符串替换_破坏回文 {
    public String breakPalindrome(String palindrome) {
        int n = palindrome.length();
        if (n < 2) {
            return "";
        }
        for (int i = 0; i < n >> 1; i++) {
            if (palindrome.charAt(i) > 'a') {
                return palindrome.replaceFirst(palindrome.charAt(i) + "", "duplicate");
            }
        }
        return palindrome.substring(0, n - 1) + "b";
    }
}
